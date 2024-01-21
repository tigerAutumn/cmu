package cc.newex.dax.perpetual.rest.controller.outer.v1.ccex;

import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.converter.DealConverter;
import cc.newex.dax.perpetual.common.converter.DepthLineConverter;
import cc.newex.dax.perpetual.common.converter.LatestTickerConverter;
import cc.newex.dax.perpetual.common.converter.MarketDataConverter;
import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import cc.newex.dax.perpetual.criteria.AssetsFeeRateExample;
import cc.newex.dax.perpetual.domain.AssetsFeeRate;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.CurrencyPairBrokerRelation;
import cc.newex.dax.perpetual.domain.Deal;
import cc.newex.dax.perpetual.domain.LatestTicker;
import cc.newex.dax.perpetual.domain.MarketData;
import cc.newex.dax.perpetual.domain.bean.ContractNavigation;
import cc.newex.dax.perpetual.domain.bean.Currency;
import cc.newex.dax.perpetual.domain.redis.DepthCacheBean;
import cc.newex.dax.perpetual.domain.redis.DepthLine;
import cc.newex.dax.perpetual.dto.DepthOrderBookDTO;
import cc.newex.dax.perpetual.dto.LatestTickerDTO;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.rest.controller.base.BaseController;
import cc.newex.dax.perpetual.service.AssetsFeeRateService;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.CurrencyPairBrokerRelationService;
import cc.newex.dax.perpetual.service.CurrencyPairService;
import cc.newex.dax.perpetual.service.LatestTickerService;
import cc.newex.dax.perpetual.service.MarketDataShardingService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.service.common.CurrencyService;
import cc.newex.dax.perpetual.service.common.MarketService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 公共接口数据
 *
 * @author newex-team
 * @date 2018-11-26
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/perpetual/public")
public class PublicController extends BaseController {
    @Resource
    private MarketDataShardingService marketDataShardingService;
    @Resource
    private ContractService contractService;
    @Resource
    private MarketService marketService;
    @Resource
    private CurrencyService currencyService;
    @Resource
    private AssetsFeeRateService assetsFeeRateService;
    @Resource
    private LatestTickerService latestTickerService;
    @Resource
    private UserPositionService userPositionService;
    @Resource
    private CurrencyPairBrokerRelationService currencyPairBrokerRelationService;
    @Resource
    private CurrencyPairService currencyPairService;

    /**
     * 仅用于性能测试
     *
     * @return
     */
    @GetMapping("/test")
    public ResponseResult test() {
        return ResultUtils.success();
    }

    /**
     * 取得所有币种列表
     *
     * @return
     */
    @GetMapping("/currencies")
    public ResponseResult currencyList() {
        final List<Currency> currencyPairList = this.currencyService.listCurrencies();
        return ResultUtils.success(currencyPairList);
    }

