package cc.newex.dax.perpetual.rest.interceptor;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.exception.BizException;
import com.netflix.client.ClientException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author newex-team
 * @date 2018/11/29
 */
@Slf4j
@Order(3)
@RestControllerAdvice
public class PerpetualExceptionAdvice {
    /**
     * 400 -
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BizException.class)
    public static ResponseResult handleBizException(final BizException e) {
        log.info("BizException code:{},msg:{}", e.getCode(), e.getMessage());
        return ResultUtils.failure(e.getCode(), e.getMessage());
    }

    /**
     * Ribbon 异常
     */
    @ExceptionHandler(ClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ResponseResult ribbonException(final ClientException e) {
        log.error("Ribbon exception", e);
        return ResultUtils.failure("Ribbon exception");
    }

    /**
     * Feign 异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ResponseResult feignException(final FeignException e) {
        log.error("Feign exception", e);
        return ResultUtils.failure("Feign exception");
    }
}
