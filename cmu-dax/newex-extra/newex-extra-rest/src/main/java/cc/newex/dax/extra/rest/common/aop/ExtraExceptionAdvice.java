package cc.newex.dax.extra.rest.common.aop;

import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.enums.SystemErrorCode;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.exception.ExtraBizException;
import cc.newex.dax.extra.dto.error.FieldErrorMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Order(3)
@RestControllerAdvice
public class ExtraExceptionAdvice {

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExtraBizException.class)
    public ResponseResult handleExtraBizException(final ExtraBizException e) {

        if (e.hasFieldErrors()) {
            final List<FieldErrorMessage> fieldErrorMessageList = e.getFieldErrorMessageList();

            log.error("ExtraBizException {}", JSON.toJSONString(fieldErrorMessageList));

            return ResultUtils.failure(SystemErrorCode.DATA_BIND_VALIDATION_FAILURE, fieldErrorMessageList);
        } else {
            log.error("ExtraBizException {}", e.getMessage());

            return ResultUtils.failure(e.getCode(), e.getMessage());
        }

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

        final BindingResult bindingResult = e.getBindingResult();

        final List<FieldErrorMessage> fieldErrorMessageList = this.getFieldErrors(bindingResult);

        return ResultUtils.failure(SystemErrorCode.DATA_BIND_VALIDATION_FAILURE, fieldErrorMessageList);
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

        final BindingResult bindingResult = e.getBindingResult();

        final List<FieldErrorMessage> fieldErrorMessageList = this.getFieldErrors(bindingResult);

        return ResultUtils.failure(SystemErrorCode.DATA_BIND_VALIDATION_FAILURE, fieldErrorMessageList);
    }

    /**
     * 封装字段校验异常信息
     *
     * @param bindingResult
     * @return
     */
    private List<FieldErrorMessage> getFieldErrors(final BindingResult bindingResult) {
        List<FieldErrorMessage> fieldErrorMessageList = null;

        if (bindingResult.hasFieldErrors()) {
            final List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

            fieldErrorMessageList = fieldErrorList.stream().map(fieldError -> {
                return FieldErrorMessage.builder()
                        .field(fieldError.getField())
                        .defaultMessage(fieldError.getDefaultMessage())
                        .build();
            }).collect(Collectors.toList());

        }

        return fieldErrorMessageList;
    }

}
