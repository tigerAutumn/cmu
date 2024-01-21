package cc.newex.dax.users.rest.common.aop;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.enums.FormValidErrorEnum;
import cc.newex.dax.users.common.exception.UsersBizException;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.rest.common.limit.exception.RetryLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Order(3)
@RestControllerAdvice
public class UsersExceptionAdvice {
    /**
     * 400 -
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsersBizException.class)
    public ResponseResult handleUsersBizException(final UsersBizException e) {
        log.error("UsersBizException {}", e.getMessage());
        return ResultUtils.failure(e.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException {}", e.getMessage());
        final List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        final Optional<FieldError> optional = fieldErrorList.stream().findFirst();
        if (optional.isPresent()) {
            final FieldError fieldError = optional.get();
            final String errors = FormValidErrorEnum.getFormCode(fieldError.getField()) + " " + fieldError.getDefaultMessage();
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_BAD_DATA.getCode(), errors);
        }
        return ResultUtils.success();
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(RetryLimitException.class)
    public ResponseResult handleRetryLimitException(final RetryLimitException e,
                                                    final HttpServletRequest request,
                                                    final HttpServletResponse response) {
        final Long userId = HttpSessionUtils.getUserId(request);
        final String realIp = IpUtil.getRealIPAddress(request);

        log.warn("RetryLimitException App:{},Url:{},Reason:{},Message:{}",
                AppEnvConsts.APP_NAME, request.getRequestURL(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage() + ", userId: " + userId + ", ip: " + realIp);

        return ResultUtils.failure(BizErrorCodeEnum.REQUEST_TOO_FREQUENTLY_HAS_BEEN_RESTRICTED);
    }
}
