package cc.newex.commons.support.i18n;

import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 多端(web,android,iphone)国际化语言切换拦截器
 *
 * @author newex-team
 * @date 2017/12/09
 **/
public class MultiTerminalLocaleChangeInterceptor extends LocaleChangeInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws ServletException {
        LocaleUtils.setLocale(request, response);
        return true;
    }
}
