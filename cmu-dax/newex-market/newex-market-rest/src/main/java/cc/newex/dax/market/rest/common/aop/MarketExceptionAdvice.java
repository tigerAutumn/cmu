package cc.newex.dax.market.rest.common.aop;


import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.exception.MarketBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@ResponseBody
@RestControllerAdvice
public class MarketExceptionAdvice {
    /**
     * 200 -
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MarketBizException.class)
    public ResponseResult handleExBizException(final MarketBizException e) {
        MarketExceptionAdvice.log.error("ExBizException {}", e.getMessage());
        return ResultUtils.failure(e.getCode(), e.getMessage());
    }

    /**
     * 访问拒绝
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult handleException(final AccessDeniedException e) {
        MarketExceptionAdvice.log.error(HttpStatus.FORBIDDEN.getReasonPhrase(), e);
        return ResultUtils.failure(403, e.getMessage());
    }

    /**
     * 认证异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseResult handleAuthenticationException(final AuthenticationException e) {
        MarketExceptionAdvice.log.error(HttpStatus.UNAUTHORIZED.getReasonPhrase(), e);
        return ResultUtils.failure(401, e.getMessage());
    }
}
