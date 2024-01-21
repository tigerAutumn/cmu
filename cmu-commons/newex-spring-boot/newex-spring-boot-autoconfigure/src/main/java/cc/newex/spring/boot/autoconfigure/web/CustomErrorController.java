package cc.newex.spring.boot.autoconfigure.web;

import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.enums.HttpStatusCode;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@Slf4j
@RestController
@RequestMapping("/customError")
public class CustomErrorController extends AbstractErrorController {
    private final ErrorProperties errorProperties;

    public CustomErrorController(final ErrorAttributes errorAttributes,
                                 final ErrorProperties errorProperties) {
        super(errorAttributes);
        this.errorProperties = errorProperties;
    }

    @Override
    protected HttpStatus getStatus(final HttpServletRequest request) {
        final Integer statusCode = (Integer) request.getAttribute("spring.security.auth.status_code");
        if (statusCode == null) {
            return super.getStatus(request);
        }
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    protected Map<String, Object> getErrorAttributes(final HttpServletRequest request,
                                                     final boolean includeStackTrace) {
        final Map<String, Object> model = super.getErrorAttributes(request, includeStackTrace);
        final Object path = request.getAttribute("spring.security.auth.request_uri");
        if (path != null) {
            model.put("path", path);
            model.put("status", this.getStatus(request).value());
        }
        return model;
    }

    @RequestMapping(value = "/401")
    public ResponseResult error401(final HttpServletRequest request) {
        return ResultUtils.failure(HttpStatusCode.UNAUTHORIZED, null);
    }

    @RequestMapping(value = "/403")
    public ResponseResult error403(final HttpServletRequest request) {
        return ResultUtils.failure(HttpStatusCode.FORBIDDEN, null);
    }

    @RequestMapping(value = "/404")
    public ResponseResult error404(final HttpServletRequest request) {
        return ResultUtils.failure(HttpStatusCode.NOT_FOUND, null);
    }

    @RequestMapping
    public ResponseResult error(final HttpServletRequest request) {
        final ResponseEntity<Map<String, Object>> responseEntity = this.getResponseEntity(request);
        return ResultUtils.failure(
                HttpStatusCode.valueOf(responseEntity.getStatusCode().value()), null
        );
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    protected ResponseEntity<Map<String, Object>> getResponseEntity(final HttpServletRequest request) {
        final HttpStatus status = this.getStatus(request);
        final Map<String, Object> model = this.getErrorModel(request);
        return new ResponseEntity<>(model, status);
    }

    protected Map<String, Object> getErrorModel(final HttpServletRequest request) {
        final HttpStatus status = this.getStatus(request);
        final Map<String, Object> model = Collections.unmodifiableMap(
                this.getErrorAttributes(request, this.isIncludeStackTrace(request)));
        log.error("app:{},path:{},status:{},trace:{}",
                AppEnvConsts.APP_NAME, model.get("path"), status.value(), model.get("trace"));
        return model;
    }

    protected boolean isIncludeStackTrace(final HttpServletRequest request) {
        final IncludeStacktrace include = this.errorProperties.getIncludeStacktrace();
        if (include == IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == IncludeStacktrace.ON_TRACE_PARAM) {
            return this.getTraceParameter(request);
        }
        return false;
    }
}
