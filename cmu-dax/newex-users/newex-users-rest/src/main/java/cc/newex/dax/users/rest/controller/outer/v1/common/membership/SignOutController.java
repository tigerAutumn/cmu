package cc.newex.dax.users.rest.controller.outer.v1.common.membership;

import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户注销控制器
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/users/membership/sign-out")
public class SignOutController {
    @GetMapping(value = "")
    public ResponseResult logout(final HttpServletRequest request, final HttpServletResponse response) {
        JwtTokenUtils.clearSession(request);
        return ResultUtils.success();
    }
}
