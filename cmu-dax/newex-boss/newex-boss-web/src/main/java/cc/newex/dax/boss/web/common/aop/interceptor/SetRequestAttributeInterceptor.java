package cc.newex.dax.boss.web.common.aop.interceptor;

import cc.newex.commons.support.consts.AppEnvConsts;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 为每次请求增加一些属性
 *
 * @author newex-team
 * @date 2017-03-18
 */
public class SetRequestAttributeInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response, final Object handler) throws Exception {
        this.appendRequestAttributes(request);
        return super.preHandle(request, response, handler);
    }

    private void appendRequestAttributes(final HttpServletRequest request) {
        request.setAttribute(AppEnvConsts.CONTEXT_PATH, request.getContextPath());
        request.setAttribute(AppEnvConsts.ENV_NAME_ITEM, AppEnvConsts.ENV_NAME);
        request.setAttribute(AppEnvConsts.RANDOM_ITEM, AppEnvConsts.RANDOM);
        request.setAttribute(AppEnvConsts.DOMAIN_ITEM, AppEnvConsts.DOMAIN);
        request.setAttribute("lang", LocaleContextHolder.getLocale().toLanguageTag().toLowerCase());
    }
}
