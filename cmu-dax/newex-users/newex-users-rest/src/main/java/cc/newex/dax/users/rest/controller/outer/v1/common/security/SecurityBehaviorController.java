package cc.newex.dax.users.rest.controller.outer.v1.common.security;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.domain.UserBehavior;
import cc.newex.dax.users.service.behavior.UserBehaviorService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/users/security/behavior")
public class SecurityBehaviorController {
    @Autowired
    private UserBehaviorService userBehaviorService;

    @GetMapping("/{name}")
    public ResponseResult getUserBehaviorResult(@NotBlank @PathVariable("name") final String name,
                                                final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserBehavior behaviorConf = this.userBehaviorService.getUserBehaviorFromCache(name);
        if (behaviorConf == null) {
            return ResultUtils.failure(BizErrorCodeEnum.NOT_FOUND_BEHAVIOR_NAME);
        }
        return ResultUtils.success(this.userBehaviorService.getUserCheckRuleBehavior(behaviorConf, userId));
    }
}
