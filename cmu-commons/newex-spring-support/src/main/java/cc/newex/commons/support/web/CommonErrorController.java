package cc.newex.commons.support.web;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@Slf4j
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CommonErrorController extends AbstractErrorController {
    private final ErrorProperties errorProperties;

    public CommonErrorController(final ErrorAttributes errorAttributes,
                                 final ErrorProperties errorProperties) {
        super(errorAttributes);
        this.errorProperties = errorProperties;
    }

    @ResponseBody
    @RequestMapping(value = "/401")
    public ResponseResult error401(final HttpServletRequest request) {
        return ResultUtils.failure(HttpStatusCode.UNAUTHORIZED, null);
    }

    @RequestMapping(value = "/401", produces = "text/html")
    public ModelAndView error401Html(final HttpServletRequest request,
                                     final HttpServletResponse response) {
        final Map<String, Object> model = this.getViewModel(request, response);
        return new ModelAndView("error/401", model);
    }

    @ResponseBody
    @RequestMapping(value = "/403")
    public ResponseResult error403(final HttpServletRequest request) {
        return ResultUtils.failure(HttpStatusCode.FORBIDDEN, null);
    }

    @RequestMapping(value = "/403", produces = "text/html")
    public ModelAndView error403Html(final HttpServletRequest request,
                                     final HttpServletResponse response) {
        final Map<String, Object> model = this.getViewModel(request, response);
        return new ModelAndView("error/403", model);
    }

    @ResponseBody
    @RequestMapping(value = "/404")
    public ResponseResult error404(final HttpServletRequest request) {
        return ResultUtils.failure(HttpStatusCode.NOT_FOUND, null);
    }

    @RequestMapping(value = "/404", produces = "text/html")
    public ModelAndView error404Html(final HttpServletRequest request,
                                     final HttpServletResponse response) {
        final Map<String, Object> model = this.getViewModel(request, response);
        return new ModelAndView("error/404", model);
    }

    @ResponseBody
    @RequestMapping
    public ResponseResult error(final HttpServletRequest request) {
        final ResponseEntity<Map<String, Object>> responseEntity = this.getResponseEntity(request);
        return ResultUtils.failure(
                HttpStatusCode.valueOf(responseEntity.getStatusCode().value()), null
        );
    }

    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(final HttpServletRequest request,
                                  final HttpServletResponse response) {
        final Map<String, Object> model = this.getViewModel(request, response);
        return new ModelAndView("error/global", model);
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    protected Map<String, Object> getViewModel(final HttpServletRequest request, final HttpServletResponse response) {
        final HttpStatus status = this.getStatus(request);
        response.setStatus(status.value());
        return this.getErrorModel(request);
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
        log.debug("path:{},status:{},trace:{}", model.get("path"), status.value(), model.get("trace"));
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

