package cc.newex.wallet.service;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.CrudService;
import cc.newex.wallet.criteria.UtxoTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.UtxoTransaction;

import java.math.BigDecimal;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-03-31
 */
public interface UtxoTransactionService
        extends CrudService<UtxoTransaction, UtxoTransactionExample, Long> {
    /**
     * 获取utxo的balance总和
     *
     * @return
     */
    BigDecimal getTotalBalance(UtxoTransactionExample example, ShardTable table);

    int addOnDuplicateKey(UtxoTransaction tx, final ShardTable table);

    UtxoTransaction getByTxid(String txId, CurrencyEnum currencyEnum);

    int setSpentTxId(UtxoTransaction utxo, String spentTxid, CurrencyEnum currencyEnum);

    UtxoTransaction markAsSpent(UtxoTransactionExample example, CurrencyEnum currencyEnum);

}
