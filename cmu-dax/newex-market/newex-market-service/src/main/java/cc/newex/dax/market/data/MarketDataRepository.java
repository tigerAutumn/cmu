package cc.newex.dax.market.data;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.dax.market.criteria.MarketDataExample;
import cc.newex.dax.market.domain.MarketData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *  数据访问类
 * @author newex-team
 * @date 2018/03/18
 */
@Repository
public interface MarketDataRepository
    extends CrudRepository<MarketData, MarketDataExample, Long> {

   int insertMarketDataWithId(@Param("record") MarketData record, @Param("shardTable") ShardTable shardTable);
}