package cc.newex.dax.boss.web.common.aop;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.exception.BossBizException;
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
 * @date 2017-03-18
 */
@Slf4j
@Order(3)
@RestControllerAdvice
public class BossExceptionAdvice {
    /**
     * 200 -
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BossBizException.class)
    public ResponseResult handleBossBizException(final BossBizException e) {
        log.error("BossBizException: " + e.getMessage(), e);
        return ResultUtils.failure(e.getCode(), e.getMessage());
    }

    /**
     * Ribbon 异常
     */
    @ExceptionHandler(ClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult ribbonException(final ClientException e) {
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
    public ResponseResult feignException(final FeignException e) {
        log.error("Feign exception", e);
        return ResultUtils.failure("Feign exception");
    }

}
