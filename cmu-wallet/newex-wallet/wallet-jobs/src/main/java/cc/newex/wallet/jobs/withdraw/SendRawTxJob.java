package cc.newex.wallet.jobs.withdraw;

import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.annotation.StartThread;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.TransactionService;
import cc.newex.wallet.utils.Constants;
import com.alibaba.fastjson.JSONObject;
import com.googlecode.jsonrpc4j.JsonRpcClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author newex-team
 * @data 01/04/2018
 */
@Component
@StartThread
@Slf4j
public class SendRawTxJob implements Runnable {

    private final long COUNT = 100L;

    private final long WAIT_TIME = 30 * 1000L;

    @Autowired
    private TransactionService txService;

    @Override
    public void run() {
        SendRawTxJob.log.info("send withdraw begin");
        final String key = Constants.WALLET_WITHDRAW_SIG_DONE_KEY;
        while (true) {
            try {
                final List<String> withdrawStr = REDIS.lRange(key, 0L, this.COUNT);
                if (!CollectionUtils.isEmpty(withdrawStr)) {
                    withdrawStr.parallelStream().forEach((str) -> {
                        final JSONObject json = JSONObject.parseObject(str);
                        final WithdrawTransaction transaction = json.toJavaObject(WithdrawTransaction.class);
                        this.txService.sendWithdrawTransaction(transaction);

                    });
                    REDIS.lTrim(key, withdrawStr.size(), -1L);
                    continue;
                }

            } catch (DataAccessException | JsonRpcClientException e) {
                SendRawTxJob.log.info("send raw withdraw error", e);

            } catch (final Throwable e) {
                SendRawTxJob.log.info("send raw withdraw quit", e);
                break;
            }

            try {
                Thread.sleep(this.WAIT_TIME);
            } catch (final InterruptedException e) {
                SendRawTxJob.log.info("send raw withdraw quit", e);
                break;
            }

        }

    }


}
