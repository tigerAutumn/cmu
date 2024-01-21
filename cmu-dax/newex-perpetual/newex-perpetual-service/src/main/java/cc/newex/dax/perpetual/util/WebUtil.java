package cc.newex.dax.perpetual.util;

import cc.newex.dax.perpetual.dto.enums.OrderFromEnum;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

/**
 * 客户端工具类
 */
public class WebUtil {
    public static final Integer MAX_DOWNLOAD_SIZE = 1000;
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 设备ID
     */
    private static final String USER_AGENT = "user-agent";

    /**
     * 获取用户的User-Agent
     *
     * @param request
     * @return
     */
    public static String getUserAgent(final HttpServletRequest request) {
        return request.getHeader(WebUtil.USER_AGENT);
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

    /**
     * 获取设备信息
     *
     * @param request
     * @return
     */
    public static OrderFromEnum getWebFrom(final HttpServletRequest request) {
        final String userAgent = getUserAgent(request);
        if (userAgent.equalsIgnoreCase("android")) {
            return OrderFromEnum.CLIENT_ANDROID_ORDER;
        } else if (userAgent.contains("iphone") || userAgent.contains("ipod") || userAgent.contains("ipad")) {
            return OrderFromEnum.CLIENT_IOS_ORDER;
        } else {
            return OrderFromEnum.WEB_PAGE_ORDER;
        }
    }
}
