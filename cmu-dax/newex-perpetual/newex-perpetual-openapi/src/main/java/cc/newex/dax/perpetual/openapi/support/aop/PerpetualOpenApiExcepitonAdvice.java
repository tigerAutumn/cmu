package cc.newex.dax.perpetual.openapi.support.aop;

import cc.newex.commons.openapi.specs.enums.HttpStatusEnum;
import cc.newex.commons.openapi.specs.exception.OpenApiException;
import cc.newex.commons.openapi.support.advice.OpenApiExcepitonAdvice;
import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.enums.SystemErrorCode;
import cc.newex.dax.perpetual.common.enums.V1ErrorCodeEnum;
import cc.newex.dax.perpetual.common.exception.BizException;
import com.alibaba.fastjson.JSONObject;
import com.netflix.client.ClientException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

/**
 * @author newex-team
 */
@Slf4j
@RestControllerAdvice
public class PerpetualOpenApiExcepitonAdvice extends OpenApiExcepitonAdvice {
    /**
     * Ribbon 异常
     */
    @ExceptionHandler(ClientException.class)
    public JSONObject ribbonException(final ClientException e) {
        log.warn("Ribbon exception", e);
        return this.handleOpenApiException(V1ErrorCodeEnum.REMOTE_INVOKE_ERROR);
    }

    /**
     * Feign 异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(FeignException.class)
    public JSONObject feignException(final FeignException e) {
        log.warn("Feign exception", e);
        return this.handleOpenApiException(V1ErrorCodeEnum.REMOTE_INVOKE_ERROR);
    }

    /**
     * http 参数不匹配异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    public JSONObject missingServletRequestParameterException(final MissingServletRequestParameterException e) {
        log.error("missing servlet request parameter exception", e);
        return this.handleOpenApiException(V1ErrorCodeEnum.MISSING_REQUEST_PARAMETER);
    }

    @ExceptionHandler(PerpetualOpenApiException.class)
    public JSONObject handlePerpetualOpenApiException(final PerpetualOpenApiException e) {
        log.info("OpenApiException code:{},msg:{}", e.getCode(), e.getMessage());
        return this.handleOpenApiException(e.getCodeEnum());
    }

    @ExceptionHandler(BizException.class)
    public JSONObject handleCcexBizException(final HttpServletResponse response,
                                             final BizException e) {
        log.info("OpenApiException code:{},msg:{}", e.getCode(), e.getMessage());
        return this.handleOpenApiException(
                new OpenApiException(e.getCodeEnum().getHttpStatus(), e.getCodeEnum().getCode(), e.getMessage())
        );
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public JSONObject handleValidationException(final ValidationException e,
                                                final HttpServletRequest request,
                                                final HttpServletResponse response) {
        log.warn("Validation Exception App:{},Url:{},Reason:{},Message:{}",
                AppEnvConsts.APP_NAME, request.getRequestURL(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
        return this.handleOpenApiException(
                new OpenApiException(
                        HttpStatusEnum.BAD_REQUEST.getStatus(),
                        SystemErrorCode.DATA_VALIDATION_FAILURE.getCode(),
                        SystemErrorCode.DATA_VALIDATION_FAILURE.getMessage())
        );
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JSONObject handleMethodArgumentNotValidException(final MethodArgumentNotValidException e,
                                                            final HttpServletRequest request,
                                                            final HttpServletResponse response) {
        log.warn("MethodArgumentNotValid App:{},Url:{},Reason:{},Message:{}",
                AppEnvConsts.APP_NAME, request.getRequestURL(),
                HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), e.getMessage());
        return this.handleOpenApiException(
                new OpenApiException(
                        HttpStatusEnum.BAD_REQUEST.getStatus(),
                        SystemErrorCode.DATA_BIND_VALIDATION_FAILURE.getCode(),
                        SystemErrorCode.DATA_BIND_VALIDATION_FAILURE.getMessage())
        );
    }

    private JSONObject handleOpenApiException(final V1ErrorCodeEnum codeEnum) {
        return this.handleOpenApiException(
                new OpenApiException(codeEnum.getHttpStatus(), codeEnum.getCode(), codeEnum.getMessage())
        );
    }
}
