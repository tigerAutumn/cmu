package cc.newex.wallet.dao;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.wallet.criteria.UtxoTransactionExample;
import cc.newex.wallet.pojo.UtxoTransaction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018-03-31
 */
@Repository
public interface UtxoTransactionRepository
        extends CrudRepository<UtxoTransaction, UtxoTransactionExample, Long> {
    /**
     * 获取utxo的balance总和
     *
     * @return
     */
    BigDecimal getTotalBalance(@Param("example") UtxoTransactionExample example, @Param("shardTable") ShardTable table);

    int insertOnDuplicateKey(@Param("record") UtxoTransaction tx, @Param("shardTable") ShardTable table);

}