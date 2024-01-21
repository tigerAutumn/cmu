package cc.newex.dax.asset.rest.common.aop;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.exception.AssetBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
public class CustomExceptionAdvice {
    /**
     * 200 -
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AssetBizException.class)
    public ResponseResult handleNewExBizException(final AssetBizException e) {
        log.error("NewExBizException {}", e.getMessage());
        return ResultUtils.failure(e.getCode(), e.getMessage());
    }
}
