package cc.newex.commons.security.jwt.util;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.security.jwt.model.JwtConsts;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author newex-team
 * @date 2018-07-08
 */
public class ValidateUtils {

    /**
     * @param request
     * @return
     */
    public static Long getRequestIP(final HttpServletRequest request) {
        return IpUtil.toLong(IpUtil.getRealIPAddress(request));
    }

    /**
     * @param request
     * @return
     */
    public static String getDeviceId(final HttpServletRequest request) {
        return StringUtils.defaultIfBlank(request.getHeader(JwtConsts.X_DEV_ID), StringUtils.EMPTY);
    }
}
