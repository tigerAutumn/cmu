package cc.newex.dax.users.rest.controller.outer.v1.common.security;

import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenProvider;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.rest.model.AccessTokenResVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/v1/users/security/oauth")
public class SecurityOAuthController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping(value = "/refresh")
    public ResponseResult refresh(final HttpServletRequest request) {
        final String token = request.getHeader(this.jwtTokenProvider.getJwtConfig().getRequestHeaderName());
        final JwtUserDetails user = JwtTokenUtils.getCurrentLoginUser(request);

        if (this.jwtTokenProvider.validateToken(token, user)) {
            final String refreshedToken = this.jwtTokenProvider.refreshToken(token);
            return ResultUtils.success(
                    AccessTokenResVO.builder()
                            .accessToken(token)
                            .refreshToken(refreshedToken)
                            .scopes(Lists.newArrayList())
                            .build()
            );
        }
        return ResultUtils.failure(BizErrorCodeEnum.REFRESH_TOKEN_FAILURE);
    }
}
