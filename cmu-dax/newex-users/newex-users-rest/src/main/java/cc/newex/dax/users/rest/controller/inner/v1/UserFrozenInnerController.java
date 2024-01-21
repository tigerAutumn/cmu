package cc.newex.dax.users.rest.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.dto.common.BizTypeEnum;
import cc.newex.dax.users.service.frozen.UserFrozenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author newex-team
 * @date 2018-07-08
 */
@Slf4j
@RestController
@RequestMapping(value = "/inner/v1/users/frozen")
public class UserFrozenInnerController {
    @Autowired
    private UserFrozenService userFrozenService;

    /**
     * 从缓存中(redis/memcached/etc.)获取全站交易业务冻结状态,表示所有用户都不能进行所有或相关业务(spot,c2c,contracts等)交易
     *
     * @param name {@link cc.newex.dax.users.dto.common.GlobalFrozenEnum}
     */
    @GetMapping(value = "/global/{name}")
    public ResponseResult getGlobalFrozenStatus(@PathVariable("name") final String name) {
        return ResultUtils.success(BooleanUtils.toBoolean(this.userFrozenService.getGlobalFrozen(name)));
    }

    /**
     * 从缓存中(redis/memcached/etc.)获取用户各业务(spot,c2c,contracts等)冻结状态
     *
     * @param bizType {@link cc.newex.dax.users.dto.common.BizTypeEnum}
     * @param userId  用户id
     * @return true|false
     */
    @GetMapping(value = "/{bizType}/{userId}")
    public ResponseResult getUserFrozenStatus(
            @PathVariable("bizType") final String bizType,
            @PathVariable("userId") final long userId) {
        final BizTypeEnum type = BizTypeEnum.forName(bizType);
        if (type == BizTypeEnum.ALL) {
            return ResultUtils.success(BooleanUtils.toBoolean(this.userFrozenService.getUserFrozenStatus(userId)));
        }
        if (type == BizTypeEnum.SPOT) {
            return ResultUtils.success(BooleanUtils.toBoolean(this.userFrozenService.getUserSpotFrozenStatus(userId)));
        }
        if (type == BizTypeEnum.C2C) {
            return ResultUtils.success(BooleanUtils.toBoolean(this.userFrozenService.getUserC2CFrozenStatus(userId)));
        }
        if (type == BizTypeEnum.CONTRACTS) {
            return ResultUtils.success(BooleanUtils.toBoolean(this.userFrozenService.getUserContractsFrozenStatus(userId)));
        }
        if (type == BizTypeEnum.ASSET) {
            return ResultUtils.success(BooleanUtils.toBoolean(this.userFrozenService.getUserAssetFrozenStatus(userId)));
        }
        return ResultUtils.failure("Not Found Biz Type");
    }
}
