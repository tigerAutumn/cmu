package cc.newex.wallet.jobs.withdraw;

import cc.newex.commons.redis.REDIS;
import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.service.TransactionService;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 18/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "1 1/1 * * * ?")
@Slf4j
public class RetryFailedWithdraw implements SimpleJob {

    @Autowired
    private TransactionService txService;

    @Override
    public void execute(final ShardingContext shardingContext) {
        RetryFailedWithdraw.log.info("RetryFailedWithdraw begin");
        final String failKey = Constants.WALLET_WITHDRAW_FAIL_KEY;
        final String failKeyTmp = Constants.WALLET_WITHDRAW_FAIL_KEY_TMP;

        try {
            Long len = REDIS.lLen(failKey);
            while (len > 0) {
                final String str = REDIS.rPoplPush(failKey, failKeyTmp);
                boolean success = false;
                try {
                    final JSONObject json = JSONObject.parseObject(str);
                    final WithdrawRecord record = json.toJavaObject(WithdrawRecord.class);
                    success = this.txService.withdraw(record);
                } catch (final Throwable e) {
                    RetryFailedWithdraw.log.error("retry withdraw error,record:{}", str, e);
                }
                if (success) {
                    REDIS.lPop(failKeyTmp);
                } else {
                    REDIS.rPoplPush(failKeyTmp, failKey);
                }

                len = len - 1;
            }

        } catch (final Throwable e) {
            RetryFailedWithdraw.log.error("RetryFailedWithdraw error", e);
        }


    }
}
