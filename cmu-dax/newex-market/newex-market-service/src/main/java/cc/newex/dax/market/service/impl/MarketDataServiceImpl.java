package cc.newex.dax.market.service.impl;

import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.dax.market.common.enums.CoinConfigTypeEnum;
import cc.newex.dax.market.common.util.DateUtils;
import cc.newex.dax.market.common.util.StringUtil;
import cc.newex.dax.market.criteria.MarketDataExample;
import cc.newex.dax.market.data.MarketDataRepository;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.domain.MarketData;
import cc.newex.dax.market.domain.MarketIndex;
import cc.newex.dax.market.service.MarketDataService;
import cc.newex.dax.market.service.MarketIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service("marketDataService")
public class MarketDataServiceImpl extends MarketDataOuterBaseServiceImpl implements MarketDataService {

    final String MARKET_DATA = "market_data_";
    @Autowired
    MarketDataRepository marketDataRepository;
    @Autowired
    MarketIndexService marketIndexService;

    @Override
    public List<MarketData> getLastListByTypeAndLimit(final CoinConfig coinConfig, final int type, final int limit) {
        final MarketDataExample marketDataExample = new MarketDataExample();
        final MarketDataExample.Criteria criteria = marketDataExample.createCriteria();
        final ShardTable shardTable = this.getShardTable(coinConfig);
        criteria.andTypeEqualTo(type);
        final PageInfo pageInfo = new PageInfo();
        pageInfo.setStartIndex(0);
        pageInfo.setPageSize(limit);
        return this.marketDataRepository.selectByExample(marketDataExample, shardTable);
    }

    @Override
    public List<MarketData> getLastMarketDataByType(final CoinConfig coinConfig, final int type, final Date date) {
        final MarketDataExample marketDataExample = new MarketDataExample();
        final MarketDataExample.Criteria criteria = marketDataExample.createCriteria();
        final ShardTable shardTable = this.getShardTable(coinConfig);
        criteria.andTypeEqualTo(type);
        criteria.andCreatedDateGreaterThanOrEqualTo(date);
        return this.marketDataRepository.selectByExample(marketDataExample, shardTable);
    }

    @Override
    public List<MarketData> getListByType(final CoinConfig coinConfig, final int type, final int pageSize) {
        final MarketDataExample marketDataExample = new MarketDataExample();
        final MarketDataExample.Criteria criteria = marketDataExample.createCriteria();
        final ShardTable shardTable = this.getShardTable(coinConfig);
        criteria.andTypeEqualTo(type);
        marketDataExample.setOrderByClause("created_date desc limit 0," + pageSize);
        return this.marketDataRepository.selectByExample(marketDataExample, shardTable);
    }