    /**
     * 取得所有可用合约列表
     *
     * @return
     */
    @GetMapping("")
    public ResponseResult list() {
        final Integer brokerId = this.getBrokerId();

        final List<CurrencyPairBrokerRelation> relationList = this.currencyPairBrokerRelationService.getByBrokerId(brokerId);

        final List<ContractNavigation> list = Lists.newArrayList();

        final List<String> pairCodeList = relationList.stream()
                .map(CurrencyPairBrokerRelation::getPairCode).collect(Collectors.toList());

        final List<Contract> contractList =
                this.contractService.getUnExpiredPairCodeContract(pairCodeList);

        contractList.stream().forEach(x -> {
            final CurrencyPair currencyPair = this.currencyPairService.getByPairCode(x.getPairCode());
            if (currencyPair.getOnline() == 0) {
                return;
            }

            final ContractNavigation contractNavigation = ContractNavigation.builder()
                    .code(x.getContractCode())
                    .base(x.getBase())
                    .quote(x.getQuote())
                    .env(x.getEnv())
                    .build();

            final LatestTickerDTO latestTicker = LatestTickerConverter.convert(this.latestTickerService.getTickerRedis(x), x);

            if (Objects.nonNull(latestTicker)) {
                final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(x);
                final BigDecimal totalPosition = this.userPositionService.sumTotalUserPosition(x.getContractCode());

                contractNavigation.setDirection(currencyPair.getDirection());
                contractNavigation.setMinTradeDigit(currencyPair.getMinTradeDigit());
                contractNavigation.setMinQuoteDigit(currencyPair.getMinQuoteDigit());
                contractNavigation.setPrice(latestTicker.getLast());
                contractNavigation.setFluctuation(latestTicker.getChangePercentage());
                contractNavigation.setHigh(latestTicker.getHigh());
                contractNavigation.setLow(latestTicker.getLow());
                contractNavigation.setAmount24(latestTicker.getAmount24());
                contractNavigation.setSize24(latestTicker.getSize24());
                contractNavigation
                        .setTotalPosition(totalPosition == null ? null : totalPosition.stripTrailingZeros().toPlainString());
                contractNavigation.setFund(
                        markIndexPriceDTO == null ? null : markIndexPriceDTO.getFeeRate().stripTrailingZeros().toPlainString());
                contractNavigation.setMarkPrice(
                        markIndexPriceDTO == null ? null : markIndexPriceDTO.getMarkPrice().stripTrailingZeros().toPlainString());
                contractNavigation.setIndexPrice(
                        markIndexPriceDTO == null ? null : markIndexPriceDTO.getIndexPrice().stripTrailingZeros().toPlainString());
                contractNavigation.setMaxLever(BigDecimal.ONE
                        .divide(currencyPair.getMaintainRate().add(currencyPair.getMaintainRate())).setScale(PerpetualConstants.LEVER_SCALE, BigDecimal.ROUND_DOWN));
                contractNavigation.setUnitAmount(currencyPair.getUnitAmount());

                list.add(contractNavigation);
            }
        });
        return ResultUtils.success(list);
    }

    /**
     * K线
     *
     * @return
     */
    @GetMapping("/{contractCode}/candles")
    public ResponseResult candles(@PathVariable("contractCode") final String contractCode,
                                  @RequestParam(value = "kline") final String kline,
                                  @RequestParam(value = "since", defaultValue = "0", required = false) final Long since,
                                  @RequestParam(value = "size", defaultValue = "200") final Integer size) {
        final Contract contract = this.checkContract(contractCode, false);

        final KlineEnum klineEnum = this.marketService.getKlineEnum(kline);
        if (Objects.isNull(klineEnum)) {
            return ResultUtils.failure(BizErrorCodeEnum.NO_KLINE_TYPE);
        }

        List<MarketData> marketDataList = this.marketService.getKlineData(contract, klineEnum);
        marketDataList = marketDataList.stream().filter(x -> x.getCreatedDate().getTime() >= since).sorted(Comparator.comparing(MarketData::getCreatedDate)).collect(Collectors.toList());

        final List<Object[]> klineData = MarketDataConverter.toObjectArray(marketDataList, size);
        return ResultUtils.success(klineData);
    }

    /**
     * 取得对应合约深度数据
     *
     * @param contractCode
     * @return
     */
    @GetMapping("/{contractCode}/orderbook")
    public ResponseResult orderbook(@PathVariable("contractCode") final String contractCode,
                                    final String size) {
        this.checkContract(contractCode, false);
        final DepthCacheBean depthCacheBean =
                this.marketDataShardingService.getDepthCacheBean(contractCode);
        final int maxSize = 200;
        final int length = NumberUtils.min(NumberUtils.toInt(size, maxSize), maxSize);
        // 小->大
        final List<DepthLine> askList = depthCacheBean.getAsks();
        // 大->小
        final List<DepthLine> bidList = depthCacheBean.getBids();
        // ask
        final List<String[]> asks = DepthLineConverter.toStringArray(askList, false, length);

        // bid
        final List<String[]> bids = DepthLineConverter.toStringArray(bidList, true, length);

        final DepthOrderBookDTO depthOrderBookDTO =
                DepthOrderBookDTO.builder().asks(asks).bids(bids).build();
        return ResultUtils.success(depthOrderBookDTO);
    }

    /**
     * 获取最新交易数据接口
     *
     * @param contractCode
     * @return
     */
    @GetMapping("/{contractCode}/fills")
    public ResponseResult fills(@PathVariable("contractCode") final String contractCode) {
        final Contract contract = this.checkContract(contractCode, false);
        final List<Deal> dealList = this.marketService.fills(contract);
        final List<Object[]> dealData = DealConverter.toObjectArray(dealList);
        return ResultUtils.success(dealData);
    }

