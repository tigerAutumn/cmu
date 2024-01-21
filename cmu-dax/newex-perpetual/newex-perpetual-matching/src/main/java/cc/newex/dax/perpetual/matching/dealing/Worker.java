package cc.newex.dax.perpetual.matching.dealing;

import cc.newex.dax.perpetual.common.config.PerpetualConfig;
import cc.newex.dax.perpetual.common.enums.ContractStatusEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.matching.bean.constant.Constants;
import cc.newex.dax.perpetual.matching.service.TradeService;
import cc.newex.dax.perpetual.service.ContractService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 启动线程进行合约撮合
 *
 * @author xionghui
 * @date 2018/10/20
 */
@Slf4j
@Component
public class Worker {
    private static final int THREAD_POOL_SIZE = 128;

    private static final String uuid = UUID.randomUUID().toString();

    @Autowired
    private ContractService contractService;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private PerpetualConfig perpetualConfig;
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 本机器正在撮合入库的合约
    private final ConcurrentMap<String, AtomicInteger> contractWorkMap = new ConcurrentHashMap<>();

    // 上一次的合约
    private final AtomicReference<Map<String, Contract>> contractMapReference =
            new AtomicReference<>();
    // 需要撮合的最新合约
    private final ConcurrentMap<String, Dealer> dealerMap = new ConcurrentHashMap<>();
    // 正在撮合的合约
    private final ConcurrentMap<String, Dealer> runningDealerMap = new ConcurrentHashMap<>();
    // 撮合所有合约的线程，线程不能无限增加，以防止内存溢出
    private static final ThreadPoolExecutor dealerThreadPoolExecutor =
            new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE, 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    // 正在撮合入库的合约
    private final ConcurrentMap<String, String> runningDBMap = new ConcurrentHashMap<>();
    // 撮合入库的线程，线程不能无限增加，以防止内存溢出
    private static final ThreadPoolExecutor dbThreadPoolExecutor =
            new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE, 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    @PostConstruct
    public void init() {
        this.refresh();
        new Thread((() -> {
            while (true) {
                try {
                    /**
                     * 合约总数可能大于线程池数，此时币对需要排队等待前面的合约搓完 <br />
                     * 每撮完一轮合约后休息一段时间
                     */
                    for (Map.Entry<String, Dealer> entry : this.dealerMap.entrySet()) {
                        Dealer dealer = entry.getValue();
                        // 如果该合约正在撮合中则不重复撮
                        if (this.runningDealerMap.putIfAbsent(entry.getKey(), dealer) == null) {
                            dealerThreadPoolExecutor.execute(() -> {
                                try {
                                    AtomicInteger tag = this.contractWorkMap.get(entry.getKey());
                                    // 该合约不属于本机器撮合或者在停止中
                                    if (tag == null) {
                                        return;
                                    }
                                    if (tag.get() != 0) {
                                        // try stop
                                        if (tag.get() < 3) {
                                            tag.getAndIncrement();
                                        }
                                        return;
                                    }

                                    final Contract contract =
                                            this.contractService.getContractWithNoCache(entry.getKey());
                                    ContractStatusEnum contractStatusEnum =
                                            ContractStatusEnum.fromInteger(contract.getStatus());
                                    // 清算中停撮合
                                    if (contractStatusEnum == ContractStatusEnum.NORMAL) {
                                        dealer.deal();
                                    } else {
                                        if (contractStatusEnum == ContractStatusEnum.CLEARING) {
                                            contract.setStatus(ContractStatusEnum.MATCHING_STOP.getCode());
                                            this.contractService.editById(contract);
                                        }
                                    }
                                } catch (Exception e) {
                                    log.error("Worker init deal error: ", e);
                                    dealer.resetInit();
                                } finally {
                                    this.runningDealerMap.remove(entry.getKey());
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    log.error("Worker init dealerThreadPoolExecutor error: ", e);
                } finally {
                    try {
                        // rest for 10ms to release cpu
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }
            }
        })).start();
        new Thread((() -> {
            while (true) {
                try {
                    for (Map.Entry<String, Dealer> entry : this.dealerMap.entrySet()) {
                        String contractCode = entry.getValue().getContract().getContractCode().toLowerCase();
                        // 如果该合约正在入库中则不重复入库
                        if (this.runningDBMap.putIfAbsent(contractCode, "") == null) {
                            dbThreadPoolExecutor.execute(() -> {
                                try {
                                    AtomicInteger tag = this.contractWorkMap.get(contractCode);
                                    // 该合约不属于本机器撮合或者在停止中
                                    if (tag == null) {
                                        return;
                                    }
                                    if (tag.get() != 0) {
                                        // try stop
                                        if (tag.get() < 3) {
                                            tag.getAndIncrement();
                                        }
                                        return;
                                    }

                                    this.tradeService.dealDB(contractCode);
                                } catch (Exception e) {
                                    log.error("Worker init dealDB error: ", e);
                                } finally {
                                    this.runningDBMap.remove(contractCode);
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    log.error("Worker init dbThreadPoolExecutor error: ", e);
                } finally {
                    try {
                        // rest for 10ms to release cpu
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }
            }
        })).start();
    }

    /**
     * 下线币对移除旧的dealer，上线币对增加新的dealer
     */
    public void refresh() {
        log.info("Worker refresh uuid: {}, {}", uuid, this.contractWorkMap.toString());
        // 下线的币对也查询
        final List<Contract> contractAllList = this.contractService.getAll();
        final List<Contract> contractList = new ArrayList<>();
        final Set<String> contractCode = new HashSet<>();
        for (final Contract contract : contractAllList) {
            if (contract.getExpired() == 0 && contractCode.add(contract.getContractCode().toLowerCase())) {
                contractList.add(contract);
            }
        }
        for (final Contract contract : contractAllList) {
            if (contract.getExpired() != 0 && contractCode.add(contract.getContractCode().toLowerCase())) {
                contractList.add(contract);
            }
        }
        // 刷新redis
        this.refreshMachine(contractList);
        final Map<String, Contract> contractMap = new ConcurrentHashMap<>();
        contractList.forEach((contract) -> {
            contractMap.put(contract.getContractCode().toLowerCase(), contract);
        });
        Map<String, Contract> oldContractMap = this.contractMapReference.getAndSet(contractMap);
        if (oldContractMap == null) {
            oldContractMap = new HashMap<>();
        }
        for (final Map.Entry<String, Contract> entry : contractMap.entrySet()) {
            final Contract oldContract = oldContractMap.remove(entry.getKey());
            // 比较Contract是否有变化
            if (entry.getValue().equals(oldContract)) {
                continue;
            }
            final Dealer dealer = new Dealer(entry.getValue(), contractList, this.tradeService,
                    this.perpetualConfig, this.redisTemplate);
            // 修改或者新增合约
            this.dealerMap.put(entry.getKey(), dealer);
        }
        // 需要下线的合约
        for (final Map.Entry<String, Contract> entry : oldContractMap.entrySet()) {
            this.dealerMap.remove(entry.getKey());
        }
    }

    /**
     * 刷新redis缓存
     */
    private void refreshMachine(final List<Contract> contractList) {
        Map<Object, Object> entries = this.redisTemplate.opsForHash().entries(Constants.PERPETUAL_MACHINE);
        if (entries == null) {
            entries = new HashMap<>();
        }

        // 本机的key
        final String contractCodekey = Constants.PERPETUAL_MACHINE + "_" + uuid;
        // 1分钟失效
        this.redisTemplate.opsForValue().set(contractCodekey, "1", 1, TimeUnit.MINUTES);

        // 宕机的机器释放合约
        for (final Contract contract : contractList) {
            final String lockUuid = this.redisTemplate.opsForValue().get(Constants.PERPETUAL_MACHINE
                    + "_" + contract.getContractCode().toLowerCase());
            if (lockUuid != null) {
                final String value = this.redisTemplate.opsForValue().get(Constants.PERPETUAL_MACHINE + "_" + lockUuid);
                if (value == null) {
                    this.redisTemplate.delete(Constants.PERPETUAL_MACHINE + "_" + contract.getContractCode().toLowerCase());
                }
            }
        }

        // 宕机的机器
        final Set<String> expiredSet = new HashSet<>();
        // 存活的机器
        final Set<String> unExpiredSet = new HashSet<>();
        for (final Map.Entry<Object, Object> entry : entries.entrySet()) {
            final String machineKey = (String) entry.getKey();
            // add需要撮合的合约
            if (contractCodekey.equals(machineKey)) {
                final JSONArray machineValue = JSON.parseArray((String) entry.getValue());
                if (machineValue != null) {
                    for (final Object obj : machineValue) {
                        final String value = this.redisTemplate.opsForValue().get(Constants.PERPETUAL_MACHINE + "_" + obj);
                        if (uuid.equals(value)) {
                            this.contractWorkMap.putIfAbsent((String) obj, new AtomicInteger(0));
                        }
                    }
                }
            }
            final String value = this.redisTemplate.opsForValue().get(machineKey);
            // 该台机器可能已经宕机
            if (value == null) {
                final JSONArray machineValue = JSON.parseArray((String) entry.getValue());
                if (machineValue != null) {
                    for (final Object obj : machineValue) {
                        this.redisTemplate.delete(Constants.PERPETUAL_MACHINE + "_" + (String) obj);
                    }
                }
                expiredSet.add(machineKey);
            } else {
                unExpiredSet.add(machineKey);
            }
        }
        if (expiredSet.size() > 0) {
            // 移除失效的key
            this.redisTemplate.opsForHash().delete(Constants.PERPETUAL_MACHINE, expiredSet.toArray());
        }
        // 移除处理完的合约
        for (final String contractCode : this.contractWorkMap.keySet()) {
            final AtomicInteger tag = this.contractWorkMap.get(contractCode);
            if (tag.get() >= 3) {
                this.redisTemplate.delete(Constants.PERPETUAL_MACHINE + "_" + contractCode);
                this.contractWorkMap.remove(contractCode);
            }
        }

        // 停止不属于本机的撮合任务
        if (this.contractWorkMap.size() > 0) {
            boolean stop = false;
            for (final String contractCode : this.contractWorkMap.keySet()) {
                final String value = this.redisTemplate.opsForValue().get(Constants.PERPETUAL_MACHINE + "_" + contractCode);
                if (!uuid.equals(value)) {
                    stop = true;
                    final AtomicInteger tag = this.contractWorkMap.get(contractCode);
                    if (tag.get() == 0) {
                        tag.getAndIncrement();
                    }
                }
            }
            if (stop) {
                // 停完才继续
                return;
            }
        }

        unExpiredSet.add(contractCodekey);
        int count = contractList.size() / unExpiredSet.size();
        count = (contractList.size() % unExpiredSet.size()) == 0 ? count : count + 1;
        final Set<String> newContractCodeSet = new HashSet<>(this.contractWorkMap.keySet());
        if (newContractCodeSet.size() > count) {
            int diff = newContractCodeSet.size() - count;
            for (final Map.Entry<String, AtomicInteger> entry : this.contractWorkMap.entrySet()) {
                if (entry.getValue().get() == 0) {
                    entry.getValue().getAndIncrement();
                }
                if (--diff == 0) {
                    break;
                }
            }
        } else if (newContractCodeSet.size() < count) {
            int diff = count - newContractCodeSet.size();
            for (final Contract contract : contractList) {
                final Boolean absent = this.redisTemplate.opsForValue().setIfAbsent(Constants.PERPETUAL_MACHINE
                        + "_" + contract.getContractCode().toLowerCase(), uuid);
                if (absent != null && absent) {
                    newContractCodeSet.add(contract.getContractCode().toLowerCase());
                    this.contractWorkMap.putIfAbsent(contract.getContractCode().toLowerCase(), new AtomicInteger(0));
                    if (--diff == 0) {
                        break;
                    }
                }
            }
        }
        final JSONArray newContractCode = new JSONArray();
        newContractCode.addAll(newContractCodeSet);
        this.redisTemplate.opsForHash().put(Constants.PERPETUAL_MACHINE, contractCodekey, newContractCode.toJSONString());
    }
}
