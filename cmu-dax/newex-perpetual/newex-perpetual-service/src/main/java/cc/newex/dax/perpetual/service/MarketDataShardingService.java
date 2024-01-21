package cc.newex.dax.perpetual.service;

import java.math.BigDecimal;
import java.util.List;

import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.CrudService;
import cc.newex.dax.perpetual.criteria.MarketDataShardingExample;
import cc.newex.dax.perpetual.domain.MarketData;
import cc.newex.dax.perpetual.domain.redis.DepthCacheBean;

/**
 * K线 服务接口
 *
 * @author newex-team
 * @date 2018-10-30 18:50:01
 */
public interface MarketDataShardingService
    extends CrudService<MarketData, MarketDataShardingExample, Long> {

  /**
   * 建表
   */
  int createMarketDataIfNotExists(final ShardTable shardTable);

  /**
   * 获取最高价格
   */
  BigDecimal getHighPrice(final MarketDataShardingExample example, final ShardTable shardTable);

  /**
   * 获取最低价格
   */
  BigDecimal getLowPrice(final MarketDataShardingExample example, final ShardTable shardTable);

  /**
   * 查询总张数
   */
  BigDecimal selectSumAmount(final MarketDataShardingExample example, final ShardTable shardTable);

  /**
   * 查询总价值
   */
  BigDecimal selectSumSize(final MarketDataShardingExample example, final ShardTable shardTable);

  /**
   * 获取深度数据
   *
   * @param contractCode
   * @return
   */
  DepthCacheBean getDepthCacheBean(String contractCode);

  /**
   * 取得K线数据，默认从redis中取数据，如果Redis中不存在。则从DB中加载 如果数据库中加载开关打开，直接从DB中加载
   */
  List<MarketData> getByType(final KlineEnum klineEnum, String pairCode);
}
