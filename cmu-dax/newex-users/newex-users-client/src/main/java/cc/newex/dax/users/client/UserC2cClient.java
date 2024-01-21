package cc.newex.dax.users.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.dto.security.UserPaymentTypeDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author gi
 * @date 11/8/18
 */
@FeignClient(value = "${newex.feignClient.dax.users}", path = "/inner/v1/users/c2c")
public interface UserC2cClient {

    /**
     * 获取用户支付
     * @param userId
     * @return
     */
    @GetMapping(value = "payment/{userId}")
    ResponseResult<UserPaymentTypeDTO> getPayment(@PathVariable(value = "userId") long userId);


}
