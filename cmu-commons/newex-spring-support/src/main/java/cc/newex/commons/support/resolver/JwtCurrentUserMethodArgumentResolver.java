package cc.newex.commons.support.resolver;

import cc.newex.commons.support.annotation.JwtCurrentUserId;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author newex-team
 * @date 2017/12/09
 */
public class JwtCurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public JwtCurrentUserMethodArgumentResolver() {
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtCurrentUserId.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final JwtCurrentUserId annotation = parameter.getParameterAnnotation(JwtCurrentUserId.class);
        return webRequest.getAttribute(annotation.value(), NativeWebRequest.SCOPE_REQUEST);
    }
}
