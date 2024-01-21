package cc.newex.dax.perpetual.scheduler.service.impl;

import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.commons.lang.util.DateUtil;
import cc.newex.dax.perpetual.common.constant.PerpetualCacheKeys;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.common.push.PushData;
import cc.newex.dax.perpetual.criteria.LatestTickerExample;
import cc.newex.dax.perpetual.criteria.MarketDataShardingExample;
import cc.newex.dax.perpetual.criteria.PendingShardingExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.LatestTicker;
import cc.newex.dax.perpetual.domain.MarketData;
import cc.newex.dax.perpetual.domain.Pending;
import cc.newex.dax.perpetual.domain.redis.DepthCacheBean;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.MarketDataDTO;
import cc.newex.dax.perpetual.scheduler.service.KlineService;
import cc.newex.dax.perpetual.service.LatestTickerService;
import cc.newex.dax.perpetual.service.MarketDataShardingService;
import cc.newex.dax.perpetual.service.PendingShardingService;
import cc.newex.dax.perpetual.service.cache.CacheService;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.util.ObjectCopyUtil;
import cc.newex.dax.perpetual.util.ShardingUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KlineServiceImpl implements KlineService {
    /**
     * kline缓存条数
     */
    private final static int KLINE_SIZE = 2000;

    @Autowired
    private CacheService cacheService;
    @Autowired
    private MarketDataShardingService marketDataShardingService;
    @Autowired
    private LatestTickerService latestTickerService;
    @Autowired
    private PendingShardingService pendingShardingService;
    @Autowired
    private MarketService marketService;

    @Override
    public void bulidOneMinuteKline(final Contract contract, final KlineEnum klineEnum) {
        log.info("KlineServiceImpl bulidOneMinuteKline begin: {}, {}", contract, klineEnum);
        final MarketData lastKline;
        final List<MarketData> marketDatas = this.marketService.getKlineData(contract, klineEnum);
        if (CollectionUtils.isEmpty(marketDatas)) {
            lastKline = new MarketData();
            final Calendar calendar = Calendar.getInstance();
            // 初始时间设置为当前分钟
            calendar.setTime(new Date());
            calendar.set(Calendar.MILLISECOND, 0);
            lastKline.setCreatedDate(calendar.getTime());
        } else {
            // 如果K线存在，取得最后一条K线数据
            lastKline = marketDatas.get(marketDatas.size() - 1);
        }

        // 获取ticker
        final LatestTickerExample tickerExample = new LatestTickerExample();
        tickerExample.createCriteria().andContractCodeEqualTo(contract.getContractCode());
        LatestTicker lastTicker = this.latestTickerService.getOneByExample(tickerExample);
        if (lastTicker == null) {
            lastTicker = new LatestTicker();
            lastTicker.setContractCode(contract.getContractCode());
            final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(contract);
            //没有指数价格不计算kline
            if (markIndexPriceDTO == null) {
                log.warn("markIndexPriceDTO is null");
                return;
            }
            lastTicker.setHigh(markIndexPriceDTO.getIndexPrice());
            lastTicker.setLow(markIndexPriceDTO.getIndexPrice());
            lastTicker.setFirst(markIndexPriceDTO.getIndexPrice());
            lastTicker.setLast(markIndexPriceDTO.getIndexPrice());
            this.latestTickerService.add(lastTicker);
        }
        // 生成ticker
        this.buildTicker(lastTicker, contract, marketDatas);

        final long minute = DateUtil.betweenMinute(lastKline.getCreatedDate(), new Date());
        Date startTime = lastKline.getCreatedDate();
        int count = 0;
        // 此处不批量操作是因为1s执行一次，常规情况下for循环只会执行一次或两次
        for (int i = 0; i <= minute; i++) {
            // 一分钟的时间
            final Date endTime = DateUtils.addMinutes(startTime, 1);
            BigDecimal lastClose = null;
            // 拿最后一条数据的close
            if (i == 0) {
                // 第一条Data拿倒数第二条的Close
                if (marketDatas.size() > 1) {
                    lastClose = marketDatas.get(marketDatas.size() - 2).getClose();
                }
            } else {
                if (marketDatas.size() > 0) {
                    lastClose = marketDatas.get(marketDatas.size() - 1).getClose();
                }
            }
            final MarketData marketData =
                    this.getMarketDataByTime(contract, klineEnum, startTime, endTime, lastClose);
            startTime = endTime;
            final boolean valid = this.checkMarketData(marketData, lastTicker);
            if (!valid) {
                continue;
            }
            // 最后一条更新
            if (i == 0 && lastKline.getId() != null && marketDatas.size() > 0) {
                // 数据没变化不需要更新
                if (this.isSameMarketData(marketData, lastKline)) {
                    continue;
                }
                marketData.setId(lastKline.getId());
                this.marketDataShardingService.editById(marketData,
                        ShardingUtil.buildContractMarketDataShardTable(contract));
                marketDatas.set(marketDatas.size() - 1, marketData);
            } else {
                // 更新数据库, 需要带id，因为后续edit需要id
                this.marketDataShardingService.add(marketData,
                        ShardingUtil.buildContractMarketDataShardTable(contract));
                marketDatas.add(marketData);
            }
            count++;
            // push kline
            this.pushRedisKline(marketData, contract);
        }
        if (count > 0) {
            // 更新redis缓存
            this.putMarketDatasCache(marketDatas, contract, klineEnum);
        }
        log.info("KlineServiceImpl bulidOneMinuteKline end");
    }

    @Override
    public void bulidKline(final Contract contract, final KlineEnum klineEnum) {
        log.info("KlineServiceImpl bulidKline begin: {}, {}", contract, klineEnum);
        // 需要根据一分钟的Kline生成其他维度的Kline，误差比较小
        final List<MarketData> oneMinuteKlineDatas =
                this.marketService.getKlineData(contract, KlineEnum.MIN1);
        if (CollectionUtils.isEmpty(oneMinuteKlineDatas)) {
            return;
        }
        final List<MarketData> marketDatas = this.marketService.getKlineData(contract, klineEnum);
        final MarketData lastKline;
        if (CollectionUtils.isEmpty(marketDatas)) {
            lastKline = new MarketData();
            lastKline.setCreatedDate(this.getStartDate(klineEnum));
        } else {
            lastKline = marketDatas.get(marketDatas.size() - 1);
        }

        // 获取ticker
        final LatestTickerExample tickerExample = new LatestTickerExample();
        tickerExample.createCriteria().andContractCodeEqualTo(contract.getContractCode());
        final LatestTicker lastTicker = this.latestTickerService.getOneByExample(tickerExample);
        // 生成ticker
        this.buildTicker(lastTicker, contract, oneMinuteKlineDatas);

        final Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(lastKline.getCreatedDate());
        final Date endDate = new Date();
        int i = 0, count = 0;
        // 暂时不考虑批量操作
        while (beginCalendar.getTime().compareTo(endDate) < 0) {
            final Date startTime = beginCalendar.getTime();
            beginCalendar.add(Calendar.MINUTE, klineEnum.getPeriodMinute());
            final MarketData marketData = this.getMarketData(contract, klineEnum, startTime,
                    beginCalendar.getTime(), oneMinuteKlineDatas);
            // 不用比较ticket，因为一分钟的数据是已经和ticket比较过的合法数据
            // 当没有交易时数据不合法
            if (this.isMarketDataValid(marketData)) {
                continue;
            }
            // 最后一条更新
            if (i++ == 0 && lastKline.getId() != null && marketDatas.size() > 0) {
                marketData.setId(lastKline.getId());
                // 数据没变化不需要更新
                if (this.isSameMarketData(marketData, lastKline)) {
                    continue;
                }
                this.marketDataShardingService.editById(marketData,
                        ShardingUtil.buildContractMarketDataShardTable(contract));
                marketDatas.set(marketDatas.size() - 1, marketData);
            } else {
                // 插入数据库，需要带回id，因为后续可能需要根据id更新
                this.marketDataShardingService.add(marketData,
                        ShardingUtil.buildContractMarketDataShardTable(contract));
                marketDatas.add(marketData);
            }
            count++;
            // push kline
            this.pushRedisKline(marketData, contract);
        }
        if (count > 0) {
            this.putMarketDatasCache(marketDatas, contract, klineEnum);
        }
        log.info("KlineServiceImpl bulidKline end");
    }

    /**
     * 生成K线数据
     */
    private MarketData getMarketData(final Contract contract, final KlineEnum klineEnum,
                                     final Date startDate, final Date endDate, final List<MarketData> oneMinuteKlineDatas) {
        final MarketData marketData = new MarketData();
        // 开盘价格
        BigDecimal open = null;
        Date openDate = null;
        // 收盘价格
        BigDecimal close = null;
        Date closeDate = null;
        // 最高价
        BigDecimal high = null;
        // 最低价
        BigDecimal low = null;
        // 获取量
        BigDecimal amount = BigDecimal.ZERO, size = BigDecimal.ZERO;
        for (final MarketData oneMinuteMarketData : oneMinuteKlineDatas) {
            if (oneMinuteMarketData.getCreatedDate() == null
                    || oneMinuteMarketData.getCreatedDate().compareTo(startDate) < 0
                    || oneMinuteMarketData.getCreatedDate().compareTo(endDate) >= 0) {
                continue;
            }
            if (high == null || oneMinuteMarketData.getHigh().compareTo(high) > 0) {
                high = oneMinuteMarketData.getHigh();
            }
            if (low == null || oneMinuteMarketData.getLow().compareTo(low) < 0) {
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
            amount = oneMinuteMarketData.getAmount().add(amount);
            size = oneMinuteMarketData.getSize().add(size);
        }
        marketData.setOpen(open);
        marketData.setHigh(high);
        marketData.setLow(low);
        marketData.setClose(close);
        marketData.setAmount(amount);
        marketData.setSize(size);
        marketData.setContractCode(contract.getContractCode());
        marketData.setType(klineEnum.getType());
        marketData.setCreatedDate(startDate);
        return marketData;
    }

    /**
     * 获取klineEnum对于的时间
     */
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

    @Override
    public void bulidWeekKline(final Contract contract, final KlineEnum klineEnum) {
        log.info("KlineServiceImpl bulidWeekKline begin: {}, {}", contract, klineEnum);
        // 需要根据一分钟的Kline生成其他维度的Kline，误差比较小
        final List<MarketData> oneMinuteKlineDatas =
                this.marketService.getKlineData(contract, KlineEnum.MIN1);
        if (CollectionUtils.isEmpty(oneMinuteKlineDatas)) {
            return;
        }

        final List<MarketData> marketDatas = this.marketService.getKlineData(contract, klineEnum);
        final MarketData lastKline;
        if (CollectionUtils.isEmpty(marketDatas)) {
            lastKline = new MarketData();
            lastKline.setCreatedDate(this.getStartDate(klineEnum));
        } else {
            lastKline = marketDatas.get(marketDatas.size() - 1);
        }

        // 获取ticker
        final LatestTickerExample tickerExample = new LatestTickerExample();
        tickerExample.createCriteria().andContractCodeEqualTo(contract.getContractCode());
        final LatestTicker lastTicker = this.latestTickerService.getOneByExample(tickerExample);
        // 生成ticker
        this.buildTicker(lastTicker, contract, oneMinuteKlineDatas);

        final Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(lastKline.getCreatedDate());
        final Date endDate = new Date();
        int i = 0, count = 0;
        // 暂时不考虑批量操作
        while (beginCalendar.getTime().compareTo(endDate) < 0) {
            final Date startTime = beginCalendar.getTime();
            beginCalendar.add(Calendar.MINUTE, klineEnum.getPeriodMinute());
            final MarketData marketData =
                    this.getWeekMarketData(contract, klineEnum, startTime, beginCalendar.getTime());
            // 不用比较ticket，因为一分钟的数据是已经和ticket比较过的合法数据
            // 当没有交易时数据不合法
            if (this.isMarketDataValid(marketData)) {
                continue;
            }
            // 最后一条更新
            if (i++ == 0 && lastKline.getId() != null && marketDatas.size() > 0) {
                marketData.setId(lastKline.getId());
                // 数据没变化不需要更新
                if (this.isSameMarketData(marketData, lastKline)) {
                    continue;
                }
                this.marketDataShardingService.editById(marketData,
                        ShardingUtil.buildContractMarketDataShardTable(contract));
                marketDatas.set(marketDatas.size() - 1, marketData);
            } else {
                // 插入数据库，需要带回id，因为后续可能需要根据id更新
                this.marketDataShardingService.add(marketData,
                        ShardingUtil.buildContractMarketDataShardTable(contract));
                marketDatas.add(marketData);
            }
            count++;
            // push kline
            this.pushRedisKline(marketData, contract);
        }
        if (count > 0) {
            this.putMarketDatasCache(marketDatas, contract, klineEnum);
        }
        log.info("KlineServiceImpl bulidWeekKline end");
    }

    /**
     * 一周的数据不能用2000条一分钟的数据求和，所以此处查数据库
     */
    private MarketData getWeekMarketData(final Contract contract, final KlineEnum klineEnum,
                                         final Date startDate, final Date endDate) {
        final MarketData marketData = new MarketData();

        final MarketDataShardingExample marketDataOpenExample = new MarketDataShardingExample();
        marketDataOpenExample.createCriteria().andTypeEqualTo(klineEnum.getType())
                .andCreatedDateGreaterThanOrEqualTo(startDate).andCreatedDateLessThan(endDate);
        marketDataOpenExample.setOrderByClause("id asc");
        final MarketData marketDataOpen = this.marketDataShardingService.getOneByExample(
                marketDataOpenExample, ShardingUtil.buildContractMarketDataShardTable(contract));
        if (marketDataOpen == null) {
            return marketData;
        }
        final BigDecimal open = marketDataOpen.getOpen();

        final MarketDataShardingExample marketDataExample = new MarketDataShardingExample();
        marketDataExample.createCriteria().andCreatedDateGreaterThanOrEqualTo(startDate)
                .andCreatedDateLessThan(endDate).andTypeEqualTo(klineEnum.getType());
        final BigDecimal high = this.marketDataShardingService.getHighPrice(marketDataExample,
                ShardingUtil.buildContractMarketDataShardTable(contract));
        final BigDecimal low = this.marketDataShardingService.getLowPrice(marketDataExample,
                ShardingUtil.buildContractMarketDataShardTable(contract));

        final MarketDataShardingExample marketDataCloseExample = new MarketDataShardingExample();
        marketDataCloseExample.createCriteria().andCreatedDateGreaterThanOrEqualTo(startDate)
                .andCreatedDateLessThan(endDate).andTypeEqualTo(klineEnum.getType());
        marketDataCloseExample.setOrderByClause("id desc");
        final MarketData marketDataCLose = this.marketDataShardingService.getOneByExample(
                marketDataCloseExample, ShardingUtil.buildContractMarketDataShardTable(contract));
        if (marketDataCLose == null) {
            return marketData;
        }
        final BigDecimal close = marketDataCLose.getClose();

        final MarketDataShardingExample marketDataSumExample = new MarketDataShardingExample();
        marketDataSumExample.createCriteria().andCreatedDateGreaterThanOrEqualTo(startDate)
                .andCreatedDateLessThan(endDate).andTypeEqualTo(klineEnum.getType());
        final BigDecimal amount = this.marketDataShardingService.selectSumAmount(marketDataSumExample,
                ShardingUtil.buildContractMarketDataShardTable(contract));
        final BigDecimal size = this.marketDataShardingService.selectSumSize(marketDataSumExample,
                ShardingUtil.buildContractMarketDataShardTable(contract));

        marketData.setOpen(open);
        marketData.setHigh(high);
        marketData.setLow(low);
        marketData.setClose(close);
        marketData.setAmount(amount);
        marketData.setSize(size);
        marketData.setCreatedDate(startDate);
        marketData.setContractCode(contract.getContractCode());
        marketData.setType(klineEnum.getType());
        return marketData;
    }

    /**
     * 获取某一时间段 行情数据 一次查询，内存分析数据，一分钟内的数据量不会太大
     */
    private MarketData getMarketDataByTime(final Contract contract, final KlineEnum klineEnum,
                                           final Date startTime, final Date endTime, final BigDecimal lastClose) {
        // 获取最高价格
        final PendingShardingExample pendingShardingExample = new PendingShardingExample();
        pendingShardingExample.createCriteria().andContractCodeEqualTo(contract.getContractCode())
                .andCreatedDateLessThan(endTime).andCreatedDateGreaterThanOrEqualTo(startTime);
        final List<Pending> pendingList = this.pendingShardingService.getByExample(
                pendingShardingExample, ShardingUtil.buildContractPendingShardTable(contract));

        final MarketData marketData = new MarketData();
        // 开盘价格
        BigDecimal open = null;
        Date openDate = null;
        // 收盘价格
        BigDecimal close = null;
        Date closeDate = null;
        // 最高价
        BigDecimal high = null;
        // 最低价
        BigDecimal low = null;
        // 获取量
        BigDecimal amount = BigDecimal.ZERO, size = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(pendingList)) {
            for (final Pending pending : pendingList) {
                final BigDecimal dealPrice = pending.getPrice();
                if (high == null || dealPrice.compareTo(high) > 0) {
                    high = dealPrice;
                }
                if (low == null || dealPrice.compareTo(low) < 0) {
                    low = dealPrice;
                }
                final Date createdDate = pending.getCreatedDate();
                if (createdDate != null) {
                    if (openDate == null || createdDate.before(openDate)) {
                        open = dealPrice;
                        openDate = createdDate;
                    }
                    if (closeDate == null || createdDate.after(openDate)) {
                        close = dealPrice;
                        closeDate = createdDate;
                    }
                }
                amount = amount.add(pending.getAmount());
                size = size.add(pending.getSize());
            }

            marketData.setOpen(lastClose == null ? open : lastClose);
            marketData
                    .setHigh(lastClose == null ? high : (lastClose.compareTo(high) > 0 ? lastClose : high));
            marketData.setLow(lastClose == null ? low : (lastClose.compareTo(low) < 0 ? lastClose : low));
            marketData.setClose(close);
        } else {
            marketData.setOpen(lastClose);
            marketData.setHigh(lastClose);
            marketData.setLow(lastClose);
            marketData.setClose(lastClose);
        }
        marketData.setAmount(amount);
        if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
            marketData.setSize(size.setScale(contract.getMinQuoteDigit(), BigDecimal.ROUND_DOWN));
        } else {
            marketData.setSize(size.setScale(contract.getMinTradeDigit(), BigDecimal.ROUND_DOWN));
        }
        marketData.setContractCode(contract.getContractCode());
        marketData.setType(klineEnum.getType());
        marketData.setCreatedDate(startTime);
        return marketData;
    }

    /**
     * 生成最新成交数据
     */
    private void buildTicker(final LatestTicker ticker, final Contract contract,
                             final List<MarketData> oneMinuteMarketDataList) {
        if (ticker == null) {
            return;
        }

        final Date endTime = new Date();
        final Date startTime = DateUtils.addDays(endTime, -1);

        // 拉取redis计算24小时高低和成交量
        final List<MarketData> marketDataList = oneMinuteMarketDataList.stream()
                .filter(x -> x.getCreatedDate().after(startTime)).collect(Collectors.toList());

        marketDataList.stream().reduce((y, x) -> {
            final MarketData result = new MarketData();
            result.setHigh(y.getHigh().max(x.getHigh()));
            result.setLow(y.getLow().min(x.getLow()));
            result.setAmount(y.getAmount().add(x.getAmount()));
            result.setSize(y.getSize().add(x.getSize()));
            return result;
        }).ifPresent(m -> {
            ticker.setHigh(m.getHigh());
            ticker.setLow(m.getLow());
            ticker.setAmount24(m.getAmount());
            ticker.setSize24(m.getSize());
        });

        // 最新成交，数据来自撮合
        final JSONArray lastDeals = JSONArray.parseArray(
                this.cacheService.getCacheValue(PerpetualCacheKeys.getDealKey(contract.getContractCode())));
        if (!CollectionUtils.isEmpty(lastDeals)) {
            final BigDecimal last = new BigDecimal(lastDeals.getJSONObject(0).getString("price"));
            ticker.setLast(last);
            if (last.compareTo(ticker.getHigh()) > 0) {
                ticker.setHigh(last);
            }
            if (last.compareTo(ticker.getLow()) < 0) {
                ticker.setLow(last);
            }
        }

        final DepthCacheBean depthCacheBean =
                this.marketDataShardingService.getDepthCacheBean(contract.getContractCode());
        ticker.setSell(BigDecimal.ZERO);
        ticker.setBuy(BigDecimal.ZERO);
        if (depthCacheBean != null) {
            if (!CollectionUtils.isEmpty(depthCacheBean.getAsks())) {
                ticker.setSell(depthCacheBean.getAsks().get(0).getPrice());
            }
            if (!CollectionUtils.isEmpty(depthCacheBean.getBids())) {
                ticker.setBuy(depthCacheBean.getBids().get(0).getPrice());
            }
        }

        // change
        marketDataList.stream().findFirst().ifPresent(m -> {
            ticker.setFirst(m.getOpen());
            ticker.setChange24(ticker.getLast().subtract(ticker.getFirst()));
        });
        ticker.setCreatedDate(endTime);

        this.latestTickerService.editById(ticker);
        // 放入缓存
        this.latestTickerService.putTickerRedis(ticker);
        this.latestTickerService.putPushTickerRedis(ticker);
        // 放入推送
        this.latestTickerService.pushRedisTicker(contract, ticker);
    }

    /**
     * 检查marketData
     */
    private boolean checkMarketData(final MarketData marketData, final LatestTicker ticker) {
        // 当前数据无效
        if (this.isMarketDataValid(marketData)) {
            // 获取最新成交价格
            if (this.isLatestTickerValid(ticker)) {
                // 价格无效不插入不补全之前数据
                log.error("KlineServiceImpl checkMarketData LatestTicker is illegal: {}", ticker);
                return false;
            }
            marketData.setOpen(ticker.getLast());
            marketData.setHigh(ticker.getLast());
            marketData.setLow(ticker.getLast());
            marketData.setClose(ticker.getLast());
            marketData.setAmount(BigDecimal.ZERO);
            marketData.setSize(BigDecimal.ZERO);
        }
        return true;
    }

    private boolean isMarketDataValid(final MarketData marketData) {
        if (marketData.getOpen() == null || marketData.getHigh() == null || marketData.getLow() == null
                || marketData.getClose() == null) {
            return true;
        }
        return BigDecimal.ZERO.equals(marketData.getOpen())
                && BigDecimal.ZERO.equals(marketData.getHigh())
                && BigDecimal.ZERO.equals(marketData.getLow())
                && BigDecimal.ZERO.equals(marketData.getClose());
    }

    private boolean isLatestTickerValid(final LatestTicker ticker) {
        if (ticker.getHigh() == null || ticker.getLow() == null || ticker.getFirst() == null
                || ticker.getLast() == null || ticker.getBuy() == null || ticker.getSell() == null) {
            return true;
        }
        return BigDecimal.ZERO.equals(ticker.getHigh()) && BigDecimal.ZERO.equals(ticker.getLow())
                && BigDecimal.ZERO.equals(ticker.getFirst()) && BigDecimal.ZERO.equals(ticker.getLast())
                && BigDecimal.ZERO.equals(ticker.getBuy()) && BigDecimal.ZERO.equals(ticker.getSell());
    }

    /**
     * 判断数据有没有变化，只判断将要修改的数据，新增的不管
     */
    private boolean isSameMarketData(final MarketData marketData, final MarketData lastKline) {
        if (lastKline.getOpen() == null || lastKline.getHigh() == null || lastKline.getLow() == null
                || lastKline.getClose() == null || lastKline.getAmount() == null
                || lastKline.getSize() == null) {
            return false;
        }
        if (marketData.getOpen() == null || marketData.getHigh() == null || marketData.getLow() == null
                || marketData.getClose() == null || marketData.getAmount() == null
                || marketData.getSize() == null) {
            return true;
        }
        // equals有精度问题
        return marketData.getOpen().compareTo(lastKline.getOpen()) == 0
                && marketData.getHigh().compareTo(lastKline.getHigh()) == 0
                && marketData.getLow().compareTo(lastKline.getLow()) == 0
                && marketData.getClose().compareTo(lastKline.getClose()) == 0
                && marketData.getAmount().compareTo(lastKline.getAmount()) == 0
                && marketData.getSize().compareTo(lastKline.getSize()) == 0;
    }

    /**
     *  缓存数据
     */
    private void putMarketDatasCache(List<MarketData> marketDatas, final Contract contract,
                                     final KlineEnum klineEnum) {
        // 删除之前数据只保留最近 2000条数据
        if (marketDatas.size() > KLINE_SIZE) {
            marketDatas = marketDatas.subList(marketDatas.size() - KLINE_SIZE, marketDatas.size());
        }
        this.cacheService.setCacheValue(
                this.marketService.buildKlineKey(contract.getContractCode(), klineEnum),
                JSONObject.toJSONString(marketDatas));
    }

    /**
     * K线推送
     */
    private void pushRedisKline(final MarketData marketData, final Contract contract) {
        final MarketDataDTO marketDataBean = ObjectCopyUtil.map(marketData, MarketDataDTO.class);
        final PushData pushData = new PushData();
        pushData.setBiz(PerpetualConstants.PERPETUAL);
        pushData.setContractCode(contract.getContractCode());
        pushData.setType("CANDLES");
        pushData.setGranularity(KlineEnum.getTypeString(marketData.getType()));
        pushData.setZip(false);

        final List<MarketDataDTO> tickerBeanList = new ArrayList<>();
        tickerBeanList.add(marketDataBean);
        pushData.setData(tickerBeanList);
        this.cacheService.convertAndSend(
                (PerpetualConstants.PERPETUAL + "_" + contract.getPairCode()).toUpperCase(),
                JSONObject.toJSONString(pushData));
    }
}
