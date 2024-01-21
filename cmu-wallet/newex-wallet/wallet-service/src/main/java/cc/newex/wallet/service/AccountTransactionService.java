package cc.newex.wallet.service;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.CrudService;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.pojo.AccountTransaction;

import java.math.BigDecimal;


/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-04-12
 */
public interface AccountTransactionService
        extends CrudService<AccountTransaction, AccountTransactionExample, Long> {
    BigDecimal getTotalBalance(AccountTransactionExample example, ShardTable table);

    int addOnDuplicateKey(AccountTransaction tx, ShardTable table);
}
