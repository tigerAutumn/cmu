package cc.newex.dax.market.service.impl;

import cc.newex.commons.lang.util.DateUtil;
import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.common.util.DateUtils;
import cc.newex.dax.market.criteria.MarketIndexExample;
import cc.newex.dax.market.data.MarketIndexRepository;
import cc.newex.dax.market.domain.MarketIndex;
import cc.newex.dax.market.service.MarketIndexService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Service("marketIndexService")
public class MarketIndexServiceImpl implements MarketIndexService {

    @Autowired
    MarketIndexRepository marketIndexRepository;
    @Autowired
    private RedisService redisService;

    @Override
    public MarketIndex getMarketIndex(final int symbol) {
        final MarketIndexExample marketIndexExample = new MarketIndexExample();
        final MarketIndexExample.Criteria criteria = marketIndexExample.createCriteria();
        criteria.andSymbolEqualTo(symbol);
        marketIndexExample.setOrderByClause("id DESC LIMIT 1");
        final List<MarketIndex> marketIndex = this.marketIndexRepository.selectByExample(marketIndexExample);
        return CollectionUtils.isEmpty(marketIndex) ? null : marketIndex.get(0);
    }

    @Override
    public List<MarketIndex> getMarketIndexAndLimit(final int symbol, final int limit) {
        final MarketIndexExample marketIndexExample = new MarketIndexExample();
        final MarketIndexExample.Criteria criteria = marketIndexExample.createCriteria();
        criteria.andSymbolEqualTo(symbol);
        marketIndexExample.setOrderByClause("id DESC LIMIT 0," + limit);
        return this.marketIndexRepository.selectByExample(marketIndexExample);
    }

    @Override
    public long insertMarketIndex(final MarketIndex marketIndex) {
        marketIndex.setCreatedDate(new Date());
        marketIndex.setModifyDate(new Date());
        return this.marketIndexRepository.insert(marketIndex);
    }

    @Override
    public MarketIndex getMarketIndexById(final long id) {
        return this.marketIndexRepository.selectById(id);
    }

    @Override
    public double getIndexBefore24HourLow(final int symbol, final Date date) {
        return this.getMarketIndexBySymbolAndDate(symbol, date, "price ASC LIMIT 1");
    }

    @Override
    public double getIndexBefore24HourHigh(final int symbol, final Date date) {
        return this.getMarketIndexBySymbolAndDate(symbol, date, "price DESC LIMIT 1");
    }

    @Override
    public void deleteMarketIndex(final int symbol) {
        final MarketIndexExample marketIndexExample = new MarketIndexExample();
        final MarketIndexExample.Criteria criteria = marketIndexExample.createCriteria();
        criteria.andSymbolEqualTo(symbol);
        criteria.andCreatedDateLessThan(DateUtil.addDays(Calendar.getInstance().getTime(), -2));
        this.marketIndexRepository.deleteByExample(marketIndexExample);
    }

    private double getMarketIndexBySymbolAndDate(final int symbol, final Date date, final String orderStr) {
        final MarketIndexExample marketIndexExample = new MarketIndexExample();
        final MarketIndexExample.Criteria criteria = marketIndexExample.createCriteria();
        //symbol = ? AND created_date >=?
        criteria.andSymbolEqualTo(symbol).andCreatedDateGreaterThanOrEqualTo(date);
        marketIndexExample.setOrderByClause(orderStr);
        final List<MarketIndex> marketIndex = this.marketIndexRepository.selectByExample(marketIndexExample);
        return CollectionUtils.isEmpty(marketIndex) ? 0d : marketIndex.get(0).getPrice();
    }

    @Override
    public long getMarketIndexStartId(final int symbol, final Date startDateStr) {
        final MarketIndexExample marketIndexExample = new MarketIndexExample();
        final MarketIndexExample.Criteria criteria = marketIndexExample.createCriteria();
        criteria.andSymbolEqualTo(symbol).andCreatedDateGreaterThanOrEqualTo(startDateStr);
        marketIndexExample.setOrderByClause("id ASC LIMIT 1");
        final List<MarketIndex> marketIndex = this.marketIndexRepository.selectByExample(marketIndexExample);
        return CollectionUtils.isEmpty(marketIndex) ? 0L : marketIndex.get(0).getId();
    }

    @Override
    public long getMarketIndexEndId(final int symbol, final Date endDateStr) {
        final MarketIndexExample marketIndexExample = new MarketIndexExample();
        final MarketIndexExample.Criteria criteria = marketIndexExample.createCriteria();
        criteria.andSymbolEqualTo(symbol).andCreatedDateLessThan(endDateStr);
        marketIndexExample.setOrderByClause("id DESC LIMIT 1");
        final List<MarketIndex> marketIndex = this.marketIndexRepository.selectByExample(marketIndexExample);
        return CollectionUtils.isEmpty(marketIndex) ? 0L : marketIndex.get(0).getId();
    }

