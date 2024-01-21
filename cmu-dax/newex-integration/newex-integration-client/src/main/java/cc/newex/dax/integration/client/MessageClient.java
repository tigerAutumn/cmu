package cc.newex.dax.integration.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("${newex.feignClient.dax.integration}")
public interface MessageClient {

    @PostMapping("/inner/v1/integration/messages")
    ResponseResult<String> send(@RequestBody MessageReqDTO dto);
}
