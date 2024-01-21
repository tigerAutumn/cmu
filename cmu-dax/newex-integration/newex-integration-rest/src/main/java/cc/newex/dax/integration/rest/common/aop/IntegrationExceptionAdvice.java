package cc.newex.dax.integration.rest.common.aop;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.common.exception.IntegrationBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Order(3)
@RestControllerAdvice
public class IntegrationExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IntegrationBizException.class)
    public ResponseResult handleIntegrationBizException(final IntegrationBizException e) {
        log.error("IntegrationBizException {}", e.getMessage());
        return ResultUtils.failure(e.getCode(), e.getMessage());
    }
}
