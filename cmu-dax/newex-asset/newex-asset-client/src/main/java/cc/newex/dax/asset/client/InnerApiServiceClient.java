package cc.newex.dax.asset.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.dto.WithdrawDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "${spring.application.name}", path = "/inner/v1/asset")
public interface InnerApiServiceClient {

    @GetMapping(value = "/query/{userId}")
    ResponseResult queryUserBaseInfo(@PathVariable("userId") final long userId);

    @PostMapping(value = "/correct/{userId}")
    ResponseResult kycCorrectData(@PathVariable("userId") final long userId);

    @PostMapping(value = "/{biz}/withdraw/{currency}")
    ResponseResult withdraw(@RequestBody WithdrawDto withdraw);
}