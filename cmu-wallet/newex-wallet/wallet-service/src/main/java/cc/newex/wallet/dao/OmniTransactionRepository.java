package cc.newex.wallet.dao;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.wallet.criteria.OmniTransactionExample;
import cc.newex.wallet.pojo.OmniTransaction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018-04-08
 */
@Repository
public interface OmniTransactionRepository
        extends CrudRepository<OmniTransaction, OmniTransactionExample, Long> {
    int insertOnDuplicateKey(@Param("record") OmniTransaction omni, @Param("shardTable") ShardTable table);

    /**
     * 获取utxo的balance总和
     *
     * @return
     */
    BigDecimal getTotalBalance(@Param("example") OmniTransactionExample example, @Param("shardTable") ShardTable table);
}