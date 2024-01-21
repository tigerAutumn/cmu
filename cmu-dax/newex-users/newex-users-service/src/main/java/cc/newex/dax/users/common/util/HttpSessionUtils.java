package cc.newex.dax.users.common.util;

import cc.newex.commons.security.jwt.model.JwtConsts;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class HttpSessionUtils {

    /**
     * 获取当前登录用户id
     *
     * @param request
     * @return
     */
    public static Long getUserId(final HttpServletRequest request) {
        try {
            final JwtUserDetails user = JwtTokenUtils.getCurrentLoginUser(request);

            if (user == null || user.getStatus() != JwtConsts.STATUS_NORMAL) {
                return -1L;
            }

            return user.getUserId();
        } catch (final Exception e) {
            log.error("get user token error! " + e.getMessage(), e);
        }

        return -1L;
    }

    /**
     *  获取用户令牌状态
     */
    public static int getUserTokenStatus(final HttpServletRequest request) {
        try {
            final JwtUserDetails user = JwtTokenUtils.getCurrentLoginUser(request);
            return user.getStatus();
        } catch (final Exception e) {
            log.error("get user token error!", e);
        }
        return JwtConsts.STATUS_NOT_EXISTS;
    }
}
