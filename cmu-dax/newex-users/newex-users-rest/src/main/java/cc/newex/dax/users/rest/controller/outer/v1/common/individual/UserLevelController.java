package cc.newex.dax.users.rest.controller.outer.v1.common.individual;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.domain.UserLevel;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.service.membership.UserLevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/v1/users/level")
public class UserLevelController extends BaseController {

    @Autowired
    private UserLevelService userLevelService;

    /**
     * @param request
     * @description 获取用户等级
     * @date 2018/7/7 下午1:35
     */
    @GetMapping("")
    public ResponseResult getUserLevel(final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        final UserLevel userLevel = this.userLevelService.getByUserId(userId);
        return ResultUtils.success(userLevel);
    }
}
