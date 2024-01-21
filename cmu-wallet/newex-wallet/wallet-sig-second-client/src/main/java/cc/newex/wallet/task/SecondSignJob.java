package cc.newex.wallet.task;

import cc.newex.wallet.annotation.StartThread;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.SignDataService;
import cc.newex.wallet.signature.api.ITransactionSignService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author newex-team
 * @data 01/04/2018
 */
@Slf4j
@Component
@StartThread
public class SecondSignJob implements Runnable {
    private final long WAIT_TIME = 10 * 1000L;

    @Autowired
    private ITransactionSignService signService;
    @Autowired
    private SignDataService dataService;


    @Override
    public void run() {

        while (true) {
            try {
                log.info("Signature second job begin");

                final List<WithdrawTransaction> withdrawTransactions = this.dataService.getWithdrawTx();

                if (!CollectionUtils.isEmpty(withdrawTransactions)) {
                    withdrawTransactions.parallelStream().forEach((transaction) -> {
                        try {
                            final JSONObject sigJson = JSONObject.parseObject(transaction.getSignature());

                            transaction.setBalanceStr(transaction.getBalance().toString());
                            final String secondSig = this.signService.signTransaction(transaction);
                            if (StringUtils.isEmpty(secondSig)) {
                                sigJson.put("valid", false);
                            } else {
                                sigJson.put("rawTransaction", secondSig);
                                sigJson.remove("firstSignTx");
                                sigJson.put("valid", true);
                            }
                            transaction.setSignature(sigJson.toJSONString());

                            //final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
                        } catch (final Throwable e) {
                            log.error("Signature second error",e);
                        }
                        //final String txId = this.signService.getTxId(secondSig, currency);
                        //transaction.setTxId(txId);
                    });
                    this.dataService.postWithdrawTx(withdrawTransactions);
                    continue;
                }

                log.info("Signature second job end");
            } catch (final Throwable e) {
                log.error("Signature second job error", e);
            }

            try {
                Thread.sleep(this.WAIT_TIME);
            } catch (final InterruptedException e) {
                log.error("Signature second job quit", e);
                break;
            }

        }

    }
}
