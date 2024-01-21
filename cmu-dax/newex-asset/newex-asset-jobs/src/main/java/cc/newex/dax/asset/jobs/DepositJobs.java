package cc.newex.dax.asset.jobs;

import cc.newex.commons.redis.REDIS;
import cc.newex.dax.asset.jobs.service.DepositService;
import cc.newex.wallet.annotation.StartThread;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @data 06/04/2018
 */
@Component
@Slf4j
@StartThread
public class DepositJobs implements Runnable {
    private final long COUNT = 50L;

    private final long WAIT_TIME = 30 * 1000L;

    @Autowired
    DepositService depositService;

    @Autowired
    REDIS redis;

    @Override
    public void run() {
        DepositJobs.log.info("DepositJobs begin");
        final String key = Constants.WALLET_DEPOSIT_KEY;
        long threeMinutes = 3 * 60;
        String lockKey = Constants.WALLET_DEPOSIT_LOCK_KEY;
        while (true) {
            try {
                boolean lock = REDIS.setNX(lockKey, "lock");
                if (lock) {
                    //this.depositService.retryNotifyDeposit();
                    REDIS.expire(lockKey, threeMinutes);

                    //再去队列中查新的充值记录
                    final List<String> depositStr = REDIS.lRange(key, 0L, this.COUNT);
                    if (CollectionUtils.isEmpty(depositStr)) {
                        REDIS.del(lockKey);
                        Thread.sleep(this.WAIT_TIME);
                    } else {
                        Map<String, TransactionDTO> txMap = new LinkedHashMap<>(50);
                        depositStr.forEach((str) -> {
                            final JSONObject json = JSONObject.parseObject(str);
                            final TransactionDTO tx = json.toJavaObject(TransactionDTO.class);
                            if (txMap.containsKey(tx.getTxId())) {
                                if (txMap.get(tx.getTxId()).getConfirmNum() < tx.getConfirmNum()) {
                                    txMap.put(tx.getTxId(), tx);
                                }
                            } else {
                                txMap.put(tx.getTxId(), tx);
                            }

                        });
                        txMap.entrySet().parallelStream().forEach((entry) -> {
                            this.depositService.deposit(entry.getValue());
                        });

                        REDIS.lTrim(key, depositStr.size(), -1L);
                        REDIS.del(lockKey);
                    }
                } else {
                    if (REDIS.ttl(lockKey) < 0) {
                        REDIS.expire(lockKey, threeMinutes);
                    }
                    Thread.sleep(this.WAIT_TIME);
                }

            } catch (InterruptedException e) {
                REDIS.del(lockKey);
                DepositJobs.log.info("DepositJobs quit", e);
                break;
            } catch (final DataAccessException e) {
                DepositJobs.log.info("DepositJobs error", e);

            } catch (final Throwable e) {
                REDIS.del(lockKey);
                DepositJobs.log.info("DepositJobs error", e);
            }

        }


    }
}
