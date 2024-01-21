package cc.newex.dax.market.client.admin;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.market.dto.result.CoinConfigDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by wj on 2018/7/26.
 */
@FeignClient(value = "${newex.feignClient.dax.market}", path = "/admin/v1/market/coin/config")
public interface CoinConfigClient {

    @GetMapping(value = "list")
    ResponseResult<List<CoinConfigDto>> list();

    @PostMapping(value = "update")
    ResponseResult create(@RequestBody CoinConfigDto coinConfigDto);
}
