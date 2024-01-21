package cc.newex.dax.perpetual.data;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.MarketDataShardingExample;
import cc.newex.dax.perpetual.domain.MarketData;

/**
 * K线 数据访问类
 *
 * @author newex -team
 * @date 2018 -10-30 18:50:01
 */
@Repository
public interface MarketDataShardingRepository
    extends CrudRepository<MarketData, MarketDataShardingExample, Long> {

  /**
   * 建表
   *
   * @param shardTable the shard table
   * @return the int
   */
  int createMarketDataIfNotExists(@Param("shardTable") final ShardTable shardTable);

  /**
   * 获取最高价格
   *
   * @param example the example
   * @param shardTable the shard table
   * @return the high price
   */
  BigDecimal getHighPrice(@Param("example") MarketDataShardingExample example,
      @Param("shardTable") ShardTable shardTable);

  /**
   * 获取最低价格
   *
   * @param example the example
   * @param shardTable the shard table
   * @return the low price
   */
  BigDecimal getLowPrice(@Param("example") MarketDataShardingExample example,
      @Param("shardTable") ShardTable shardTable);

  /**
   * 获取总张数
   *
   * @param example the example
   * @param shardTable the shard table
   * @return the big decimal
   */
  BigDecimal selectSumAmount(@Param("example") MarketDataShardingExample example,
      @Param("shardTable") ShardTable shardTable);

  /**
   * 获取总价值
   *
   * @param example the example
   * @param shardTable the shard table
   * @return the big decimal
   */
  BigDecimal selectSumSize(@Param("example") MarketDataShardingExample example,
      @Param("shardTable") ShardTable shardTable);

}
