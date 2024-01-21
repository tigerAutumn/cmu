package cc.newex.dax.boss.client;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.dto.domain.UserDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "${newex.feignClient.dax.boss}", path = "/inner/v1/boss")
public interface BossClient {

    /**
     * 按组查询用户信息
     *
     * @param groupIds
     * @return
     */
    @GetMapping(value = "/users")
    ResponseResult<List<UserDTO>> list(@RequestParam("groupIds") String groupIds);

}
