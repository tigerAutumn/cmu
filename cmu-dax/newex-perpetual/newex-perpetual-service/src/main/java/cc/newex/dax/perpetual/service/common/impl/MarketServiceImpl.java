package cc.newex.dax.perpetual.service.common.impl;

import cc.newex.commons.dictionary.consts.CacheKeys;
import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.commons.lang.cache.CacheUtil;
import cc.newex.dax.perpetual.common.constant.PerpetualCacheKeys;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.ConfigNameEnum;
import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.criteria.MarketDataShardingExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.Deal;
import cc.newex.dax.perpetual.domain.LatestTicker;
import cc.newex.dax.perpetual.domain.MarkPrice;
import cc.newex.dax.perpetual.domain.MarketData;
import cc.newex.dax.perpetual.domain.bean.Currency;
import cc.newex.dax.perpetual.dto.DepthDataDTO;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.MarkIndexReasonablePriceDTO;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import cc.newex.dax.perpetual.service.AssetsFeeRateService;
import cc.newex.dax.perpetual.service.CommonPropService;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.LatestTickerService;
import cc.newex.dax.perpetual.service.MarkPriceService;
import cc.newex.dax.perpetual.service.MarketDataShardingService;
import cc.newex.dax.perpetual.service.cache.CacheService;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.service.common.PushService;
import cc.newex.dax.perpetual.util.BigDecimalUtil;
import cc.newex.dax.perpetual.util.ShardingUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MarketServiceImpl implements MarketService {
    /**
     * kline缓存条数
     */
    private final static int KLINE_SIZE = 2000;
    @Autowired
    private CommonPropService commonPropService;
    @Autowired
    private MarketDataShardingService marketDataShardingService;
    @Autowired
    private AssetsFeeRateService assetsFeeRateService;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private PushService pushService;
    @Autowired
    private LatestTickerService latestTickerService;
    @Autowired
    private MarkPriceService markPriceService;

    @Override
    public MarkIndexPriceDTO getMarkIndex(final Contract contract) {
        final String cacheKey = PerpetualCacheKeys.getMarketPriceKey();

        return this.cacheService.hgetCacheObject(cacheKey, contract.getContractCode(), MarkIndexPriceDTO.class);
    }

    @Override
    public void removeNotInContractMarkIndex(final List<Contract> contracts) {
        final String cacheKey = PerpetualCacheKeys.getMarketPriceKey();
        final List<String> codes =
                contracts.stream().map(Contract::getContractCode).collect(Collectors.toList());
        final Map<String, MarkIndexPriceDTO> priceMap = this.cacheService.hmgetAllCacheValue(cacheKey, MarkIndexPriceDTO.class);
        priceMap.keySet().removeAll(codes);
        this.cacheService.hdelCacheObject(cacheKey, priceMap.keySet().toArray(new String[0]));
    }

    @Override
    public List<MarkIndexPriceDTO> getMarkIndexList(final List<Contract> contract) {
        if (CollectionUtils.isEmpty(contract)) {
            return new ArrayList<>(0);
        }
        final String cacheKey = PerpetualCacheKeys.getMarketPriceKey();
        final List<String> codes =
                contract.stream().map(Contract::getContractCode).collect(Collectors.toList());
        return this.cacheService
                .hmgetCacheValue(cacheKey, MarkIndexPriceDTO.class, codes.toArray(new String[0])).stream()
                .filter(x -> x != null).collect(Collectors.toList());
    }

    @Override
    public Map<String, MarkIndexPriceDTO> allMarkIndexPrice() {
        final String cacheKey = PerpetualCacheKeys.getMarketPriceKey();
        return this.cacheService.hmgetAllCacheValue(cacheKey, MarkIndexPriceDTO.class);
    }

    @Override
    public DepthDataDTO getFirstDepthData(final Contract contract) {

        final String cacheValue = this.cacheService
                .getCacheValue(PerpetualCacheKeys.getFirstDepthData(contract.getContractCode()));
        if (StringUtils.isBlank(cacheValue)) {
            return null;
        }

        final DepthDataDTO dto = JSONObject.parseObject(cacheValue, DepthDataDTO.class);
        return dto;
    }

    @Override
    public void scheduleMarketPrice(final Contract contract) {
        final LatestTicker tickerRedis = this.latestTickerService.getTickerRedis(contract);

        final String cacheKey = PerpetualCacheKeys.getMarketPriceKey();

        // 指数价格
        final BigDecimal indexPrice = this.getIndexPrice(contract.getIndexBase(), 2);
        if (indexPrice == null) {
            return;
        }

        final CurrencyPair currencyPair = this.currencyPairService.getByPairCode(contract.getPairCode());
        if (currencyPair == null) {
            log.error("not found currencyPair, pairCode : {}", contract.getPairCode());
            return;
        }

        final BigDecimal feeRate = this.assetsFeeRateService.takeFeeRate(contract.getContractCode());
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(contract.getLiquidationDate());
        calendar.add(Calendar.HOUR_OF_DAY, currencyPair.getLiquidationHour() * -1);
        final BigDecimal estimateFeeRate = this.assetsFeeRateService.takeFeeRate(contract, currencyPair,
                calendar.getTime(), contract.getLiquidationDate());

        final Long time = currencyPair.getLiquidationHour() * 1000 * 60 * 60L;
        final BigDecimal feeBaseRate =
                BigDecimalUtil.multiply(feeRate, BigDecimalUtil.divide(new BigDecimal(((contract.getLiquidationDate().getTime() / (60 * 1000L) - System.currentTimeMillis() / (60 * 1000L)))).max(BigDecimal.ZERO), new BigDecimal((time / (60 * 1000L)))));

        final BigDecimal cacheValue = BigDecimalUtil.multiply(indexPrice, BigDecimal.ONE.add(feeBaseRate)).setScale(contract.getMarketPriceDigit(), BigDecimal.ROUND_DOWN);

        final MarkIndexPriceDTO priceDTO = MarkIndexPriceDTO.builder()
                .indexPrice(indexPrice)
                .markPrice(cacheValue)
                .baseCurrency(contract.getBase())
                .quoteCurrency(contract.getQuote())
                .contractCode(contract.getContractCode())
                .feeRate(feeRate)
                .estimateFeeRate(estimateFeeRate)
                .lastPrice(tickerRedis != null ? tickerRedis.getLast() : BigDecimal.ZERO)
                .build();

        this.cacheService.hsetCacheValue(cacheKey, contract.getContractCode(), JSON.toJSONString(priceDTO));

        this.pushService.pushData(contract, PushTypeEnum.FUND_RATE, JSON.toJSONString(priceDTO), true, false, false);

        final ModelMapper mapper = new ModelMapper();
        final MarkPrice record = mapper.map(priceDTO, MarkPrice.class);
        record.setCreatedDate(new Date());
        record.setModifyDate(new Date());

        this.markPriceService.add(record);
    }

    @Override
    public MarkIndexReasonablePriceDTO getReasonablePrice(final Contract contract,
                                                          final BigDecimal diffMargin) {

        final MarkIndexPriceDTO price = this.getMarkIndex(contract);

        if (price == null) {
            return null;
        }

        final BigDecimal max, min;
        if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
            max = BigDecimalUtil.divide(price.getMarkPrice(), BigDecimal.ONE.subtract(diffMargin).add(price.getFeeRate().abs()),
                    PerpetualConstants.SCALE);
            min = BigDecimalUtil.divideUp(price.getMarkPrice(), BigDecimal.ONE.add(diffMargin).subtract(price.getFeeRate().abs()),
                    PerpetualConstants.SCALE);
        } else {
            max = price.getMarkPrice()
                    .multiply(BigDecimal.ONE.add(diffMargin).subtract(price.getFeeRate().abs()))
                    .setScale(PerpetualConstants.SCALE, BigDecimal.ROUND_DOWN);
            min = price.getMarkPrice()
                    .multiply(BigDecimal.ONE.subtract(diffMargin).add(price.getFeeRate().abs()))
                    .setScale(PerpetualConstants.SCALE, BigDecimal.ROUND_UP);
        }

        final MarkIndexReasonablePriceDTO map =
                new ModelMapper().map(price, MarkIndexReasonablePriceDTO.class);
        map.setMaxReasonablePrice(max);
        map.setMinReasonablePrice(min);
        return map;
    }

    private BigDecimal getIndexPrice(final String currencyCode, final int scale) {
        final String cacheValue = this.cacheService.getCacheValue(PerpetualCacheKeys.getPerpetualIndexPriceKey(currencyCode));

        if (StringUtils.isBlank(cacheValue)) {
            return null;
        }

        return new BigDecimal(JSON.parseObject(cacheValue).getString("price")).setScale(scale, BigDecimal.ROUND_DOWN);
    }

    @Override
    public String buildKlineKey(final String contractCode, final KlineEnum klineEnum) {
        return new StringBuilder().append(PerpetualConstants.PERPETUAL).append(CacheKeys.DELIMITER)
                .append(CacheKeys.MARKET_PREFIX).append(CacheKeys.DELIMITER).append(contractCode)
                .append(CacheKeys.DELIMITER).append(klineEnum.getTypeStr()).toString().toUpperCase();
    }

    @Override
    public List<MarketData> getKlineData(final Contract contract, final KlineEnum klineEnum) {
        final List<MarketData> marketDatas;
        final String klineJson =
                this.cacheService.getCacheValue(this.buildKlineKey(contract.getContractCode(), klineEnum));
        // 缓存为空
        final String reloadSwitch =
                this.commonPropService.getConfigObject(PerpetualConstants.DEFAULT_BROKERID,
                        ConfigNameEnum.KLINE_RELOAD_SWITCH.getName(), String.class);
        if (org.springframework.util.StringUtils.isEmpty(klineJson)
                || "1".equalsIgnoreCase(reloadSwitch)) {
            marketDatas = this.getMarketDatas(contract, klineEnum);
        } else {
            marketDatas = JSONArray.parseArray(klineJson, MarketData.class);
        }
        return marketDatas == null ? new ArrayList<>() : marketDatas;
    }

    @Override
    public KlineEnum getKlineEnum(final String klineStr) {
        final KlineEnum klineEnum = Arrays.stream(KlineEnum.values())
                .filter(x -> x.getTypeStr().equalsIgnoreCase(klineStr)).findFirst().orElse(null);
        if (klineEnum == null) {
            return null;
        }
        return klineEnum;
    }

    @Override
    public List<MarketData> getMarketDatas(final Contract contract, final KlineEnum klineEnum) {
        final MarketDataShardingExample marketDataExample = new MarketDataShardingExample();
        marketDataExample.setOrderByClause("id desc limit 0," + MarketServiceImpl.KLINE_SIZE);
        marketDataExample.createCriteria().andContractCodeEqualTo(contract.getContractCode())
                .andTypeEqualTo(klineEnum.getType());
        final List<MarketData> marketDatas = this.marketDataShardingService
                .getByExample(marketDataExample, ShardingUtil.buildContractMarketDataShardTable(contract));
        if (CollectionUtils.isNotEmpty(marketDatas)) {
            Collections.reverse(marketDatas);
        }
        return marketDatas;
    }

    @Override
    public List<Deal> fills(final Contract contract) {
        final String value =
                this.cacheService.getCacheValue(PerpetualCacheKeys.getDealKey(contract.getContractCode()));
        if (StringUtils.isNotEmpty(value)) {
            final List<Deal> dealList = JSON.parseArray(value, Deal.class);
            return dealList;
        }

        return null;
    }

    @Override
    public List<Currency> getCurrencies() {
        final String value = CacheUtil.getCache(CacheKeys.getCurrencyKey(),
                () -> this.cacheService.getCacheValue(CacheKeys.getCurrencyKey()));

        return JSONObject.parseArray(value, Currency.class);
    }

}
