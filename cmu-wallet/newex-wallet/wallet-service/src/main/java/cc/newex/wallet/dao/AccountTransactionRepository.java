package cc.newex.wallet.dao;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.pojo.AccountTransaction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018-04-12
 */
@Repository
public interface AccountTransactionRepository
        extends CrudRepository<AccountTransaction, AccountTransactionExample, Long> {
    BigDecimal getTotalBalance(@Param("example") AccountTransactionExample example, @Param("shardTable") ShardTable table);

    int insertOnDuplicateKey(@Param("record") AccountTransaction tx, @Param("shardTable") ShardTable table);
}