package cc.newex.dax.push.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.market.client.MarketServiceClient;
import cc.newex.dax.market.dto.result.TickerDto;

/**
 * {@link MarketServiceClient}
 *
 * @author xionghui
 * @date 2018/09/18
 */
@FeignClient(value = "${newex.feignClient.dax.market}", path = "/inner/v1/market/indexes")
public interface FeignMarketServiceClient {

  @GetMapping("/{currencyCode}/ticker")
  ResponseResult<TickerDto> ticker(@PathVariable("currencyCode") final String currencyCode);

  @GetMapping(value = "/{currencyCode}/candles")
  ResponseResult<?> candles(@PathVariable("currencyCode") final String currencyCode,
      @RequestParam("granularity") String granularity, @RequestParam("since") Long since,
      @RequestParam(value = "limit", required = false) Integer limit);
}
