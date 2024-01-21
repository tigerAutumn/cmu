package cc.newex.wallet.jobs.withdraw;

import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.annotation.StartThread;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.service.TransactionService;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 01/04/2018
 * withdrawJob 要持续运行,所以不用定时任务
 * 去从队列中获取待提现记录
 */
@Component
@StartThread
@Slf4j
public class GetWithdrawRecordJob implements Runnable {

    private final long COUNT = 100L;

    private final long WAIT_TIME = 30 * 1000L;

    @Autowired
    private TransactionService txService;

    @Override
    public void run() {
        log.info("get waiting for withdraw begin");
        String key = Constants.WALLET_WITHDRAW_WAIT_KEY;
        String failKey = Constants.WALLET_WITHDRAW_FAIL_KEY;
        while (true) {
            try {

                List<String> withdrawStr = REDIS.lRange(key, 0L, this.COUNT);
                if (!CollectionUtils.isEmpty(withdrawStr)) {
                    Set<WithdrawRecord> withdrawRecordSet = withdrawStr.parallelStream().map((str) -> {
                        JSONObject json = JSONObject.parseObject(str);
                        WithdrawRecord record = json.toJavaObject(WithdrawRecord.class);
                        return record;
                    }).collect(Collectors.toSet());

                    withdrawRecordSet.parallelStream().forEach((record) -> {
                        boolean success = false;
                        try {
                            success = this.txService.withdraw(record);
                        } catch (final Throwable e) {
                            log.error("withdraw error,currency:{},address:{}", record.getCurrency(), record.getAddress(), e);
                        }
                        if (!success) {
                            REDIS.lPush(failKey, JSONObject.toJSONString(record));
                        }
                    });
                    REDIS.lTrim(key, withdrawStr.size(), -1L);
                    continue;
                }
            } catch (DataAccessException e) {
                log.info("get waiting for withdraw error", e);

            } catch (Throwable e) {
                log.info("get waiting for withdraw quit", e);
                break;
            }
            try {
                Thread.sleep(this.WAIT_TIME);
            } catch (InterruptedException e) {
                log.info("get waiting for withdraw quit", e);
                break;
            }
        }
    }
}
