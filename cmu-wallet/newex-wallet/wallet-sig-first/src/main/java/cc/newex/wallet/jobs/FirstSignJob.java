package cc.newex.wallet.jobs;

import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.SignContent;
import cc.newex.wallet.annotation.StartThread;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.ISignService;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author newex-team
 * @data 02/04/2018
 */
@Component
@StartThread
@Slf4j
public class FirstSignJob implements Runnable {

    private final long WAIT_TIME = 10 * 1000L;
    @Autowired
    SignContent signContent;

    @Override
    public void run() {
        FirstSignJob.log.info("Signature first job begin");
        final String key = Constants.WALLET_WITHDRAW_SIG_FIRST_KEY;
        final String tmp = Constants.WALLET_WITHDRAW_SIG_FIRST_TMP_KEY;
        while (true) {
            try {
                if (ObjectUtils.isEmpty(Constants.NET_PARAMS)) {
                    FirstSignJob.log.info("Constants.NET_PARAMS has not been init");
                    Thread.sleep(this.WAIT_TIME);
                    continue;
                }
                final String txStr = REDIS.rPoplPush(key, tmp);
                if (!ObjectUtils.isEmpty(txStr)) {

                    final JSONObject txJson = JSONObject.parseObject(txStr);
                    final WithdrawTransaction transaction = txJson.toJavaObject(WithdrawTransaction.class);
                    final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
                    final ISignService signService = this.signContent.getSignService(currency);
                    signService.signTransaction(transaction);
                    final String signatureStr = transaction.getSignature();
                    final JSONObject sigJson = JSONObject.parseObject(signatureStr);
                    final String rKey;
                    if (sigJson.getBoolean("valid")) {
                        rKey = Constants.WALLET_WITHDRAW_SIG_SECOND_KEY;
                    } else {
                        //如果签名失败，直接推送到已完成队列，不需要再进行第二次签名
                        rKey = Constants.WALLET_WITHDRAW_SIG_DONE_KEY;
                    }
                    REDIS.lPush(rKey, JSONObject.toJSONString(transaction));
                    REDIS.lPop(tmp);
                    continue;
                }

            } catch (final DataAccessException e) {
                FirstSignJob.log.info("Signature first job error", e);

            } catch (final Throwable e) {
                FirstSignJob.log.error("Signature first job quit", e);
                break;
            }

            try {
                Thread.sleep(this.WAIT_TIME);
            } catch (final InterruptedException e) {
                FirstSignJob.log.info("Signature first job quit", e);
                break;
            }

        }

        FirstSignJob.log.info("Signature first job end");
    }
}
