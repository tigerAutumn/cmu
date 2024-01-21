package cc.newex.dax.integration.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.integration.dto.message.BrokerSignConfigDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "${newex.feignClient.dax.integration}", path = "/inner/v1/integration/broker")
public interface BrokerSignConfigClient {

    /**
     * 添加 broker 配置信息
     *
     * @param config
     * @return
     */
    @PostMapping("/batchupdate")
    ResponseResult batchUpdate(@RequestBody final List<BrokerSignConfigDTO> config);
}
