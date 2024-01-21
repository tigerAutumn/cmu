package cc.newex.dax.push.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;

/**
 * {@link UsersClient}
 *
 * @author xionghui
 * @date 2018/09/13
 */
@FeignClient(value = "${newex.feignClient.dax.users}", path = "/inner/v1/users")
public interface FeignUsersClient {

  @GetMapping("/userInfo/{apiKey}")
  ResponseResult<UserInfoResDTO> getUserInfoByApiKey(@PathVariable("apiKey") final String apiKey,
      @RequestParam("passphrase") final String passphrase);
}