    /**
     * 获取交易行情信息接口
     *
     * @param contractCode
     */
    @GetMapping(value = "/products/{contractCode}/ticker")
    public ResponseResult ticker(@PathVariable final String contractCode) {
        final Contract contract = this.checkContract(contractCode, false);

        final LatestTickerDTO latestTicker =
                LatestTickerConverter.convert(this.latestTickerService.getTickerRedis(contract), contract);
        if (latestTicker == null) {
            return ResultUtils.failure(BizErrorCodeEnum.NO_LATEST_TICKER);
        }
        final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(contract);
        final BigDecimal positionAmount = this.userPositionService.sumTotalUserPosition(contractCode);

        final Object[] latestTickerData =
                LatestTickerConverter.toObjectArray(latestTicker, positionAmount, markIndexPriceDTO);

        return ResultUtils.success(latestTickerData);
    }

    /**
     * 获取交易行情信息接口
     */
    @GetMapping(value = "/products/tickers")
    public ResponseResult tickers() {
        final List<Contract> contractList = this.contractService.getUnExpiredContract();
        final List<Object[]> latestTickerDataList = new ArrayList<>();
        for (final Contract contract : contractList) {
            final LatestTickerDTO latestTicker =
                    LatestTickerConverter.convert(this.latestTickerService.getTickerRedis(contract), contract);
            if (latestTicker == null) {
                return ResultUtils.failure(BizErrorCodeEnum.NO_LATEST_TICKER);
            }
            final MarkIndexPriceDTO markIndexPriceDTO = this.marketService.getMarkIndex(contract);
            final BigDecimal positionAmount = this.userPositionService.sumTotalUserPosition(contract.getContractCode());

            final Object[] latestTickerData =
                    LatestTickerConverter.tickersToObjectArray(latestTicker, positionAmount, markIndexPriceDTO);
            latestTickerDataList.add(latestTickerData);
        }
        return ResultUtils.success(latestTickerDataList.toArray());
    }

    /**
     * 获取资金费用历史
     *
     * @param contractCode
     * @return
     */
    @GetMapping("/{contractCode}/fee-rate")
    public ResponseResult feeRate(@RequestParam(value = "page", required = false, defaultValue = "1") final Integer page,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "100") final Integer pageSize,
                                  @PathVariable(value = "contractCode") final String contractCode) {

        final Contract contract = this.checkContract(contractCode, false);

        final AssetsFeeRateExample example = new AssetsFeeRateExample();
        if (StringUtils.isNotEmpty(contractCode)) {
            example.createCriteria().andContractCodeEqualTo(contractCode);
        }

        final PageInfo pageInfo = new PageInfo();
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_DES);
        pageInfo.setPageSize((pageSize == null || pageSize <= 0 || pageSize >= 100) ? 100 : pageSize);
        pageInfo.setStartIndex((page == null || page <= 1) ? 0 : (page - 1) * pageInfo.getPageSize());

        final LatestTicker latestTicker = this.latestTickerService.getTickerRedis(contract);

        final List<AssetsFeeRate> assetsFeeRateList = this.assetsFeeRateService.getByPage(pageInfo, example);
        assetsFeeRateList.stream().forEach(x -> {
            x.setAmount24(latestTicker.getAmount24());
            x.setSize24(latestTicker.getSize24());
        });
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), assetsFeeRateList));
    }

    /**
     * 获取最新的合约资金费用列表
     *
     * @return
     */
    @GetMapping("/latest-fee-rate")
    public ResponseResult latestFeeRate() {
        final List<Contract> contractList = this.contractService.getUnExpiredContract();
        final List<AssetsFeeRate> assetsFeeRateList = Lists.newArrayList();
        for (final Contract contract : contractList) {
            final LatestTicker latestTicker = this.latestTickerService.getTickerRedis(contract);
            final AssetsFeeRate assetsFeeRate = this.assetsFeeRateService.get(contract.getContractCode());
            assetsFeeRate.setAmount24(latestTicker.getAmount24());
            assetsFeeRate.setSize24(latestTicker.getSize24());
            assetsFeeRateList.add(assetsFeeRate);
        }
        return ResultUtils.success(assetsFeeRateList);
    }
}
