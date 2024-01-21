package cc.newex.dax.portal.common.aop.interceptor;

import cc.newex.dax.portal.common.config.properties.PortalProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 为每次请求增加一些属性
 *
 * @author newex-team
 * @date 2018/03/18
 */
public class AppendAttributeInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private PortalProperties portalProperties;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response, final Object handler) throws Exception {
        this.appendRequestAttributes(request);
        return super.preHandle(request, response, handler);
    }

    private void appendRequestAttributes(final HttpServletRequest request) {
        for (final Map.Entry<String, String> kv :
                this.portalProperties.getUrls().entrySet()) {
            request.setAttribute(kv.getKey(), kv.getValue());
        }
        request.setAttribute("languageTag", LocaleContextHolder.getLocale().toLanguageTag().toLowerCase());
    }
}
