package cc.newex.dax.market.client.inner;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.market.dto.result.PortfolioResultDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wj on 2018/7/26.
 */
@FeignClient(value = "${newex.feignClient.dax.market}", path = "/inner/v1/market/portfolio")
public interface PortfolioClient {
    @GetMapping(value = "detail")
    ResponseResult<PortfolioResultDto> portfolioDetail(@RequestParam(value = "name", required = false) final String name);
}
