package cc.newex.dax.users.common.util;

import cc.newex.commons.security.jwt.model.JwtConsts;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

public class WebUtils {

    /**
     * 设备ID
     */
    private static final String USER_AGENT = "user-agent";

    /**
     * 获取登录用户的设备ID
     *
     * @param request
     * @return
     */
    public static String getDeviceId(final HttpServletRequest request) {
        return request.getHeader(JwtConsts.X_DEV_ID);
    }

    /**
     * 获取用户的User-Agent
     *
     * @param request
     * @return
     */
    public static String getUserAgent(final HttpServletRequest request) {
        return request.getHeader(WebUtils.USER_AGENT);
    }

    /**
     * 获取主机名
     *
     * @param request
     * @return
     */
    public static String getHost(final HttpServletRequest request) {
        final String urlStr = request.getRequestURL().toString();
        final java.net.URL url;
        try {
            url = new java.net.URL(urlStr);
        } catch (final MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return url.getHost();
    }
}
