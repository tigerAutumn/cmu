package cc.newex.wallet.service;

import cc.newex.commons.mybatis.sharding.service.CrudService;
import cc.newex.wallet.criteria.WithdrawTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.WithdrawTransaction;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-04-01
 */
public interface WithdrawTransactionService
        extends CrudService<WithdrawTransaction, WithdrawTransactionExample, Integer> {

    WithdrawTransaction getByTxId(String txid, CurrencyEnum currencyEnum);

}
