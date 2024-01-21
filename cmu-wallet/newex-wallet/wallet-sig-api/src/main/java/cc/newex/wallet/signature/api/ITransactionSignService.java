package cc.newex.wallet.signature.api;


import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.signature.annotation.HessianClient;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author newex-team
 * @create 2018-11-27 下午4:45
 **/
@HessianClient("cc.newex.wallet.service.TransactionSignService")
public interface ITransactionSignService {

    /**
     * 返回二次签名结果
     *
     * @param transaction
     * @return
     */
    String signTransaction(WithdrawTransaction transaction);

    List<String> generateNeedAddress(JSONObject param);

    /**
     * 根据txId获得txId
     *
     * @param rawTransaction
     * @param currency
     * @return
     */
    //String getTxId(String rawTransaction, CurrencyEnum currency);
}
