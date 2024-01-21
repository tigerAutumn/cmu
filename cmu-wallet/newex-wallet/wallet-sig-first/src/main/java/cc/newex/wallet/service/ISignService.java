package cc.newex.wallet.service;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.WithdrawTransaction;

/**
 * @author newex-team
 * @data 02/04/2018
 */

public interface ISignService {

    /**
     * 交易签名
     *
     * @param transaction
     */
    void signTransaction(WithdrawTransaction transaction);

    /**
     * 获取currency对应的签名类
     *
     * @return
     */
    CurrencyEnum getCurrency();
}
