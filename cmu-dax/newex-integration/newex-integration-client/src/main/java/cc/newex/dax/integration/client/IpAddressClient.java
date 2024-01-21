package cc.newex.dax.integration.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.integration.dto.ip.IpLocationDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author: lingqing.wan
 * date: 2018-04-02 下午6:36
 */
@FeignClient("${newex.feignClient.dax.integration}")
public interface IpAddressClient {

    @GetMapping("/inner/v1/integration/ip/info")
    ResponseResult<IpLocationDTO> getIpLocationInfo(@RequestParam("ip") String ip);
}
