package cc.newex.commons.security.aop;

import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.enums.HttpStatusCode;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局安全相关异常处理器
 *
 * @author newex-team
 * @date 2017/12/09
 **/
@Slf4j
@Order(2)
@RestControllerAdvice
@ConditionalOnProperty(prefix = "newex.security.exception-advice", name = "enabled", matchIfMissing = true)
public class SecurityExceptionAdvice {

    /**
     * 认证异常,未授权
     */
    @Order(4)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseResult handleAuthenticationException(final AuthenticationException e,
                                                        final HttpServletRequest request,
                                                        final HttpServletResponse response) {
        log.warn("AuthenticationException App:{},Url:{},Reason:{},Message:{}", AppEnvConsts.APP_NAME,
                request.getRequestURL(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage());
        return ResultUtils.failure(HttpStatusCode.UNAUTHORIZED);
    }

    /**
     * 访问拒绝
     */
    @Order(5)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult handleAccessDeniedException(final AccessDeniedException e,
                                                      final HttpServletRequest request,
                                                      final HttpServletResponse response) {
        log.warn("AccessDeniedException App:{},Url:{},Reason:{},Message:{}", AppEnvConsts.APP_NAME,
                request.getRequestURL(), HttpStatus.FORBIDDEN.getReasonPhrase(), e.getMessage());
        return ResultUtils.failure(HttpStatusCode.FORBIDDEN);
    }

}