    @Override
    public MarketData getLastListByType(final CoinConfig coinConfig, final int type) {
        final MarketDataExample marketDataExample = new MarketDataExample();
        final MarketDataExample.Criteria criteria = marketDataExample.createCriteria();
        final ShardTable shardTable = this.getShardTable(coinConfig);
        criteria.andTypeEqualTo(type);
        final PageInfo pageInfo = new PageInfo();
        pageInfo.setStartIndex(0);
        pageInfo.setPageSize(1);
        pageInfo.setSortItem("id");
        pageInfo.setSortType("DESC");
        final List<MarketData> list = this.marketDataRepository.selectByPager(pageInfo, marketDataExample, shardTable);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public int updateMarketDataById(final CoinConfig coinConfig, final MarketData marketData) {
        final ShardTable shardTable = this.getShardTable(coinConfig);
        return this.marketDataRepository.updateById(marketData, shardTable);
    }

    @Override
    public long updateMarketData(final CoinConfig coinConfig, final MarketData marketData, final double open, final double high, final double low,
                                 final double close, final double volume) {
        final MarketDataExample marketDataExample = new MarketDataExample();
        final MarketDataExample.Criteria criteria = marketDataExample.createCriteria();
        final ShardTable shardTable = this.getShardTable(coinConfig);
        criteria.andIdEqualTo(marketData.getId());
        marketData.setOpen(open);
        marketData.setHigh(high);
        marketData.setLow(low);
        marketData.setClose(close);
        marketData.setVolume(volume);
        return this.marketDataRepository.updateByExample(marketData, marketDataExample, shardTable);
    }

    @Override
    public long updateMarketData(final CoinConfig coinConfig, final MarketData marketData) {
        final MarketDataExample marketDataExample = new MarketDataExample();
        final MarketDataExample.Criteria criteria = marketDataExample.createCriteria();
        final ShardTable shardTable = this.getShardTable(coinConfig);
        criteria.andIdEqualTo(marketData.getId());
        return this.marketDataRepository.updateByExample(marketData, marketDataExample, shardTable);
    }

    private ShardTable getShardTable(final CoinConfig coinConfig) {
        final ShardTable shardTable = new ShardTable();
        shardTable.setName(this.MARKET_DATA + coinConfig.getIndexMarketFrom());
        return shardTable;
    }

    @Override
    public int insertMarketData(final CoinConfig coinConfig, final int marketFrom, final double open, final double high, final double low,
                                final double close, final double volume, final int type,
                                final Date createdDate) {
        final MarketData marketData = MarketData.builder().marketFrom(marketFrom).open(open).high(high).low(low).close(
                close).volume(volume).type(type).createdDate(createdDate).build();
        return this.marketDataRepository.insert(marketData, this.getShardTable(coinConfig));
    }

    @Override
    public int insertMarketDataWithId(final CoinConfig coinConfig, final MarketData marketData) {
        return this.marketDataRepository.insertMarketDataWithId(marketData, this.getShardTable(coinConfig));
    }

    @Override
    public int insertMarketData(final CoinConfig coinConfig, final MarketData lastTicker) {
        return this.marketDataRepository.insert(lastTicker, this.getShardTable(coinConfig));
    }

    @Override
    public MarketData getTodayOpenPrice(final CoinConfig coinConfig, final Date day) {
        final MarketDataExample marketDataExample = new MarketDataExample();
        final MarketDataExample.Criteria criteria = marketDataExample.createCriteria();
        final ShardTable shardTable = this.getShardTable(coinConfig);
        criteria.andTypeEqualTo(0).andCreatedDateGreaterThan(day);
        marketDataExample.setOrderByClause("created_date LIMIT 1");
        final List<MarketData> list = this.marketDataRepository.selectByExample(marketDataExample, shardTable);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public List<MarketData> getByTypeAndDate(final CoinConfig coinConfig, final int type, final Date date, final int limit) {
        final MarketDataExample marketDataExample = new MarketDataExample();
        final MarketDataExample.Criteria criteria = marketDataExample.createCriteria();
        final ShardTable shardTable = this.getShardTable(coinConfig);
        criteria.andTypeEqualTo(type);
        criteria.andCreatedDateLessThan(date);
        final PageInfo pageInfo = new PageInfo();
        pageInfo.setStartIndex(0);
        pageInfo.setPageSize(limit);
        pageInfo.setSortItem("id");
        pageInfo.setSortType("DESC");
        return this.marketDataRepository.selectByPager(pageInfo, marketDataExample, shardTable);
    }

    @Override
    public void deleteMarketDataLessThanId(final Long id, final CoinConfig coinConfig) {
        final MarketDataExample marketDataExample = new MarketDataExample();
        final MarketDataExample.Criteria criteria = marketDataExample.createCriteria();
        criteria.andIdLessThan(id);
        final ShardTable shardTable = this.getShardTable(coinConfig);
        this.marketDataRepository.deleteByExample(marketDataExample, shardTable);
    }

    @Override
    public int deleteMarketData(final CoinConfig coinConfig, final Date date, final int type) {
        final MarketDataExample marketDataExample = new MarketDataExample();
        final MarketDataExample.Criteria criteria = marketDataExample.createCriteria();
        criteria.andTypeEqualTo(type);
        criteria.andCreatedDateLessThan(date);
        final ShardTable shardTable = this.getShardTable(coinConfig);
        return this.marketDataRepository.deleteByExample(marketDataExample, shardTable);
    }

    @Override
    public List<String[]> getMarketDataFromCacheArray(final Integer marketFrom, final int type, final int limit, final long unixTime) {
        final List<String[]> array = this.getMarketCacheDataArray(marketFrom, type);
        List<String[]> resultArr;
        if (CollectionUtils.isEmpty(array)) {
            return null;
        }
        if (limit == 0 && unixTime == 0) {
            return array;
        }
        if (limit < array.size() && limit > 0) {
            resultArr = array.subList(array.size() - limit, array.size());
        } else {
            resultArr = array;
        }
        if (unixTime > 0) {
            int i = 0;
            for (final String[] obj : resultArr) {
                if (unixTime > StringUtil.toLong(obj[0], 0l)) {
                    i++;
                } else {
                    break;
                }
            }
            resultArr = resultArr.subList(i, resultArr.size());
        }

        return resultArr;
    }

    @Override
    public void initMarketData(final CoinConfig coinConfig) {
        try {

            if (coinConfig.getType().equals(CoinConfigTypeEnum.PORTFOLIO.getCode())) {
                return;
            }

            final MarketDataExample marketDataExample = new MarketDataExample();
            final int count = this.marketDataRepository.countByExample(marketDataExample, this.getShardTable(coinConfig));
            if (count != 0) {
                return;
            }
            final MarketIndex marketIndex = this.marketIndexService.getMarketIndex(coinConfig.getSymbol());
            if (marketIndex == null) {
                return;
            }

            final List<Integer> typeList = new ArrayList<>();
            typeList.add(0);
            for (final KlineEnum klineEnum : KlineEnum.values()) {
                typeList.add(klineEnum.getType());
            }

            for (final int type : typeList) {
                Date createdDate = DateUtils.dateString2Util(DateUtils.dateUtil2String(DateUtils.getDateInDayAgo(new Date(), 1), DateUtils.YYYY_MM_DD), DateUtils.YYYY_MM_DD);
                if (type == 0) {
                    createdDate = DateUtils.dateString2Util(DateUtils.dateUtil2String(DateUtils.getDateInMinuteAgo(new Date(), 2), "yyyy-MM-dd HH:mm"), "yyyy-MM-dd HH:mm");
                }
                final MarketData marketData = MarketData.builder().marketFrom(coinConfig.getIndexMarketFrom()).open(marketIndex.getPrice()).high(marketIndex.getPrice()).
                        low(marketIndex.getPrice()).close(marketIndex.getPrice()).volume(0D).coinVolume(0D).type(type).createdDate(createdDate)
                        .startId(0L).endId(0L).build();
                this.marketDataRepository.insert(marketData, this.getShardTable(coinConfig));
            }
        } catch (final Exception e) {
            MarketDataServiceImpl.log.error("initMarketData error msg:{}", e.getMessage());
        }
    }
}
