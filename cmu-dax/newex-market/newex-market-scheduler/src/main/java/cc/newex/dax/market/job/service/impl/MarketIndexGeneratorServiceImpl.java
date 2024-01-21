package cc.newex.dax.market.job.service.impl;

import cc.newex.commons.lang.util.DoubleUtil;
import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.common.util.StringUtil;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.domain.LatestTicker;
import cc.newex.dax.market.domain.MarketIndex;
import cc.newex.dax.market.domain.MarketIndexRecord;
import cc.newex.dax.market.dto.enums.RateConvertEnum;
import cc.newex.dax.market.job.service.CoinService;
import cc.newex.dax.market.job.service.MarketIndexGeneratorService;
import cc.newex.dax.market.job.service.PublishService;
import cc.newex.dax.market.model.RateInfo;
import cc.newex.dax.market.service.LatestTickerService;
import cc.newex.dax.market.service.MarketIndexRecordService;
import cc.newex.dax.market.service.MarketIndexService;
import cc.newex.dax.market.service.MonitorService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cc.newex.dax.market.common.consts.IndexConst.AVAILABLE_PERPETUAL_MARKET_RATE;

/**
 * 生成指数数据
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Service("marketIndexGeneratorService")
@Slf4j
public class MarketIndexGeneratorServiceImpl implements MarketIndexGeneratorService {

    @Autowired
    CoinService coinService;
    @Autowired
    RedisService redisService;
    @Autowired
    MonitorService monitorService;
    @Autowired
    PublishService publishService;
    @Autowired
    LatestTickerService latestTickerService;
    @Autowired
    MarketIndexService marketIndexService;
    @Autowired
    MarketIndexRecordService marketIndexRecordService;

    private final Map<String, String> cacheFutureIndex = new ConcurrentHashMap<>();

    /**
     * 指数入库
     *
     * @param coinConfig
     */
    @Override
    public void generateMarketIndexAndCached(final CoinConfig coinConfig) {

        MarketIndex marketIndex;

        final int symbol = coinConfig.getSymbol();

        //获取排序后的行情信息 LatestTicker列表。如果不为空，则生成对应的指数数据。并保存到缓存
        final List<LatestTicker> tickers = this.getQuotationMap(coinConfig.getMarketFromArray());
        if (CollectionUtils.isEmpty(tickers)) {
            log.error("tickers is null");
            return;
        }
        try {
            marketIndex = this.getQuotationAVG(tickers, coinConfig);
            this.marketIndexService.insertMarketIndex(marketIndex);
            marketIndex = this.marketIndexService.getMarketIndexById(marketIndex.getId());
            if (marketIndex == null) {
                log.error("marketIndex is null");
                return;
            }
        } catch (final Exception e) {
            log.error("获取当前行情失败", e);
            return;
        }

        /** 以下推送数据到缓存 **/
        //1. 将最新指数计算数据放入 缓存 显示用的
        this.putFutureLastIndex(symbol, tickers, marketIndex.getPrice(), marketIndex.getInvalid());

        //2. 生成指数数据后，推送数据至缓存
        final String keyNew = String.format(IndexConst.KEY_SUB_INDEX, coinConfig.getSymbolName().toLowerCase());
        final String oldIndexPrice = this.cacheFutureIndex.get(keyNew);

        if (!StringUtil.isEmpty(oldIndexPrice) && oldIndexPrice.equalsIgnoreCase(
                String.valueOf(marketIndex.getPrice()))) {
            return;
        }
        this.cacheFutureIndex.put(keyNew, String.valueOf(marketIndex.getPrice()));
        //从缓存中取得数据，如果新生成指数价格不等于原价格。则推送数据及汇率数据

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("futureIndex", String.valueOf(marketIndex.getPrice()));
        jsonObject.put("timestamp", String.valueOf(System.currentTimeMillis()));
        //合约发布订阅，查usd_cny
        final RateInfo rateInfo = this.monitorService.getCacheRateInfo(RateConvertEnum.USD_CNY.getCode());
        final double usdCnyRate = rateInfo == null ? 0 : rateInfo.getRate();
        jsonObject.put("usdCnyRate", usdCnyRate);
        //指数推送，指数价格
//        this.publishService.publish(IndexConst.INDEX_CHANNEL, PublishTypeEnum.INDEX, coinConfig.getSymbolName(), 1,
//                jsonObject, null, null);

        this.redisService.setInfo("PlatForm_Index_" + coinConfig.getSymbolName().toUpperCase(), JSON.toJSONString(marketIndex));
        //3.放入缓存、接口通过缓存获取
        this.redisService.setInfo(this.coinService.getLastIndexCacheKey(coinConfig), JSON.toJSONString(marketIndex));
    }

    @Override
    public boolean generatePerpetualMarketIndexAndCached(final CoinConfig coinConfig) {

        //获取排序后的行情信息 LatestTicker列表。如果不为空，则生成对应的指数数据。并保存到缓存
        List<LatestTicker> tickers = this.getQuotationMap(coinConfig.getMarketFromArray());
        if (CollectionUtils.isEmpty(tickers)) {
            log.error("tickers is null");
            return false;
        }
        tickers = tickers.stream().sorted(Comparator.comparing(LatestTicker::getLast)).collect(Collectors.toList());
        List<LatestTicker> availableList = new LinkedList<>();
        final List<List<LatestTicker>> allAvailableList = new LinkedList<>();
        final List<MarketIndexRecord> marketIndexRecords = new LinkedList<>();
        final Set<Integer> marketFromSet = new HashSet<>();

        final Date nowTime = new Date();
        for (final LatestTicker x : tickers) {
            final MarketIndexRecord indexRecord = MarketIndexRecord.builder()
                    .createdDate(nowTime)
                    .invalid(1)
                    .marketFrom(x.getMarketFrom())
                    .middlePrice(BigDecimal.ZERO)
                    .price(x.getLast())
                    .build();
            marketIndexRecords.add(indexRecord);
            marketFromSet.add(x.getMarketFrom());
            if (x.getLast().compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            if (availableList.isEmpty()) {
                availableList.add(x);
                continue;
            }

            final LatestTicker latestTicker = availableList.get(availableList.size() - 1);
            if (x.getLast().divide(latestTicker.getLast(), 16, BigDecimal.ROUND_DOWN).compareTo(BigDecimal.ONE.add(AVAILABLE_PERPETUAL_MARKET_RATE)) > 0) {
                allAvailableList.add(availableList);
                availableList = new LinkedList<>();
            }
            availableList.add(x);
        }
        allAvailableList.add(availableList);
        availableList = null;
        for (final List<LatestTicker> x : allAvailableList) {
            if (x.size() > tickers.size() / 2) {
                availableList = x;
            }
        }

        if (CollectionUtils.isEmpty(availableList) || availableList.size() <= tickers.size() / 2) {
            this.marketIndexRecordService.batchInsertMarketIndexRecord(marketIndexRecords, coinConfig);
            log.error("ticker list is not available, tickerList : {}" + JSON.toJSONString(tickers));
            return false;
        }

        final Map<Integer, MarketIndexRecord> indexRecordMap = marketIndexRecords.stream()
                .collect(Collectors.toMap(MarketIndexRecord::getMarketFrom, Function.identity(), (x, y) -> x));

        BigDecimal sum = BigDecimal.ZERO;
        for (final LatestTicker x : availableList) {
            sum = sum.add(x.getLast());
            indexRecordMap.get(x.getMarketFrom()).setInvalid(0);
            marketFromSet.remove(x.getMarketFrom());
        }
        final BigDecimal avgLast = sum.divide(new BigDecimal(availableList.size()), 3, BigDecimal.ROUND_DOWN);
        marketIndexRecords.stream().forEach(x -> x.setMiddlePrice(new BigDecimal(avgLast.doubleValue() + "")));
        final MarketIndex marketIndex = MarketIndex.builder()
                .price(avgLast.doubleValue())
                .symbol(coinConfig.getSymbol())
                .createdDate(nowTime)
                .modifyDate(nowTime)
                .build();
        if (CollectionUtils.isNotEmpty(marketFromSet)) {
            final String invalid = StringUtils.join(marketFromSet, ",");
            marketIndex.setInvalid(invalid);
        }

        log.info("coinConfig: {}, middle price: {}", JSON.toJSONString(coinConfig), avgLast);

        this.marketIndexRecordService.batchInsertMarketIndexRecord(marketIndexRecords, coinConfig);
        this.marketIndexService.insertMarketIndex(marketIndex);
        this.redisService.setCacheValueExpireTime("PERPETUAL_INDEX_" + coinConfig.getSymbolName().toUpperCase(),
                JSON.toJSONString(marketIndex), 24, TimeUnit.HOURS);
        /** 以下推送数据到缓存 **/
        //1. 将最新指数计算数据放入 缓存 显示用的
        this.putFutureLastIndex(coinConfig.getSymbol(), tickers, marketIndex.getPrice(), marketIndex.getInvalid());

        //2. 生成指数数据后，推送数据至缓存
        final String keyNew = String.format(IndexConst.KEY_SUB_INDEX, coinConfig.getSymbolName().toLowerCase());
        final String oldIndexPrice = this.cacheFutureIndex.get(keyNew);

        if (!StringUtil.isEmpty(oldIndexPrice) && oldIndexPrice.equalsIgnoreCase(
                String.valueOf(marketIndex.getPrice()))) {
            return true;
        }
        this.cacheFutureIndex.put(keyNew, String.valueOf(marketIndex.getPrice()));

        /*final JSONObject jsonObject = new JSONObject();
        jsonObject.put("futureIndex", String.valueOf(marketIndex.getPrice()));
        jsonObject.put("timestamp", String.valueOf(System.currentTimeMillis()));
        //合约发布订阅，查usd_cny
        final RateInfo rateInfo = this.monitorService.getCacheRateInfo(RateConvertEnum.USD_CNY.getCode());
        final double usdCnyRate = rateInfo == null ? 0 : rateInfo.getRate();
        jsonObject.put("usdCnyRate", usdCnyRate);
        this.publishService.publish(IndexConst.INDEX_CHANNEL, PublishTypeEnum.INDEX, coinConfig.getSymbolName(), 1,
                    jsonObject, null, null);*/

        this.redisService.setInfo("PlatForm_Index_" + coinConfig.getSymbolName().toUpperCase(), JSON.toJSONString(marketIndex));
        //3.放入缓存、接口通过缓存获取
        this.redisService.setInfo(this.coinService.getLastIndexCacheKey(coinConfig), JSON.toJSONString(marketIndex));
        return true;
    }

    /***
     * 获取排序后的行情信息
     */
    private List<LatestTicker> getQuotationMap(final int[] marketFrom) {
        final List<LatestTicker> tickers = this.latestTickerService.getLatestTickerFromMarketFromArray(marketFrom);
        if (CollectionUtils.isEmpty(tickers)) {
            return null;
        }
        for (final LatestTicker ticker : tickers) {
            //volume 暂存原始价格
            ticker.setVolume(new BigDecimal(ticker.getLast().doubleValue()));
            final double last = this.getLastUSD(ticker.getLast().doubleValue(), ticker.getMarketFrom());
            ticker.setLast(new BigDecimal(last));
        }
        tickers.sort(Comparator.comparing(LatestTicker::getLast));
        return tickers;
    }

    private MarketIndex getQuotationAVG(final List<LatestTicker> tickers, final CoinConfig coinConfig) {
        final MarketIndex resultFutureIndex = new MarketIndex();

        final List<BigDecimal> resultAvg = new ArrayList<>();

        //获取中间参考价格
        final double middleValue = this.getOtherPlatMiddleValue(tickers);

        //获取失效的marketFrom列表
        final Set<String> invalidMarketFroms = this.getInvalidMarketFrom(coinConfig.getSymbol());

        for (final LatestTicker ticker : tickers) {
            //计算偏离范围 0正常、-1低于10%,1高于10%
            final int normal = this.isNormal(ticker.getLast().doubleValue(), middleValue);
            double lastPrice = ticker.getLast().doubleValue();

            boolean invalidRecord = false;

            //低于百分之十 按其他平台平均值的0.9 计算
            if (normal == -1) {
                lastPrice = DoubleUtil.multiply(middleValue, IndexConst.index_minPercent);
                invalidRecord = true;
            }
            //高于百分之十 按其他平台平均值的1.1 计算
            else if (normal == 1) {
                lastPrice = DoubleUtil.multiply(middleValue, IndexConst.index_maxPercent);
                invalidRecord = true;
            }
            log.info("getQuotationAVG marketFrom: {},avg: {},tickLastPrice: {},lastPrice: {},invalidRecord:{}",
                    ticker.getMarketFrom(),
                    middleValue, ticker.getLast().doubleValue(), lastPrice, invalidRecord);

            //插入流水、有效或者无效 记录
            final MarketIndexRecord marketIndexRecord = MarketIndexRecord.builder().
                    marketFrom(ticker.getMarketFrom()).
                    createdDate(new Date()).
                    invalid(invalidRecord ? 1 : 0).
                    middlePrice(new BigDecimal(middleValue)).
                    price(ticker.getLast()).
                    build();
            this.marketIndexRecordService.insertMarketIndexRecord(marketIndexRecord, coinConfig);

            //有效、无效 计算  开关开着并且满足无效计算条件
            final boolean isInvalid = this.isInValid(ticker.getMarketFrom(),
                    invalidMarketFroms.contains(String.valueOf(ticker.getMarketFrom())),
                    coinConfig) && coinConfig.getInvalidSwitch() == 1;
            if (isInvalid) {
                invalidMarketFroms.add(String.valueOf(ticker.getMarketFrom()));
            } else {
                invalidMarketFroms.remove(String.valueOf(ticker.getMarketFrom()));
                resultAvg.add(new BigDecimal(lastPrice));
            }
        }

        //无效平台列表
        if (CollectionUtils.isNotEmpty(invalidMarketFroms)) {
            resultFutureIndex.setInvalid(StringUtils.join(invalidMarketFroms, ","));
        }

        resultFutureIndex.setSymbol(coinConfig.getSymbol());
        //指数加权平均[即:具体场外价格的均值] 计算小数位
        resultFutureIndex.setPrice(DoubleUtil.divide(this.getResultAvg(resultAvg), 1, coinConfig.getPricePoint()));
        return resultFutureIndex;
    }

    /**
     * 有效、无效 计算
     */
    private boolean isInValid(final int marketForm, final boolean isInValidSign, final CoinConfig coinConfig) {
        int totalRecord = 0;
        try {

            totalRecord = this.marketIndexRecordService.countMarketIndexRecord(marketForm, 0, coinConfig);
        } catch (final Exception e) {
            log.error("totalRecord ==0, marketForm:" + marketForm + ",coinConfig:" + coinConfig.toString(), e);
        }
        if (totalRecord == 0) {
            return false;
        }
        final int invalidRecord = this.marketIndexRecordService.countMarketIndexRecord(marketForm, 1, coinConfig);
        final double invalidRate = DoubleUtil.divide(invalidRecord, totalRecord, 4);

        //超过阈值时 无效  >=0.9
        final int validResult = MarketIndexGeneratorServiceImpl.compare(invalidRate, IndexConst.INVALID_RATE_MAX);
        return validResult >= 0;
    }

    private static int compare(final double a, final double b) {
        return BigDecimal.valueOf(a).compareTo(BigDecimal.valueOf(b));
    }

    //0为正常
    private int isNormal(final double value, final double compervalue) {
        int result = 0;
        double arg = 0;
        if (compervalue != 0) {
            arg = DoubleUtil.divide(value, compervalue, 4);
        }
        if (arg <= IndexConst.index_minPercent) {
            //小于百分之十
            result = -1;
        } else if (arg >= IndexConst.index_maxPercent) {
            //大于百分之十
            result = 1;
        }
        return result;
    }

    private Set<String> getInvalidMarketFrom(final int symbol) {
        final List<MarketIndex> marketIndexList = this.marketIndexService.getMarketIndexAndLimit(symbol, 1);
        final Set<String> marketFroms = new HashSet<>();
        if (CollectionUtils.isEmpty(marketIndexList)) {
            return marketFroms;
        }

        final String invalidStr = marketIndexList.get(0).getInvalid();
        if (StringUtil.isEmpty(invalidStr)) {
            return marketFroms;
        }
        marketFroms.addAll(Arrays.asList(StringUtils.split(invalidStr, ",")));
        return marketFroms;
    }

    //获取ticker列表的中位数价格
    private double getOtherPlatMiddleValue(final List<LatestTicker> tickers) {
        final int allSize = tickers.size();
        int center = allSize / 2;
        if (allSize % 2 == 0) {
            //为偶数的时候
            center = allSize / 2;
            return tickers.get(center - 1).getLast().add(tickers.get(center).getLast()).divide(new BigDecimal(2), 2, 4)
                    .doubleValue();
        } else {
            return tickers.get(center).getLast().doubleValue();
        }
    }

    private double getResultAvg(final List<BigDecimal> result) {
        if (CollectionUtils.isEmpty(result)) {
            return 0D;
        }
        double all = 0;
        for (final BigDecimal price : result) {
            all = DoubleUtil.add(all, price.doubleValue());
        }
        return DoubleUtil.divide(all, result.size(), 4);
    }

    /***
     * 将最新指数计算数据放入 缓存 显示用的
     */
    private void putFutureLastIndex(final int symbol, final List<LatestTicker> tickers, final double marketIndex, final String invalidStr) {
        final JSONObject result = new JSONObject();
        final JSONArray jsonArray = new JSONArray();
        List<String> invalidList = new ArrayList<>();
        if (StringUtils.isNotBlank(invalidStr)) {
            invalidList = Arrays.asList(StringUtils.split(invalidStr, ","));
        }
        for (final LatestTicker ticker : tickers) {
            if (invalidList.contains(String.valueOf(ticker.getMarketFrom()))) {
                continue;
            }

            final JSONObject jsonObject = new JSONObject();
            //站点
            jsonObject.put("makerFrom", ticker.getMarketFrom());
            //美元价格
            jsonObject.put("last", ticker.getLast());
            //原始价格
            jsonObject.put("originalPrice", ticker.getVolume().doubleValue());
            //币对
            jsonObject.put("symbolDesc", ticker.getSymbol());
            //站点名字
            jsonObject.put("name", ticker.getName());
            jsonArray.add(jsonObject);
        }
        result.put("tickerArray", jsonArray);
        result.put("futureIndex", marketIndex);
        this.redisService.setInfo(IndexConst.KEY_LAST_INDEX_CALCULATION + symbol, result.toJSONString());
    }

    /**
     * 获取美元价格
     */
    private double getLastUSD(final double last, final int marketFrom) {
        //获取美元兑人民币的价格
        final RateInfo rateInfo = this.monitorService.getCacheRateInfo(RateConvertEnum.USD_CNY.getCode());
        final List<LatestTicker> tickerList = this.latestTickerService.getLatestTickerFromMarketFromArray(new int[]{marketFrom});
        double last_usd = 0;

        if (CollectionUtils.isEmpty(tickerList)) {
            return last_usd;
        }

        final LatestTicker latestTicker = tickerList.get(0);
        if (latestTicker.getQuoteCurrency() == 3) {
            switch (latestTicker.getMoneytype()) {
                //人民币
                case 0:
                    final double rate = rateInfo.getRate();
                    if (rate != 0) {
                        last_usd = DoubleUtil.divide(last, rate, 2);
                    }
                    break;
                //美元
                case 1:
                    last_usd = last;
                    break;
                //欧元
                case 3:
                    final RateInfo rateInfoEur = this.monitorService.getCacheRateInfo(RateConvertEnum.EUR_CNY.getCode());
                    final double eurrate = rateInfoEur.getRate();
                    final double last_rmb = DoubleUtil.multiply(last, eurrate);
                    final double ratetmp = rateInfo.getRate();
                    if (ratetmp != 0) {
                        last_usd = DoubleUtil.divide(last_rmb, ratetmp, 2);
                    }
                    break;
                //置零
                default:
                    last_usd = 0;
                    break;
            }
        } else {
            //查出 对标币种的价格
            final MarketIndex marketIndex = this.marketIndexService.getLatestMarketIndex(latestTicker.getQuoteCurrency());
            if (marketIndex != null) {
                final Double unit_coin_price = marketIndex.getPrice();
                last_usd = DoubleUtil.multiply(last, unit_coin_price);
            }
        }
        return last_usd;
    }

}
