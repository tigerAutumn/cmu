package cc.newex.spring.boot.autoconfigure.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author newex-team
 * @date 2018-05-04
 */
public class HeaderRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(final RequestTemplate template) {
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        final HttpServletRequest request = attributes.getRequest();
        final Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                final String name = headerNames.nextElement();
                final String values = request.getHeader(name);
                template.header(name, values);
            }
        }
    }
}
