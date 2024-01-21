package cc.newex.wallet.dao;

import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.wallet.criteria.WithdrawTransactionExample;
import cc.newex.wallet.pojo.WithdrawTransaction;
import org.springframework.stereotype.Repository;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018-04-01
 */
@Repository
public interface WithdrawTransactionRepository
        extends CrudRepository<WithdrawTransaction, WithdrawTransactionExample, Integer> {
}