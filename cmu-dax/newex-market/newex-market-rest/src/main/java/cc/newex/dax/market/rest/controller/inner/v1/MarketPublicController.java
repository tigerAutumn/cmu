package cc.newex.dax.market.rest.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.common.enums.BizErrorCodeEnum;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.dto.result.ExchangeRateDto;
import cc.newex.dax.market.dto.result.TickerDto;
import cc.newex.dax.market.model.RateInfo;
import cc.newex.dax.market.service.CoinConfigService;
import cc.newex.dax.market.service.MarketPublicService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author allen
 * @date 2018/5/21
 * @des
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/market/indexes")
public class MarketPublicController {

    @Autowired
    private CoinConfigService configService;
    @Autowired
    private MarketPublicService marketPublicService;
    @Autowired
    private RedisService redisService;

    @GetMapping(value = "/{currencyCode}/ticker")
    public ResponseResult<TickerDto> ticker(@PathVariable final String currencyCode) {
        final CoinConfig coinConfig = this.configService.getCoinConfigBySymbolName(currencyCode);
        if (coinConfig == null) {
            return ResultUtils.failure(BizErrorCodeEnum.SYMBOL_ERROR);
        }
        final JSONObject result = this.marketPublicService.buildTicker(coinConfig);
        if (result == null) {
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_DATA_NOT_FOUND);
        }
        final TickerDto tickerDto = JSON.parseObject(JSON.toJSONString(result), TickerDto.class);
        return ResultUtils.success(tickerDto);
    }


    @GetMapping(value = "/exchange-rate")
    public ResponseResult<List<ExchangeRateDto>> exchangeRate() {
        //从缓存查询
        final String object = this.redisService.getInfo(IndexConst.MARKET_RATE_LIST);
        List<ExchangeRateDto> exchangeRateDtoList = new ArrayList<>();
        if (object != null) {
            final List<RateInfo> rateInfoList = JSONObject.parseArray(object, RateInfo.class);
            rateInfoList.stream().forEach(rateInfo -> {
                exchangeRateDtoList.add(ExchangeRateDto.builder()
                        .rate(rateInfo.getRate())
                        .rateType(rateInfo.getRateType())
                        .build());
            });
        }
        return ResultUtils.success(exchangeRateDtoList);
    }
    /**
     * 获取kline数据
     * @param currencyCode
     * @param granularity
     * @param limit
     * @param since
     * @return
     */
    @GetMapping(value = "/{currencyCode}/candles")
    public ResponseResult kline(@PathVariable(value = "currencyCode") final String currencyCode,
                                @RequestParam(value = "granularity", required = false) final String granularity,
                                @RequestParam(value = "limit", required = false,defaultValue = "100") final String limit,
                                @RequestParam(value = "since", required = false) final String since) {
        return this.marketPublicService.kline(currencyCode, granularity, limit, since);
    }
}