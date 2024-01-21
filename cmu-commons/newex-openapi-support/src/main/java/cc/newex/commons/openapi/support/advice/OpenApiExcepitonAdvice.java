package cc.newex.commons.openapi.support.advice;

import cc.newex.commons.openapi.specs.exception.OpenApiException;
import cc.newex.commons.openapi.support.enums.OpenApiErrorCodeEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * Global capture exception. <br/>
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
public class OpenApiExcepitonAdvice {
    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final String MESSAGE = "message";
    @Autowired
    protected HttpServletResponse response;

    @ExceptionHandler(OpenApiException.class)
    public JSONObject handleOpenApiException(final OpenApiException e) {
        log.error("OpenApiException: status:{},code:{},msg:{}", e.getStatus(), e.getCode(), e.getMessage());
        return this.errorHelper(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public JSONObject handleException(final Exception e) {
        log.error("Open API Common Exception:", e);
        return this.errorHelper(
                OpenApiErrorCodeEnum.SYSTEM_ERROR.getStatus(),
                OpenApiErrorCodeEnum.SYSTEM_ERROR.getMessage()
        );
    }

    private JSONObject errorHelper(final int status, final String message) {
        switch (status) {
            case UNAUTHORIZED: {
                this.response.setStatus(UNAUTHORIZED);
                break;
            }
            case FORBIDDEN: {
                this.response.setStatus(FORBIDDEN);
                break;
            }
            case NOT_FOUND: {
                this.response.setStatus(NOT_FOUND);
                break;
            }
            case INTERNAL_SERVER_ERROR: {
                this.response.setStatus(INTERNAL_SERVER_ERROR);
                break;
            }
            case BAD_REQUEST:
            default: {
                this.response.setStatus(BAD_REQUEST);
                break;
            }
        }
        return this.getMessage(message);
    }

    private JSONObject getMessage(final String message) {
        final JSONObject json = new JSONObject();
        json.put(MESSAGE, message);
        return json;
    }
}
