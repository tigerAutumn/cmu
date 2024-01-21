package cc.newex.dax.perpetual.service.impl;

import static cc.newex.dax.perpetual.common.constant.PerpetualConstants.KLINE_SIZE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.AbstractCrudService;
import cc.newex.dax.perpetual.common.constant.PerpetualCacheKeys;
import cc.newex.dax.perpetual.criteria.MarketDataShardingExample;
import cc.newex.dax.perpetual.data.MarketDataShardingRepository;
import cc.newex.dax.perpetual.domain.MarketData;
import cc.newex.dax.perpetual.domain.redis.DepthCacheBean;
import cc.newex.dax.perpetual.service.MarketDataShardingService;
import cc.newex.dax.perpetual.service.cache.CacheService;

/**
 * K线 服务实现
 *
 * @author newex-team
 * @date 2018-10-30 18:50:01
 */
@Service
public class MarketDataShardingServiceImpl extends
    AbstractCrudService<MarketDataShardingRepository, MarketData, MarketDataShardingExample, Long>
    implements MarketDataShardingService {

  private static final String TABLE_NAME = "market_data";
  @Autowired
  private MarketDataShardingRepository marketDataRepository;
  @Autowired
  private CacheService cacheService;

  @Override
  protected MarketDataShardingExample getPageExample(final String fieldName, final String keyword) {
    final MarketDataShardingExample example = new MarketDataShardingExample();
    example.createCriteria().andFieldLike(fieldName, keyword);
    return example;
  }

  @Override
  public int createMarketDataIfNotExists(final ShardTable shardTable) {
    return this.marketDataRepository.createMarketDataIfNotExists(shardTable);
  }

  @Override
  public BigDecimal getHighPrice(final MarketDataShardingExample example,
      final ShardTable shardTable) {
    return this.marketDataRepository.getHighPrice(example, shardTable);
  }

  @Override
  public BigDecimal getLowPrice(final MarketDataShardingExample example,
      final ShardTable shardTable) {
    return this.marketDataRepository.getLowPrice(example, shardTable);
  }

  @Override
  public BigDecimal selectSumAmount(final MarketDataShardingExample example,
      final ShardTable shardTable) {
    return this.marketDataRepository.selectSumAmount(example, shardTable);
  }

  @Override
  public BigDecimal selectSumSize(final MarketDataShardingExample example,
      final ShardTable shardTable) {
    return this.marketDataRepository.selectSumSize(example, shardTable);
  }

  @Override
  public DepthCacheBean getDepthCacheBean(final String contractCode) {
    final String latestResult =
        this.cacheService.getCacheValue(PerpetualCacheKeys.getDepthKey(contractCode));
    if (latestResult == null) {
      return new DepthCacheBean();
    }
    return JSONObject.parseObject(latestResult, DepthCacheBean.class);
  }

  /**
   * 取得K线数据，默认从redis中取数据，如果Redis中不存在。则从DB中加载 如果数据库中加载开关打开，直接从DB中加载
   *
   * @param klineEnum K线类型
   */
  @Override
  public List<MarketData> getByType(final KlineEnum klineEnum, final String pairCode) {
    final String klineJson = this.cacheService
        .getCacheValue(PerpetualCacheKeys.getKlineKey(pairCode, klineEnum.getType()));
    // 缓存为空
    final String reloadSwitch =
        this.cacheService.getCacheValue(PerpetualCacheKeys.KLINE_RELOAD_SWITCH);
    final List<MarketData> marketDatas;
    if (StringUtils.isEmpty(klineJson) || "1".equalsIgnoreCase(reloadSwitch)) {
      marketDatas = this.getMarketDatas(klineEnum.getType(), KLINE_SIZE, pairCode);
    } else {
      marketDatas = JSONArray.parseArray(klineJson, MarketData.class);
    }
    return marketDatas == null ? new ArrayList<>() : marketDatas;
  }

  public List<MarketData> getMarketDatas(final Integer type, final int pageSize,
      final String pairCode) {
    final MarketDataShardingExample marketDataExample = new MarketDataShardingExample();
    marketDataExample.setOrderByClause(" id desc limit 0," + pageSize);
    marketDataExample.createCriteria().andTypeEqualTo(type);
    final List<MarketData> marketDatas = this.getByExample(marketDataExample,
        MarketDataShardingServiceImpl.buildShardTable(pairCode));
    if (!CollectionUtils.isEmpty(marketDatas)) {
      Collections.reverse(marketDatas);
    }
    return marketDatas;
  }

  private static ShardTable buildShardTable(final String pairCode) {
    return ShardTable.builder().prefix(MarketDataShardingServiceImpl.TABLE_NAME).name(pairCode)
        .build();
  }
}
