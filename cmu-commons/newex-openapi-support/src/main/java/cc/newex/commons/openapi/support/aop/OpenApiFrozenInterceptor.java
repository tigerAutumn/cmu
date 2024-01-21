package cc.newex.commons.openapi.support.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author newex-team
 * @date 2017/11/20
 */
@Slf4j
public class OpenApiFrozenInterceptor extends HandlerInterceptorAdapter {
    public OpenApiFrozenInterceptor() {
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response, final Object handler) throws Exception {
        log.debug("request url:{}", request.getRequestURL());
        this.validateFrozen(request);
        return super.preHandle(request, response, handler);
    }

    private void validateFrozen(final HttpServletRequest request) {

    }
}
