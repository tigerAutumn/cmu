package cc.newex.commons.support.filter;

import cc.newex.commons.support.consts.AppEnvConsts;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * ServletContext 初始化数据 Filter
 *
 * @author newex-team
 * @date 2017/12/09
 */
public class ContextInitDataFilter implements Filter {
    private String version = AppEnvConsts.VERSION;
    private String appName = AppEnvConsts.ENV_NAME;
    private String random = AppEnvConsts.RANDOM_ITEM;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        this.version = filterConfig.getInitParameter(AppEnvConsts.VERSION_ITEM);
        this.appName = filterConfig.getInitParameter(AppEnvConsts.APP_NAME_ITEM);
        this.random = filterConfig.getInitParameter(AppEnvConsts.RANDOM_ITEM);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        this.setAppEnvAttributes((HttpServletRequest) request);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private void setAppEnvAttributes(final HttpServletRequest request) {
        if (request.getAttribute(AppEnvConsts.CONTEXT_PATH) == null) {
            request.setAttribute(AppEnvConsts.CONTEXT_PATH, request.getContextPath());
        }
        if (request.getAttribute(AppEnvConsts.RANDOM_ITEM) == null) {
            request.setAttribute(AppEnvConsts.RANDOM_ITEM, this.random);
        }
        if (request.getAttribute(AppEnvConsts.VERSION_ITEM) == null) {
            request.setAttribute(AppEnvConsts.VERSION_ITEM, this.version);
            AppEnvConsts.setVersion(this.version);
        }
        if (request.getAttribute(AppEnvConsts.APP_NAME_ITEM) == null) {
            request.setAttribute(AppEnvConsts.APP_NAME_ITEM, this.appName);
        }
    }
}
