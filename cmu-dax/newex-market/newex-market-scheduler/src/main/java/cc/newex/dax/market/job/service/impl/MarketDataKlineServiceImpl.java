package cc.newex.dax.market.job.service.impl;

import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.commons.lang.util.DateUtil;
import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.common.enums.PublishTypeEnum;
import cc.newex.dax.market.common.util.KlineTime;
import cc.newex.dax.market.common.util.StringUtil;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.domain.MarketData;
import cc.newex.dax.market.domain.MarketIndex;
import cc.newex.dax.market.job.service.MarketDataKlineService;
import cc.newex.dax.market.job.service.PublishService;
import cc.newex.dax.market.service.MarketDataService;
import cc.newex.dax.market.service.MarketIndexService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外部指数1分钟K线生成服务，注意：与业务K线逻辑不同
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Service("marketDataKlineService")
@Slf4j
public class MarketDataKlineServiceImpl implements MarketDataKlineService {
    public final static int KLINE_SIZE = 2000;

    @Autowired
    private RedisService redisService;
    @Autowired
    private MarketIndexService marketIndexService;
    @Autowired
    private MarketDataService marketDataService;
    @Autowired
    private PublishService publishService;

    /**
     * 生成指数K线有两种情况
     * 1. 当时生成时间在1分钟内，则直接的插入一条数据
     * 2. 当前生成时间在1分钟外，更新上一分钟数据，并插入一条新K线数据
     *
     * @param coinConfig
     */
    @Override
    public boolean generateOuterIndex1Minute(final CoinConfig coinConfig) {
        MarketDataKlineServiceImpl.log.info("MarketDataKlineServiceImpl generateOuterIndex1Minute begin: {}", coinConfig);
        MarketDataKlineServiceImpl.log.info("generateOuterIndex1Minute begin");
        final long start = System.currentTimeMillis();

        final MarketData lastKline;
        //从redis或者数据库中取得1分钟K线数据
        final List<MarketData> marketDatas = this.getKlineData(coinConfig, KlineEnum.MIN1.getType());
        if (CollectionUtils.isEmpty(marketDatas)) {
            //初始时间设置为前一分钟，即计算前一分钟到现在的Kline
            lastKline = new MarketData();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtil.getDateTimeMinute(org.apache.commons.lang3.time.DateUtils.addMinutes(calendar.getTime(), -1)));
            lastKline.setCreatedDate(calendar.getTime());
        } else {
            //如果K线存在，取得最后一条K线数据（按时间升序）
            lastKline = marketDatas.get(marketDatas.size() - 1);
        }

        //取得K线中最后一条（时间距最近的K线）
        final Date beginDate = lastKline.getCreatedDate();
        //退到分钟维度
        final Calendar beginDateCalendar = Calendar.getInstance();
        beginDateCalendar.setTime(beginDate);
        beginDateCalendar.set(Calendar.SECOND, 0);
        beginDateCalendar.set(Calendar.MILLISECOND, 0);
        //按最后一条K线生成时间，作为MarketIndex查询的起始时间。取得这段时间内所有marketIndex列表
        final List<MarketIndex> newMarketIndexList = this.marketIndexService.getMarketIndexWithinDate(coinConfig.getSymbol(), beginDateCalendar.getTime());

        //如果K线和对应生成marketIndex都为空。说明无生成数据。直接返回
        if (CollectionUtils.isEmpty(marketDatas) && CollectionUtils.isEmpty(newMarketIndexList)) {
            MarketDataKlineServiceImpl.log.info("generateOuterIndex1Minute has no data");
            return false;
        }
        //精确到分钟
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        final Map<String, List<MarketIndex>> marketIndexMap = new HashMap<>();
        for (final MarketIndex marketIndex : newMarketIndexList) {
            final String createdDateFormat = format.format(marketIndex.getCreatedDate());
            List<MarketIndex> oldList = marketIndexMap.get(createdDateFormat);
            if (oldList == null) {
                oldList = new ArrayList<>();
                marketIndexMap.put(createdDateFormat, oldList);
            }
            oldList.add(marketIndex);
        }

        //计算最后一条数据与当前时间间隔(以分钟计)。
        final long minute = DateUtil.betweenMinute(lastKline.getCreatedDate(), new Date());

