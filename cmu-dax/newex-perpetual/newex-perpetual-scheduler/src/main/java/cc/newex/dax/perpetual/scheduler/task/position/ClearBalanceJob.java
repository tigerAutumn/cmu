package cc.newex.dax.perpetual.scheduler.task.position;

import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Explosion;
import cc.newex.dax.perpetual.domain.Liquidate;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.scheduler.service.ClearUserPositionService;
import cc.newex.dax.perpetual.scheduler.service.SettlementUserPositionService;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.ExplosionService;
import cc.newex.dax.perpetual.service.LiquidateService;
import cc.newex.dax.perpetual.service.common.MarketService;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 清算用户资产
 */
@Slf4j
@Component
@JobHandler("ClearBalanceJob")
public class ClearBalanceJob extends IJobHandler {

    private static final int POOL_SIZE = 32;
    private static final int RECORD_SIZE = 10000;
    ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("clear-balance-%d")
            .setDaemon(false)
            .build();
    private final ExecutorService executorService = new ThreadPoolExecutor(ClearBalanceJob.POOL_SIZE,
            ClearBalanceJob.POOL_SIZE, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), this.threadFactory);
    ThreadFactory subThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("clear-balance-sub-%d")
            .setDaemon(false)
            .build();
    private final ExecutorService workerExectorService = new ThreadPoolExecutor(ClearBalanceJob.POOL_SIZE,
            ClearBalanceJob.POOL_SIZE, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), this.subThreadFactory);

    @Autowired
    private ContractService contractService;
    @Autowired
    private ExplosionService explosionService;
    @Autowired
    private LiquidateService liquidateService;
    @Autowired
    private ClearUserPositionService clearUserPositionService;
    @Autowired
    private SettlementUserPositionService settlementUserPositionService;
    @Autowired
    private MarketService marketService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {

        final List<Contract> unExpiredContract = this.contractService.getUnExpiredContract();
        if (CollectionUtils.isNotEmpty(unExpiredContract)) {
            final Map<String, List<Contract>> groupContract =
                    unExpiredContract.stream().collect(Collectors.groupingBy(Contract::getBase));
            final Set<Map.Entry<String, List<Contract>>> entrySet = groupContract.entrySet();

            final CountDownLatch countDownLatch = new CountDownLatch(entrySet.size());
            for (final Map.Entry<String, List<Contract>> entry : entrySet) {
                this.executorService.execute(new Worker(countDownLatch, entry.getValue(), unExpiredContract));
            }
            countDownLatch.await();
        }

        return ReturnT.SUCCESS;
    }

    private class Worker implements Runnable {
        private final CountDownLatch countDownLatch;
        private final List<Contract> contractList;
        private final List<Contract> allContractList;

        private Worker(final CountDownLatch countDownLatch, final List<Contract> contractList, final List<Contract> allContractList) {
            this.countDownLatch = countDownLatch;
            this.contractList = contractList;
            this.allContractList = allContractList;
        }

        @Override
        public void run() {
            try {

                for (final Contract contract : this.contractList) {

                    if (contract.getType().equalsIgnoreCase("perpetual")) {
                        // 永续合约清算
                        this.clear(contract);
                    } else {
                        // 定期合约结算结算
                        this.settlement(contract);
                    }
                    // 平仓
                    this.liquidate(contract);
                    // 爆仓
                    this.explosion(contract);
                }
            } catch (final Exception e) {
                ClearBalanceJob.log.error("unexpected error", e);
            } finally {
                this.countDownLatch.countDown();
            }
        }

        /**
         * 清算
         *
         * @param contract
         */
        void clear(final Contract contract) {
            try {
                // 没到清算时间
                if (contract.getLiquidationDate().getTime() > System.currentTimeMillis()) {
                    return;
                }
                ClearBalanceJob.log.info("clear position, contractCode : {}", contract.getContractCode());
                final long ts = System.currentTimeMillis();
                final boolean lock = this.tryLockContract(contract, 30);
                if (!lock) {
                    ClearBalanceJob.log.error("try lock contract failed, contractCode : {}", contract.getContractCode());
                    return;
                }
                ClearBalanceJob.this.clearUserPositionService.clearUserPosition(contract, this.contractList);
                ClearBalanceJob.log.info("clear user position, contractCode : {}, spend : {} ms", contract.getContractCode(), (System.currentTimeMillis() - ts));
            } catch (final Exception e) {
                ClearBalanceJob.log.error("clear error", e);
            }
        }

        /**
         * 结算
         *
         * @param contract
         */
        void settlement(final Contract contract) {
            try {
                // 没到清算时间
                if (contract.getSettlementDate().getTime() > System.currentTimeMillis()) {
                    return;
                }
                final long ts = System.currentTimeMillis();
                final boolean lock = this.tryLockContract(contract, 30);
                if (!lock) {
                    ClearBalanceJob.log.error("try lock contract failed, contractCode : {}", contract.getContractCode());
                    return;
                }
                ClearBalanceJob.this.settlementUserPositionService.settlement(contract, this.contractList);
                ClearBalanceJob.log.info("settlement user position, contractCode : {}, spend : {} ms", contract.getContractCode(), (System.currentTimeMillis() - ts));
            } catch (final Exception e) {
                ClearBalanceJob.log.error("settlement error", e);
            }
        }

        /**
         * 平仓
         *
         * @param contract
         */
        void liquidate(final Contract contract) {
            try {
                long id = 0;
                final long ts = System.currentTimeMillis();
                int size = 0;
                while (true) {
                    final List<Liquidate> liquidates = ClearBalanceJob.this.liquidateService.listLiquidate(id, ClearBalanceJob.RECORD_SIZE, contract.getContractCode());
                    size += liquidates.size();
                    if (CollectionUtils.isEmpty(liquidates)) {
                        break;
                    }
                    final Liquidate maxIdRecord = liquidates.stream().max(Comparator.comparing(Liquidate::getId)).get();
                    id = Math.max(id, maxIdRecord.getId());
                    final List<List<Liquidate>> records = this.sharding(liquidates, 200);
                    final CountDownLatch latch = new CountDownLatch(records.size());
                    ClearBalanceJob.log.info("liquidate worker : contractCode : {}, liquidate list size : {}", contract.getContractCode(), liquidates.size());
                    for (final List<Liquidate> e : records) {
                        ClearBalanceJob.this.workerExectorService.execute(() -> {
                            try {
                                if (contract.getLiquidationDate().getTime() <= System.currentTimeMillis()) {
                                    return;
                                }
                                ClearBalanceJob.this.liquidateService.liquidate(contract, Worker.this.contractList, e);
                            } catch (final Exception ex) {
                                ClearBalanceJob.log.error("unexpected error", ex);
                            } finally {
                                latch.countDown();
                            }
                        });
                    }
                    latch.await();
                }
                ClearBalanceJob.log.info("liquidate user position, contractCode : {}, size : {}, spend : {} ms", contract.getContractCode(), size, (System.currentTimeMillis() - ts));
            } catch (final Exception e) {
                ClearBalanceJob.log.error("liquidate error", e);
            }
        }

        /**
         * 爆仓
         *
         * @param contract
         */
        void explosion(final Contract contract) {
            try {
                long id = 0;
                final long ts = System.currentTimeMillis();
                int size1 = 0;
                int size2 = 0;
                while (true) {
                    final List<Explosion> explosions = ClearBalanceJob.this.explosionService.listExplosion(id,
                            ClearBalanceJob.RECORD_SIZE, contract.getContractCode());

                    if (CollectionUtils.isEmpty(explosions)) {
                        break;
                    }
                    ClearBalanceJob.log.info("explosion worker : contractCode : {}, explosion list size : {}", contract.getContractCode(), explosions.size());
                    final Explosion maxIdRecord = explosions.stream().max(Comparator.comparing(Explosion::getId)).get();
                    id = Math.max(id, maxIdRecord.getId());
                    final List<Explosion> list1 = explosions.stream().filter(x -> x.getCloseOrderId() == null).collect(Collectors.toList());
                    final List<Explosion> list2 = explosions.stream().filter(x -> x.getCloseOrderId() != null).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(list1)) {
                        size1 += list1.size();
                        final List<List<Explosion>> records1 = this.sharding(list1, 200);
                        final CountDownLatch latch = new CountDownLatch(records1.size());
                        for (final List<Explosion> e : records1) {
                            ClearBalanceJob.this.workerExectorService.execute(() -> {
                                try {
                                    if (contract.getLiquidationDate().getTime() <= System.currentTimeMillis()) {
                                        return;
                                    }
                                    ClearBalanceJob.this.explosionService.explosion(contract, Worker.this.contractList, e);

                                } catch (final Exception ex) {
                                    ClearBalanceJob.log.error("unexpected error", ex);
                                } finally {
                                    latch.countDown();
                                }
                            });
                        }
                        latch.await();
                    }
                    size2 += list2.size();
                    final List<List<Explosion>> records2 = this.sharding(list2, 200);
                    for (final List<Explosion> e : records2) {
                        try {
                            if (contract.getLiquidationDate().getTime() <= System.currentTimeMillis()) {
                                return;
                            }
                            ClearBalanceJob.this.explosionService.explosion(contract, this.contractList, e);

                        } catch (final Exception ex) {
                            ClearBalanceJob.log.error("unexpected error", ex);
                        }
                    }
                }
                ClearBalanceJob.log.info("explosion user position, contractCode : {}, list1 size : {}, list2 size : {}, spend : {} ms", contract.getContractCode(), size1, size2, (System.currentTimeMillis() - ts));
            } catch (final Exception e) {
                ClearBalanceJob.log.error("explosion error", e);
            }
        }

        boolean tryLockContract(final Contract contract, final long second) throws InterruptedException {
            final long ts = System.currentTimeMillis();
            if (StringUtils.isBlank(contract.getMarketPrice())) {
                final List<MarkIndexPriceDTO> markIndexList = ClearBalanceJob.this.marketService.getMarkIndexList(this.contractList);
                if (CollectionUtils.isEmpty(markIndexList)) {
                    ClearBalanceJob.log.error("try lock contract : {} failed, get market price failed", contract.getContractCode());
                    return false;
                }
                contract.setMarketPrice(JSON.toJSONString(markIndexList));
            }
            ClearBalanceJob.this.contractService.lockContract(contract);
            while (!ClearBalanceJob.this.contractService.isMatchStoped(contract)) {
                if ((ts + second * 1000) < System.currentTimeMillis()) {
                    ClearBalanceJob.log.error("try lock contract : {} failed, matching update status timeout", contract.getContractCode());
                    return false;
                }
                TimeUnit.MILLISECONDS.sleep(20);
            }
            return true;
        }

        private <T> List<List<T>> sharding(final List<T> records, final int max) {
            if (CollectionUtils.isEmpty(records)) {
                return new ArrayList<>();
            }

            final int size = records.size();
            final List<List<T>> result = new LinkedList<>();
            int from = 0;
            while (from < size) {
                final int toIndex = size > from + max ? from + max : size;
                final List<T> temp = records.subList(from, toIndex);
                result.add(temp);
                from = toIndex;
            }
            return result;
        }

    }
}
