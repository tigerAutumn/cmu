package cc.newex.wallet.service;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.signature.annotation.HessianService;
import cc.newex.wallet.signature.api.ITransactionSignService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author newex-team
 * @create 2018-11-27 下午4:56
 **/
@Slf4j
@HessianService
public class TransactionSignService implements ITransactionSignService {


    @Override
    public String signTransaction(WithdrawTransaction transaction) {
        TransactionSignService.log.info("{} signTransaction begin", transaction.getCurrency());
        String sig;
        try {
            transaction.setBalance(new BigDecimal(transaction.getBalanceStr()));
            CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
            ISignService signService = SignContent.getSignService(currency);
            sig = signService.signTransaction(transaction);
            log.info("{} signTransaction end", transaction.getCurrency());
        } catch (Throwable e) {
            sig = "";
            log.error("signTransaction error", e);
        }
        return sig;
    }

    @Override
    public List<String> generateNeedAddress(JSONObject param) {
        TransactionSignService.log.info("generateNeedAddress begin");

        try {
            String currency = param.getString("currency");
            CurrencyEnum currencyEnum = CurrencyEnum.parseName(currency);
            ISignService signService = SignContent.getSignService(currencyEnum);
            return signService.genrateNeedAddress(param);
        } catch (Throwable e) {
            log.error("generateNeedAddress error", e);
            return null;
        }
    }

    /**
     * 根据txId获得txId
     *
     * @param rawTransaction
     * @return
     */
//    @Override
//    public String getTxId(final String rawTransaction, final CurrencyEnum currency) {
//        TransactionSignService.log.info("getTxId begin");
//        final ISignService signService = SignContent.getSignService(currency);
//        final String txId = signService.getTxId(rawTransaction);
//        TransactionSignService.log.info("getTxId end");
//        return txId;
//    }

}