    @Override
    public double getMarketIndexHighPrice(final long startRecordId, final long endRecordId, final int symbol) {
        return this.getPrice(startRecordId, endRecordId, symbol, "price DESC LIMIT 1");
    }

    @Override
    public double getMarketIndexLowPrice(final long startRecordId, final long endRecordId, final int symbol) {
        return this.getPrice(startRecordId, endRecordId, symbol, "price ASC LIMIT 1");
    }

    @Override
    public double getMarketIndexFirstPrice(final long startRecordId, final long endRecordId, final int symbol) {
        return this.getPrice(startRecordId, endRecordId, symbol, "id ASC LIMIT 1");
    }

    @Override
    public double getMarketIndexLastPrice(final long startRecordId, final long endRecordId, final int symbol) {
        return this.getPrice(startRecordId, endRecordId, symbol, "id DESC LIMIT 1");
    }

    private double getPrice(final long startRecordId, final long endRecordId, final int symbol, final String orderString) {
        final MarketIndexExample marketIndexExample = new MarketIndexExample();
        final MarketIndexExample.Criteria criteria = marketIndexExample.createCriteria();
        criteria.andSymbolEqualTo(symbol).andIdGreaterThanOrEqualTo(startRecordId).andIdLessThanOrEqualTo(endRecordId);
        marketIndexExample.setOrderByClause(orderString);
        final List<MarketIndex> marketIndex = this.marketIndexRepository.selectByExample(marketIndexExample);
        return CollectionUtils.isEmpty(marketIndex) ? 0L : marketIndex.get(0).getPrice();
    }

    @Override
    public MarketIndex getLatestMarketIndex(final int symbol, final Date date) {
        //sql示例: symbol = ? AND created_date >=?  ORDER BY  id DESC LIMIT 1
        final MarketIndexExample marketIndexExample = new MarketIndexExample();
        final MarketIndexExample.Criteria criteria = marketIndexExample.createCriteria();
        criteria.andSymbolEqualTo(symbol).andCreatedDateGreaterThanOrEqualTo(date);
        marketIndexExample.setOrderByClause("id DESC LIMIT 1");
        final List<MarketIndex> marketIndexList = this.marketIndexRepository.selectByExample(marketIndexExample);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(marketIndexList)) {
            return marketIndexList.get(0);
        }
        return null;
    }

    @Override
    public MarketIndex getLatestMarketIndex(final int symbol) {
        //sql示例: symbol = ?  ORDER BY  id DESC LIMIT 1
        final MarketIndexExample marketIndexExample = new MarketIndexExample();
        final MarketIndexExample.Criteria criteria = marketIndexExample.createCriteria();
        criteria.andSymbolEqualTo(symbol);
        marketIndexExample.setOrderByClause("id DESC LIMIT 1");
        final List<MarketIndex> marketIndexList = this.marketIndexRepository.selectByExample(marketIndexExample);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(marketIndexList)) {
            return marketIndexList.get(0);
        }
        return null;
    }

    @Override
    public List<MarketIndex> getMarketIndexWithinDate(final int symbol, final Date date) {
        //sql示例: symbol = ? AND created_date >=?  ORDER BY  created_date ASC
        final MarketIndexExample marketIndexExample = new MarketIndexExample();
        final MarketIndexExample.Criteria criteria = marketIndexExample.createCriteria();
        criteria.andSymbolEqualTo(symbol).andCreatedDateGreaterThanOrEqualTo(date);
        marketIndexExample.setOrderByClause("created_date ASC");
        return this.marketIndexRepository.selectByExample(marketIndexExample);
    }

    @Override
    public double getMarketIndexAvg(final Date date, final int symbol) {
        final MarketIndexExample marketIndexExample = new MarketIndexExample();
        final MarketIndexExample.Criteria criteria = marketIndexExample.createCriteria();
        final Date startDate = DateUtils.getDateInHourAgo(date, -1);
        criteria.andCreatedDateGreaterThanOrEqualTo(startDate);
        criteria.andCreatedDateLessThanOrEqualTo(date);
        criteria.andSymbolEqualTo(symbol);
        final List<Map<String, Object>> mapList = this.marketIndexRepository.selectMarketIndexAvg(marketIndexExample);
        if (CollectionUtils.isEmpty(mapList)) {
            return 0D;
        }
        final Object result = mapList.get(0).get("avg");
        if (result == null) {
            return 0D;
        }
        return Double.parseDouble(result.toString());
    }

    @Override
    public MarketIndex getTickerFromCache(final String symbolName) {
        final String key = String.format(IndexConst.FUTURE_KEY_LAST_INDEX, symbolName);
        final Object o = this.redisService.getInfo(key);
        if (o == null) {
            return null;
        }
        final String marketIndexStr = (String) o;
        if (StringUtils.isBlank(marketIndexStr)) {
            return null;
        }
        return JSON.parseObject(marketIndexStr, MarketIndex.class);
    }
}
