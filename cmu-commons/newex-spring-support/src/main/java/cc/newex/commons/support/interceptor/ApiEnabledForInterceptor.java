package cc.newex.commons.support.interceptor;

import cc.newex.commons.support.annotation.ApiEnabledFor;
import cc.newex.commons.support.enums.SystemErrorCode;
import cc.newex.commons.support.exception.ApiDisabledException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Api是否启用拦截器
 *
 * @author newex-team
 * @date 2017/12/09
 */
public class ApiEnabledForInterceptor extends HandlerInterceptorAdapter {
    private static final String DEFAULT_DOMAIN = "test";
    private final String currentDomain;

    public ApiEnabledForInterceptor(final String domain) {
        this.currentDomain = domain;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            final Method method = ((HandlerMethod) handler).getMethod();
            final ApiEnabledFor apiEnabledFor = method.getAnnotation(ApiEnabledFor.class);
            if (apiEnabledFor == null ||
                    StringUtils.equalsIgnoreCase(this.currentDomain, DEFAULT_DOMAIN)) {
                return true;
            }
            final String value = apiEnabledFor.value();
            if (!StringUtils.equalsIgnoreCase(value, this.currentDomain)) {
                throw new ApiDisabledException(SystemErrorCode.API_DISABLED);
            }
        }
        return true;
    }
}
