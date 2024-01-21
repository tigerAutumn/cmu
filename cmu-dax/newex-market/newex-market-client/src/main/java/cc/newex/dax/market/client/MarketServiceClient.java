package cc.newex.dax.market.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.market.dto.result.ExchangeRateDto;
import cc.newex.dax.market.dto.result.TickerDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value = "${newex.feignClient.dax.market}", path = "/inner/v1/market/indexes")
public interface MarketServiceClient {

    /**
     * ticker
     *
     * @return
     */
    @GetMapping("/{currencyCode}/ticker")
    ResponseResult<TickerDto> ticker(@PathVariable("currencyCode") final String currencyCode);


    /**
     * candles
     *
     * @return
     */
    @GetMapping(value = "/{currencyCode}/candles")
    ResponseResult candles(@PathVariable("currencyCode") final String currencyCode, @RequestParam("granularity") String granularity,
                           @RequestParam("since") Long since, @RequestParam(value = "limit", required = false) Integer limit);


    /**
     * 查询平台汇率
     *
     * @return
     */
    @GetMapping(value = "/exchange-rate")
    ResponseResult<List<ExchangeRateDto>> exchangeRate();

    /**
     * 获取kline数据
     * @param currencyCode
     * @param granularity
     * @param limit
     * @param since
     * @return
     */
    @GetMapping(value = "/{currencyCode}/candles")
    ResponseResult kline(@PathVariable(value = "currencyCode") final String currencyCode,
                         @RequestParam(value = "granularity", required = false) final String granularity,
                         @RequestParam(value = "limit", required = false, defaultValue = "100") final String limit,
                         @RequestParam(value = "since", required = false) final String since);
}