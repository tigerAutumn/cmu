package cc.newex.dax.perpetual.openapi.controller;

import cc.newex.commons.openapi.specs.annotation.OpenApi;
import cc.newex.commons.openapi.specs.annotation.OpenApiRateLimit;
import cc.newex.commons.openapi.specs.enums.RateLimitStrategyEnum;
import cc.newex.commons.openapi.support.utils.DateUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.converter.DealConverter;
import cc.newex.dax.perpetual.common.converter.DepthLineConverter;
import cc.newex.dax.perpetual.common.converter.LatestTickerConverter;
import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Deal;
import cc.newex.dax.perpetual.domain.redis.DepthCacheBean;
import cc.newex.dax.perpetual.domain.redis.DepthLine;
import cc.newex.dax.perpetual.dto.DepthOrderBookDTO;
import cc.newex.dax.perpetual.dto.LatestTickerDTO;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.ServerTimeDTO;
import cc.newex.dax.perpetual.openapi.controller.base.BaseController;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.LatestTickerService;
import cc.newex.dax.perpetual.service.MarketDataShardingService;
import cc.newex.dax.perpetual.service.UserPositionService;
import cc.newex.dax.perpetual.service.common.MarketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * V1 open api product 相关接口
 *
 * @author newex-team
 * @date 2018/11/01
 */
@Slf4j
@OpenApi
@RestController
@RequestMapping("/api/v1/perpetual/public")
public class PublicController extends BaseController {

    @Autowired
    private MarketDataShardingService marketDataShardingService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private LatestTickerService latestTickerService;
    @Autowired
    private UserPositionService userPositionService;

    /**
     * 获取服务端时间
     *
     * @return 返回系统时间
     */
    @GetMapping(value = "/time")
    @OpenApiRateLimit(value = "10/1", strategy = RateLimitStrategyEnum.IP)
    public ServerTimeDTO getTime() {
        final Instant now = Instant.now();
        return ServerTimeDTO.builder()
                .epoch(DateUtils.getEpochTime(now.toEpochMilli()))
                .iso(DateUtils.getIsoTime(new Date(now.toEpochMilli())))
                .timestamp(now.toEpochMilli())
                .build();
    }

    /**
     * 深度信息
     *
     * @param contractCode 合约如： p_etc_eth
     * @param size         返回的最大条数限制，默认最大值为 200
     * @return 深度信息
     */
    @GetMapping("/products/{contractCode}/orderbook")
    @OpenApiRateLimit(value = "10/1", strategy = RateLimitStrategyEnum.IP)
    public DepthOrderBookDTO getBookInfo(@PathVariable("contractCode") final String contractCode,
                                         @RequestParam(defaultValue = "200") final Integer size) {

        final Contract contract = this.contractService.getContract(contractCode);
        if (Objects.isNull(contract)) {
            return null;
        }
        final DepthCacheBean depthCacheBean = this.marketDataShardingService.getDepthCacheBean(contractCode);
        final int length = NumberUtils.min(size, 200);
        // 小->大
        final List<DepthLine> askList = depthCacheBean.getAsks();
        // 大->小
        final List<DepthLine> bidList = depthCacheBean.getBids();
        // ask
        final List<String[]> asks = DepthLineConverter.toStringArray(askList, false, length);

        // bid
        final List<String[]> bids = DepthLineConverter.toStringArray(bidList, true, length);

        final DepthOrderBookDTO depthOrderBookDTO = DepthOrderBookDTO.builder().asks(asks).bids(bids).build();

        return depthOrderBookDTO;
    }

    /**
     * 获取最新交易数据接口
     */
    @GetMapping("/{contractCode}/fills")
    @OpenApiRateLimit(value = "10/1", strategy = RateLimitStrategyEnum.IP)
    public ResponseResult fills(@PathVariable("contractCode") final String contractCode) {

        final Contract contract = this.contractService.getContract(contractCode);
        if (Objects.isNull(contract)) {
            return ResultUtils.failure(BizErrorCodeEnum.NO_CONTRACT);
        }
        final List<Deal> dealList = this.marketService.fills(contract);
        final List<Object[]> dealData = DealConverter.toObjectArray(dealList);

        return ResultUtils.success(dealData);
    }

    /**
     * 获取交易行情信息接口
     */
    @GetMapping("/products/tickers")
    @OpenApiRateLimit(value = "10/1", strategy = RateLimitStrategyEnum.IP)
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
}
