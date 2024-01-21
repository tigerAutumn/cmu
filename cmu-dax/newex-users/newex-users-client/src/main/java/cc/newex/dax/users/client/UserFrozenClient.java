package cc.newex.dax.users.client;

import cc.newex.commons.support.model.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author newex-team
 * @date 2018-07-08
 */
@FeignClient(value = "${newex.feignClient.dax.users}", path = "/inner/v1/users/frozen")
public interface UserFrozenClient {
    /**
     * 从缓存中(redis/memcached/etc.)获取全站交易业务冻结状态,表示所有用户都不能进行所有或相关业务(spot,c2c,contracts等)交易
     *
     * @param name {@link cc.newex.dax.users.dto.common.GlobalFrozenEnum}
     * @return true|false
     */
    @GetMapping(value = "/global/{name}")
    ResponseResult getGlobalFrozenStatus(@PathVariable("name") final String name);

    /**
     * 从缓存中(redis/memcached/etc.)获取用户各业务(spot,c2c,contracts等)冻结状态
     *
     * @param bizType {@link cc.newex.dax.users.dto.common.BizTypeEnum}
     * @param userId  用户id
     * @return true|false
     */
    @GetMapping(value = "/{bizType}/{userId}")
    ResponseResult getUserFrozenStatus(
            @PathVariable("bizType") final String bizType,
            @PathVariable("userId") final long userId);
}
