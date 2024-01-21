package cc.newex.commons.security.jwt;

import cc.newex.commons.security.jwt.exception.AbnormalAccessException;
import cc.newex.commons.security.jwt.exception.JwtTokenExpiredException;
import cc.newex.commons.security.jwt.exception.JwtTokenForbiddenException;
import cc.newex.commons.security.jwt.exception.JwtTokenInvalidException;
import cc.newex.commons.security.jwt.exception.JwtTokenNotFoundException;
import cc.newex.commons.security.jwt.exception.SessionExpiredException;
import cc.newex.commons.security.jwt.model.JwtConsts;
import cc.newex.commons.security.jwt.model.JwtPublicClaims;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenProvider;
import cc.newex.commons.security.jwt.util.ValidateUtils;
import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.ucenter.model.SessionInfo;
import cc.newex.commons.ucenter.service.SessionService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author newex-team
 * @date 2017/11/20
 */
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private SessionService sessionService;

    public JwtInterceptor() {
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response, final Object handler) throws Exception {
        log.debug("request url:{}", request.getRequestURL());

        final String tokenName = this.jwtTokenProvider.getJwtConfig().getRequestHeaderName();
        final String token = request.getHeader(tokenName);
        if (StringUtils.isBlank(token)) {
            if (AppEnvConsts.isProductionMode()) {
                throw new JwtTokenNotFoundException();
            }
            //非生产环境方便调试支持从cookie中读取token
            final Cookie tokenCookie = WebUtils.getCookie(request, JwtConsts.TOKEN);
            if (tokenCookie == null || StringUtils.isBlank(tokenCookie.getValue())) {
                throw new JwtTokenNotFoundException();
            }
        }

        JwtUserDetails jwtUserDetails = null;
        try {
            final JwtPublicClaims claims = this.jwtTokenProvider.parseClaims(token);
            jwtUserDetails = this.jwtTokenProvider.getJwtUserDetails(claims);
            this.validateAndRefresh(jwtUserDetails, token, request);
        } catch (final JwtTokenForbiddenException | SessionExpiredException ex) {
            throw ex;
        } catch (final Exception ex) {
            this.clearSession(token, jwtUserDetails);
            if (ex instanceof SignatureException) {
                throw new JwtTokenInvalidException(ex.getMessage(), ex);
            }
            if (ex instanceof ExpiredJwtException) {
                throw new JwtTokenExpiredException();
            }
            if (ex instanceof AbnormalAccessException) {
                throw new AbnormalAccessException(ex.getMessage(), ex);
            }
            throw new JwtTokenInvalidException(ex.getMessage(), ex);
        }

        request.setAttribute(JwtConsts.JWT_CURRENT_USER, jwtUserDetails);
        request.setAttribute(JwtConsts.JWT_CURRENT_USER_ID, jwtUserDetails.getUserId());
        return super.preHandle(request, response, handler);
    }

    private void validateAndRefresh(final JwtUserDetails jwtUserDetails, final String token, final HttpServletRequest request) {
        if (jwtUserDetails.getStatus().equals(JwtConsts.STATUS_TWO_FACTOR)) {
            return;
        }

        final SessionInfo session = this.sessionService.getByToken(token);
        if (Objects.isNull(session)) {
            throw new SessionExpiredException();
        }

        jwtUserDetails.setUsername(session.getUsername());
        jwtUserDetails.setStatus(session.getStatus());
        jwtUserDetails.setFrozen(session.getFrozen());
        jwtUserDetails.setSpotFrozen(session.getSpotFrozen());
        jwtUserDetails.setC2cFrozen(session.getC2cFrozen());
        jwtUserDetails.setContractsFrozen(session.getContractsFrozen());
        jwtUserDetails.setAssetFrozen(session.getAssetFrozen());
        jwtUserDetails.setPerpetualProtocolFlag(session.getPerpetualProtocolFlag());
        if (jwtUserDetails.isForbidden()) {
            throw new JwtTokenForbiddenException();
        }
        //非正常访问
        if (this.jwtTokenProvider.verifyIpAndDevice(
                jwtUserDetails,
                ValidateUtils.getDeviceId(request),
                ValidateUtils.getRequestIP(request))
                ) {
            throw new AbnormalAccessException();
        }

        this.sessionService.refresh(token);
    }

    private void clearSession(final String token, final JwtUserDetails jwtUserDetails) {
        this.sessionService.remove(token);
    }
}
