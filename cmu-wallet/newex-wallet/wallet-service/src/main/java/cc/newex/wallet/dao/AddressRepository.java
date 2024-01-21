package cc.newex.wallet.dao;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.pojo.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018-03-27
 */
@Repository
public interface AddressRepository
        extends CrudRepository<Address, AddressExample, Integer> {
    Address selectAndLockOneByExample(@Param("example") AddressExample example, @Param("shardTable") ShardTable table);

    BigDecimal getTotalBalance(@Param("example") AddressExample example, @Param("shardTable") ShardTable table);

}