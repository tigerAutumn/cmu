package cc.newex.commons.support.aop;

import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.enums.HttpStatusCode;
import cc.newex.commons.support.enums.SystemErrorCode;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

/**
 * 全局系统公共异常处理器
 *
 * @author newex-team
 * @date 2017/12/09
 **/
@Slf4j
@Order
@RestControllerAdvice
@ConditionalOnProperty(prefix = "newex.web.support.exception-advice", name = "enabled", matchIfMissing = true)
public class CommonExceptionAdvice {
    private static final String BROKEN_PIPE = "Broken pipe";

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult handleHttpMessageNotReadableException(final HttpMessageNotReadableException e,
                                                                final HttpServletRequest request,
                                                                final HttpServletResponse response) {
        log.warn("HttpMessageNotReadable App:{},Url:{},Reason:{},Message:{}",
                AppEnvConsts.APP_NAME, request.getRequestURL(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
        return ResultUtils.failure(SystemErrorCode.HTTP_MESSAGE_NOT_READABLE);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseResult handleValidationException(final ValidationException e,
                                                    final HttpServletRequest request,
                                                    final HttpServletResponse response) {
        log.warn("Validation Exception App:{},Url:{},Reason:{},Message:{}",
                AppEnvConsts.APP_NAME, request.getRequestURL(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
        return ResultUtils.failure(SystemErrorCode.DATA_VALIDATION_FAILURE);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseResult handleMissingServletRequestParameterException(final MissingServletRequestParameterException e,
                                                                        final HttpServletRequest request,
                                                                        final HttpServletResponse response) {
        log.warn("Validation Exception App:{},Url:{},Reason:{},Message:{}",
                AppEnvConsts.APP_NAME, request.getRequestURL(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
        return ResultUtils.failure(SystemErrorCode.METHOD_ARGUMENT_NOT_VALID);
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseResult handleBindException(final BindException e,
                                              final HttpServletRequest request,
                                              final HttpServletResponse response) {
        log.warn("BindException App:{},Url:{},Reason:{},Message:{}",
                AppEnvConsts.APP_NAME, request.getRequestURL(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
        return ResultUtils.failure(SystemErrorCode.DATA_BIND_VALIDATION_FAILURE);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handleMethodArgumentNotValidException(final MethodArgumentNotValidException e,
                                                                final HttpServletRequest request,
                                                                final HttpServletResponse response) {
        log.warn("MethodArgumentNotValid App:{},Url:{},Reason:{},Message:{}",
                AppEnvConsts.APP_NAME, request.getRequestURL(),
                HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), e.getMessage());
        return ResultUtils.failure(SystemErrorCode.DATA_BIND_VALIDATION_FAILURE);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLException.class)
    public ResponseResult handleSQLException(final SQLException e,
                                             final HttpServletRequest request,
                                             final HttpServletResponse response) {
        log.warn("SQLException App:{},Url:{}", AppEnvConsts.APP_NAME, request.getRequestURL(), e);
        return ResultUtils.failure(SystemErrorCode.SQL_EXECUTE_FAILURE);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult handleSQLException(final ConstraintViolationException e,
                                             final HttpServletRequest request,
                                             final HttpServletResponse response) {
        log.warn("ConstraintViolationException App:{},Url:{}", AppEnvConsts.APP_NAME, request.getRequestURL(), e);
        final Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (final ConstraintViolation item : constraintViolations) {
            log.info("Violation Error Item:{},Message:{}", item.getPropertyPath(), item.getMessage());
        }
        return ResultUtils.failure(SystemErrorCode.DATA_VALIDATION_FAILURE, e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e,
                                                                       final HttpServletRequest request,
                                                                       final HttpServletResponse response) {
        log.warn("Method Not Allowed App:{},Url:{},Reason:{},Method:{}",
                AppEnvConsts.APP_NAME, request.getRequestURL(), HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), e.getMethod());
        return ResultUtils.failure(HttpStatusCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseResult handleHttpMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException e,
                                                                   final HttpServletRequest request,
                                                                   final HttpServletResponse response) {
        log.warn("Unsupported Media App:{},Url:{},Reason:{},Message:{}",
                AppEnvConsts.APP_NAME, request.getRequestURL(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), e.getMessage());
        return ResultUtils.failure(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 500 - Internal Server Error
     */
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult handleIOException(final IOException e,
                                            final HttpServletRequest request,
                                            final HttpServletResponse response) {
        if (StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(e), CommonExceptionAdvice.BROKEN_PIPE)) {
            log.error("IOException App:{},Url:{},Reason:{}",
                    AppEnvConsts.APP_NAME, request.getRequestURL(), CommonExceptionAdvice.BROKEN_PIPE);
        } else {
            log.error("IOException App:{},Url:{},Message:", AppEnvConsts.APP_NAME, request.getRequestURL(), e);
        }
        return ResultUtils.failure(HttpStatusCode.INTERNAL_SERVER_ERROR,
                AppEnvConsts.isProductionMode() ? null : ExceptionUtils.getStackTrace(e)
        );
    }

    /**
     * 500 - Internal Server Error
     */
    @Order
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(final Exception e,
                                          final HttpServletRequest request,
                                          final HttpServletResponse response) {
        log.error("Exception App:{},Url:{},Reason:{}",
                AppEnvConsts.APP_NAME, request.getRequestURL(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        return ResultUtils.failure(HttpStatusCode.INTERNAL_SERVER_ERROR,
                AppEnvConsts.isProductionMode() ? null : ExceptionUtils.getStackTrace(e)
        );
    }
}