package cc.newex.dax.market.rest.controller.outer.v1.common;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.common.enums.BizErrorCodeEnum;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.dto.enums.CoinConfigStatusEnum;
import cc.newex.dax.market.dto.model.ComparisonInfo;
import cc.newex.dax.market.dto.model.TickerInfo;
import cc.newex.dax.market.rest.common.aop.ControllerLog;
import cc.newex.dax.market.service.CoinConfigService;
import cc.newex.dax.market.service.MarketPublicService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@RestController
@RequestMapping("/v1/market/indexes")
@Slf4j
public class MarketIndexController {

    @Autowired
    private CoinConfigService configService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MarketPublicService marketPublicService;
    @Autowired
    private CoinConfigService coinConfigService;

    /**
     * 指数行情接口
     */
    @ControllerLog()
    @GetMapping(value = "/{currencyCode}/ticker")
    public ResponseResult ticker(@PathVariable final String currencyCode, final HttpServletRequest request) {
        final CoinConfig coinConfig = this.configService.getCoinConfigBySymbolName(currencyCode);
        if (coinConfig == null) {
            return ResultUtils.failure(BizErrorCodeEnum.SYMBOL_ERROR);
        }

        final JSONObject result = this.marketPublicService.buildTicker(coinConfig);
        if (result == null) {
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_DATA_NOT_FOUND);
        }
        return ResultUtils.success(result);
    }

    /**
     * 所有指数行情查询接口
     */
    @ControllerLog()
    @GetMapping(value = "/tickers")
    public ResponseResult tickers() {
        final List<CoinConfig> coinConfigList = this.configService.getAllCoinConfigListFromCache();

        if (CollectionUtils.isEmpty(coinConfigList)) {
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_DATA_NOT_FOUND);
        }

        //组合指数列表
        final List<CoinConfig> portfolioList = coinConfigService.getAllPortfolioCoinConfigListFromCache();
        if (CollectionUtils.isNotEmpty(portfolioList)) {
            coinConfigList.addAll(portfolioList);
        }

        final JSONArray jsonArray = new JSONArray();
        for (final CoinConfig coinConfig : coinConfigList) {

            //只返回上线的指数数据
            if (coinConfig.getStatus() != CoinConfigStatusEnum.ON_LINE.getCode()) {
                continue;
            }

            final JSONObject result = this.marketPublicService.buildTicker(coinConfig);
            if (result == null) {
                continue;
            }
            result.put("currencyCode", coinConfig.getSymbolName());
            jsonArray.add(result);
        }
        return ResultUtils.success(jsonArray);
    }


    /**
     * 指数k线
     */
    @ControllerLog()
    @GetMapping(value = "/{currencyCode}/candles")
    public ResponseResult kline(@PathVariable final String currencyCode, final HttpServletRequest request) {
        return this.marketPublicService.kline(currencyCode, request.getParameter("granularity"), request.getParameter("limit"), request.getParameter("since"));
    }

    /**
     * 行情对比 全球交易所
     */
    @ControllerLog()
    @GetMapping("/{currencyCode}/comparison")
    public ResponseResult tickets(@PathVariable final String currencyCode, final HttpServletRequest request) {
        final CoinConfig coinConfig = this.configService.getCoinConfigBySymbolName(currencyCode);
        if (coinConfig == null) {
            return ResultUtils.failure(BizErrorCodeEnum.SYMBOL_ERROR);
        }
        final String ticketsObj = this.redisService.getInfo(IndexConst.KEY_LAST_INDEX_CALCULATION + coinConfig.getSymbol());
        final List<ComparisonInfo> list = new ArrayList<>();
        if (ticketsObj != null) {
            final JSONObject tickets = JSON.parseObject(ticketsObj);
            final List<TickerInfo> tickerInfoList = JSON.parseObject(tickets.getString("tickerArray"), new TypeReference<List<TickerInfo>>() {
            });
            tickerInfoList.stream().forEach(tickerDto -> {
                list.add(ComparisonInfo.builder()
                        .name(tickerDto.getName())
                        .price(tickerDto.getLast().setScale(4, BigDecimal.ROUND_HALF_UP))
                        .build());
            });
        }
        return ResultUtils.success(list);
    }
}
