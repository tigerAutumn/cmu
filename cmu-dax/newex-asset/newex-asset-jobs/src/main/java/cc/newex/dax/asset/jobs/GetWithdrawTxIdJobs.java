package cc.newex.dax.asset.jobs;

import cc.newex.commons.redis.REDIS;
import cc.newex.dax.asset.jobs.service.WithdrawService;
import cc.newex.wallet.annotation.StartThread;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author newex-team
 * @data 06/04/2018
 */
@Component
@Slf4j
@StartThread
public class GetWithdrawTxIdJobs implements Runnable {
    private final long COUNT = 100L;

    private final long WAIT_TIME = 30 * 1000L;

    @Autowired
    private WithdrawService withdrawService;

    @Override
    public void run() {
        log.info("WithJobs begin");
        String key = Constants.WALLET_WITHDRAW_TX_BIZ_KEY;
        long threeMinutes = 3 * 60;
        String lockKey = Constants.WALLET_WITHDRAW_TX_LOCK_KEY;
        while (true) {
            try {

                boolean lock = REDIS.setNX(lockKey, "lock");
                if (lock) {
                    REDIS.expire(lockKey, threeMinutes);
                    List<String> withdrawStr = REDIS.lRange(key, 0L, this.COUNT);
                    if (CollectionUtils.isEmpty(withdrawStr)) {
                        REDIS.del(lockKey);
                        Thread.sleep(this.WAIT_TIME);
                    } else {
                        withdrawStr.parallelStream().forEach((str) -> {
                            JSONObject json = JSONObject.parseObject(str);
                            WithdrawRecord record = json.toJavaObject(WithdrawRecord.class);
                            this.withdrawService.fillWithdrawTxId(record);
                        });
                        REDIS.lTrim(key, withdrawStr.size(), -1L);
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
                log.error("WithJobs quit", e);
                break;
            } catch (DataAccessException e) {
                log.error("WithJobs error", e);

            } catch (Throwable e) {
                REDIS.del(lockKey);
                log.error("WithJobs error", e);
            }

        }


    }
}