        Date startTime = lastKline.getCreatedDate();
        //用于计算本次是否有更新或者新增K线数据
        int count = 0;
        for (int i = 0; i <= minute; i++) {
            Double lastClose = null;
            //拿最后一条数据的close
            if (i == 0) {
                //最后一条取倒数第二条的数据
                if (marketDatas.size() > 1) {
                    lastClose = marketDatas.get(marketDatas.size() - 2).getClose();
                }
            } else {
                if (marketDatas.size() > 0) {
                    lastClose = marketDatas.get(marketDatas.size() - 1).getClose();
                }
            }

            final String createdDateFormat = format.format(startTime);
            final List<MarketIndex> minuteMarketIndexList = marketIndexMap.get(createdDateFormat);

            //如果1分钟K线数据不存在且MarketIndex数据不存在。说明没有当前分钟内的抓取数据，也不用初始化K线数据
            if (CollectionUtils.isEmpty(minuteMarketIndexList) && lastClose == null) {
                continue;
            }

            final MarketData marketData = this.getMarketDataByTime(coinConfig, KlineEnum.MIN1, lastClose, minuteMarketIndexList);

            count++;
            //set time
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            marketData.setCreatedDate(calendar.getTime());

            //最后一条更新
            if (i == 0 && lastKline.getId() != null && marketDatas.size() > 0) {
                marketData.setId(lastKline.getId());

                //修改上一条记录
                this.marketDataService.updateMarketDataById(coinConfig, marketData);
                marketDatas.set(marketDatas.size() - 1, marketData);
            } else {
                //插入新纪录，需要带id
                this.marketDataService.insertMarketDataWithId(coinConfig, marketData);
                marketDatas.add(marketData);
            }

            //push1分钟数据给控制台
            this.publishService.publish(IndexConst.INDEX_CHANNEL, PublishTypeEnum.CANDLES, coinConfig.getSymbolName(), 1,
                    marketData.getArr(coinConfig.getType()), null,
                    KlineTime.mapKey.get(0));

            //update time
            startTime = DateUtils.addMinutes(startTime, 1);
        }
        //只有数据有变化的时候。定入缓存并推送push
        if (count > 0) {
            //缓存最近2000条1分钟K线
            this.putMarketDatasCache(marketDatas, coinConfig);
            final List<String[]> array = new ArrayList<>();
            marketDatas.stream().forEach(marketData -> array.add(marketData.getArr(coinConfig.getType())));
            this.redisService.putCache(coinConfig.getIndexMarketFrom(), 0, array);
        }
        MarketDataKlineServiceImpl.log.info("MarketDataKlineServiceImpl generateOuterIndex1Minute end: {}", (System.currentTimeMillis() - start));
        return true;
    }

    private void putMarketDatasCache(List<MarketData> marketDatas, final CoinConfig coinConfig) {
        //删除之前数据只保留最近 2000条数据
        if (marketDatas.size() > MarketDataKlineServiceImpl.KLINE_SIZE) {
            marketDatas = marketDatas.subList(marketDatas.size() - MarketDataKlineServiceImpl.KLINE_SIZE,
                    marketDatas.size());
        }
        final String key = IndexConst.MARKET_DATA_JSON + "_" + coinConfig.getIndexMarketFrom() + "_" + 0;
        this.redisService.setInfo(key,
                JSONObject.toJSONString(marketDatas));
    }

    /**
     * 处理一分钟数据。此处minuteMarketIndexList和lastClose其中一个肯定有数据
     *
     * @param coinConfig            币种配置
     * @param candlesEnum           K线类型
     * @param lastClose             K线中的1分钟收盘价
     * @param minuteMarketIndexList 1分钟内marketIndex数据
     * @return
     */
    private MarketData getMarketDataByTime(final CoinConfig coinConfig, final KlineEnum candlesEnum, final Double lastClose, final List<MarketIndex> minuteMarketIndexList) {
        final MarketData marketData = new MarketData();
        //如果抓取数据不存在，则用上一收盘价来继续画接下来的K线。
        if (CollectionUtils.isEmpty(minuteMarketIndexList)) {
            marketData.setOpen(lastClose);
            marketData.setHigh(lastClose);
            marketData.setLow(lastClose);
            marketData.setClose(lastClose);
        } else {
            //最高价
            Double high = null;
            //最低价
            Double low = null;
            for (final MarketIndex minuteMarketIndex : minuteMarketIndexList) {
                final double price = minuteMarketIndex.getPrice();
                if (high == null || high < price) {
                    high = price;
                }
                if (low == null || low > price) {
                    low = price;
                }
            }
            marketData.setOpen(lastClose == null ? minuteMarketIndexList.get(0).getPrice() : lastClose);
            marketData.setHigh(lastClose == null ? high : Math.max(high, lastClose));
            marketData.setLow(lastClose == null ? low : Math.min(low, lastClose));
            marketData.setClose(minuteMarketIndexList.get(minuteMarketIndexList.size() - 1).getPrice());
        }
        marketData.setVolume(0.0);
        marketData.setMarketFrom(coinConfig.getIndexMarketFrom());
        marketData.setType(candlesEnum.getType());
        return marketData;
    }


    /**
     * 取得K线数据，默认从redis中取数据，如果Redis中不存在或者开关设置为1。则从DB中加载
     *
     * @param coinConfig 币种配置
     * @param type       类型
     * @return
     */
    private List<MarketData> getKlineData(final CoinConfig coinConfig, final int type) {
        List<MarketData> marketDatas = null;
        final int marketDateUpdate = this.getMarketDateUpdateSwitchFromCache();
        if (marketDateUpdate == 1) {
            MarketDataKlineServiceImpl.log.info("getOneMinuteArray query from db");
        } else {
            final String key = IndexConst.MARKET_DATA_JSON + "_" + coinConfig.getIndexMarketFrom() + "_" + type;
            final String klineJson = this.redisService.getInfo(key);
            marketDatas = JSONArray.parseArray(klineJson, MarketData.class);
        }
        if (marketDatas == null) {
            marketDatas = this.marketDataService.getListByType(coinConfig, type, MarketDataKlineServiceImpl.KLINE_SIZE);
            if (!CollectionUtils.isEmpty(marketDatas)) {
                Collections.reverse(marketDatas);
            }
        }
        return marketDatas == null ? new ArrayList<>() : marketDatas;
    }

    @Override
    public void marketDataOuterMerge(final CoinConfig coinConfig, final KlineEnum klineEnum) {
        MarketDataKlineServiceImpl.log.info("MarketDataKlineServiceImpl marketDataOuterMerge begin: {}, {}", coinConfig, klineEnum);
        final long begin = System.currentTimeMillis();
        MarketDataKlineServiceImpl.log.info("marketDataOuterMerge begin： {}，{}", coinConfig, klineEnum.getType());

        //需要根据一分钟的Kline生成其他维度的Kline，误差比较小
        final List<MarketData> oneMinuteKlineDatas = this.getKlineData(coinConfig, KlineEnum.MIN1.getType());
        if (CollectionUtils.isEmpty(oneMinuteKlineDatas)) {
            return;
        }

        final List<MarketData> marketDatas = this.getKlineData(coinConfig, klineEnum.getType());
        final MarketData lastKline;
        if (CollectionUtils.isEmpty(marketDatas)) {
            lastKline = new MarketData();
            lastKline.setCreatedDate(this.getStartDate(klineEnum));
        } else {
            lastKline = marketDatas.get(marketDatas.size() - 1);
        }

        final Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(lastKline.getCreatedDate());
        final Date endDate = new Date();
        int i = 0;
        //暂时不考虑批量操作
        while (beginCalendar.getTime().compareTo(endDate) < 0) {
            final Date startTime = beginCalendar.getTime();
            beginCalendar.add(Calendar.MINUTE, klineEnum.getPeriodMinute());
            final MarketData marketData = this.getMarketData(coinConfig.getIndexMarketFrom(), klineEnum, startTime, beginCalendar.getTime(),
                    oneMinuteKlineDatas);
            //最后一条更新
            if (i++ == 0 && lastKline.getId() != null && marketDatas.size() > 0) {
                marketData.setId(lastKline.getId());

                //修改上一条记录
                this.marketDataService.updateMarketDataById(coinConfig, marketData);
                marketDatas.set(marketDatas.size() - 1, marketData);
            } else {
                //插入数据库，需要带回id，因为后续可能需要根据id更新
                this.marketDataService.insertMarketDataWithId(coinConfig, marketData);
                marketDatas.add(marketData);
            }
            //指数kline
            this.publishService.publish(IndexConst.INDEX_CHANNEL, PublishTypeEnum.CANDLES, coinConfig.getSymbolName(), 1,
                    marketData.getArr(coinConfig.getType()), null,
                    KlineTime.mapKey.get(klineEnum.getType()));
        }
        this.putMarketDatasCache(marketDatas, coinConfig, klineEnum.getType());

        final List<String[]> array = new ArrayList<>();
        marketDatas.stream().forEach(marketData -> array.add(marketData.getArr(coinConfig.getType())));
        this.redisService.putCache(coinConfig.getIndexMarketFrom(), klineEnum.getType(), array);

        MarketDataKlineServiceImpl.log.info("MarketDataKlineServiceImpl marketDataOuterMerge end: {}", System.currentTimeMillis() - begin);
    }

    private void putMarketDatasCache(List<MarketData> marketDatas, final CoinConfig coinConfig, final int type) {
        //删除之前数据只保留最近 2000条数据
        if (marketDatas.size() > MarketDataKlineServiceImpl.KLINE_SIZE) {
            marketDatas = marketDatas.subList(marketDatas.size() - MarketDataKlineServiceImpl.KLINE_SIZE,
                    marketDatas.size());
        }
        final String key = IndexConst.MARKET_DATA_JSON + "_" + coinConfig.getIndexMarketFrom() + "_" + type;
        this.redisService.setInfo(key,
                JSONObject.toJSONString(marketDatas));
    }

    private MarketData getMarketData(final Integer marketFrom, final KlineEnum klineEnum, final Date startDate, final Date endDate, final List<MarketData> oneMinuteKlineDatas) {
        final MarketData marketData = new MarketData();
        //开盘价
        Double open = null;
        Date openDate = null;
        //最高价
        Double high = null;
        //最低价
        Double low = null;
        //收盘价
        Double close = null;
        Date closeDate = null;
        for (final MarketData oneMinuteMarketData : oneMinuteKlineDatas) {
            if (oneMinuteMarketData.getCreatedDate() == null
                    || oneMinuteMarketData.getCreatedDate().compareTo(startDate) < 0
                    || oneMinuteMarketData.getCreatedDate().compareTo(endDate) >= 0) {
                continue;
            }
            if (high == null || oneMinuteMarketData.getHigh() > high) {
                high = oneMinuteMarketData.getHigh();
            }
            if (low == null || oneMinuteMarketData.getLow() < low) {
                low = oneMinuteMarketData.getLow();
            }
            if (openDate == null || oneMinuteMarketData.getCreatedDate().before(openDate)) {
                open = oneMinuteMarketData.getOpen();
                openDate = oneMinuteMarketData.getCreatedDate();
            }
            if (closeDate == null || oneMinuteMarketData.getCreatedDate().after(openDate)) {
                close = oneMinuteMarketData.getClose();
                closeDate = oneMinuteMarketData.getCreatedDate();
            }
        }
        marketData.setOpen(open);
        marketData.setHigh(high);
        marketData.setLow(low);
        marketData.setClose(close);
        marketData.setVolume(0.0);
        marketData.setMarketFrom(marketFrom);
        marketData.setType(klineEnum.getType());
        marketData.setCreatedDate(startDate);
        return marketData;
    }

    private Date getStartDate(final KlineEnum klineEnum) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        final int timeType = klineEnum.getTimeType();
        final int time = calendar.get(timeType) / klineEnum.getPeriod();
        switch (timeType) {
            case Calendar.MINUTE:
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(timeType, time * klineEnum.getPeriod());
                break;
            case Calendar.HOUR_OF_DAY:
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(timeType, time * klineEnum.getPeriod());
                break;
            case Calendar.DAY_OF_MONTH:
                calendar.set(Calendar.DAY_OF_MONTH, 0);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(timeType, time * klineEnum.getPeriod());
                break;
            case Calendar.DAY_OF_WEEK:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            default:
                break;
        }
        return calendar.getTime();
    }

    private int getMarketDateUpdateSwitchFromCache() {
        final String key = IndexConst.MARKET_DATA_ARRAY_UPDATE;
        final String object = this.redisService.getInfo(key);
        final int update = object != null ? StringUtil.toInteger(object, 0) : 0;
        return update;
    }
}

