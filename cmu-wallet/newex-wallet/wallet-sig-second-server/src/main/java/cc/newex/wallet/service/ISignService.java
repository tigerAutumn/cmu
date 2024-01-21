package cc.newex.wallet.service;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.WithdrawTransaction;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * @author newex-team
 * @data 02/04/2018
 */

public interface ISignService {
    /**
     * 返回签名结果
     *
     * @param transaction
     * @return
     */
    String signTransaction(WithdrawTransaction transaction);

    /**
     * 获取currency对应的签名类
     *
     * @return
     */
    CurrencyEnum getCurrency();


    default List<String> genrateNeedAddress(JSONObject param) {
        return new LinkedList<>();
    }

    /**
     * 根据rawTx，解析txid
     *
     * @param rawTx
     * @return
     */
    //String getTxId(String rawTx);
}
