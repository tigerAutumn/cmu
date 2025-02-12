package cc.newex.commons.security.jwt.token;

import cc.newex.commons.security.jwt.model.JwtConsts;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.util.ValidateUtils;
import cc.newex.commons.ucenter.model.SessionInfo;
import cc.newex.commons.ucenter.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author newex-team
 * @date 2017/12/19
 */
@Slf4j
@Component
public class JwtTokenUtils {
    private static JwtTokenProvider jwtTokenProvider;
    private static SessionService<SessionInfo> sessionService;

    @Autowired
    public JwtTokenUtils(final JwtTokenProvider jwtTokenProvider, final SessionService<SessionInfo> sessionService) {
        JwtTokenUtils.jwtTokenProvider = jwtTokenProvider;
        JwtTokenUtils.sessionService = sessionService;
    }

    /**
     * 获取与登录会话有关的全局冻结业务变量
     *
     * @param key 缓存key名称
     * @return 状态值
     */
    public static Integer getGlobalFrozenStatus(final String key) {
        return sessionService.getGlobalStatus(key);
    }

    /**
     * 设置与登录会话有关的全局冻结业务变量
     *
     * @param key    缓存key名称
     * @param status 状态值
     */
    public static void setGlobalFrozenStatus(final String key, final Integer status) {
        sessionService.setGlobalStatus(key, status);
    }

    /**
     * 删除登录会话有关的全局冻结业务变量
     *
     * @param key 缓存key名称
     */
    public static void removeGlobalFrozenStatus(final String key) {
        sessionService.deleteGlobalStatus(key);
    }

    /**
     * 从http request中获取当前登录用户对象
     *
     * @param request HttpServletRequest
     * @return @see #JwtUserDetails
     */
    public static JwtUserDetails getCurrentLoginUser(final HttpServletRequest request) {
        final JwtUserDetails jwtUserDetails = (JwtUserDetails) request.getAttribute(JwtConsts.JWT_CURRENT_USER);
        if (jwtUserDetails == null) {
            return JwtUserDetails.builder().status(JwtConsts.STATUS_NOT_EXISTS).build();
        }
        return jwtUserDetails;
    }


    /**
     * 从http request中获取当前登录用户对象
     *
     * @param request HttpServletRequest
     * @return @see #JwtUserDetails
     */
    public static JwtUserDetails getCurrentLoginUserFromToken(final HttpServletRequest request) {
        final String tokenName = jwtTokenProvider.getJwtConfig().getRequestHeaderName();
        final String token = request.getHeader(tokenName);
        if (StringUtils.isBlank(token)) {
            log.warn("jwt token is empty");
            return JwtUserDetails.builder().status(JwtConsts.STATUS_NOT_EXISTS).build();
        }
        final JwtUserDetails jwtUserDetails = jwtTokenProvider.getJwtUserDetails(token);
        if (jwtUserDetails.getStatus().equals(JwtConsts.STATUS_NOT_EXISTS)) {
            log.warn("jwt token is expired");
            return JwtUserDetails.builder().status(JwtConsts.STATUS_NOT_EXISTS).build();
        }

        //二步验证token
        if (jwtUserDetails.getStatus().equals(JwtConsts.STATUS_TWO_FACTOR)) {
            return jwtUserDetails;
        }

        final SessionInfo session = sessionService.getByToken(token);
        if (session == null) {
            log.warn("jwt token is expired");
            return JwtUserDetails.builder().status(JwtConsts.STATUS_NOT_EXISTS).build();
        }
        jwtUserDetails.setUsername(session.getUsername());
        jwtUserDetails.setStatus(session.getStatus());
        jwtUserDetails.setFrozen(session.getFrozen());
        jwtUserDetails.setSpotFrozen(session.getSpotFrozen());
        jwtUserDetails.setC2cFrozen(session.getC2cFrozen());
        jwtUserDetails.setContractsFrozen(session.getContractsFrozen());
        jwtUserDetails.setPerpetualProtocolFlag(session.getPerpetualProtocolFlag());
        return jwtUserDetails;
    }

    /**
     * 清除登录会话记录
     *
     * @param request http request
     */
    public static void clearSession(final HttpServletRequest request) {
        final String tokenName = jwtTokenProvider.getJwtConfig().getRequestHeaderName();
        final String token = request.getHeader(tokenName);
        if (StringUtils.isNotBlank(token)) {
            sessionService.remove(token);
        }
    }

    /**
     * 删除登录会话用户状态信息(会影响所有端web,android,ios等的登录状)
     *
     * @param userId 登录用户id
     */
    public static void removeSessionUser(final long userId) {
        sessionService.removeSessionUser(userId);
    }

    /**
     * 根据用户ID获取当前用户登录会话信息
     *
     * @param userId 用户id
     * @return {@link SessionInfo}
     */
    public static SessionInfo getSession(final long userId) {
        return sessionService.getByUserId(userId);
    }

    /**
     * 生成jwt token
     *
     * @param jwtUserDetails {@link JwtUserDetails}
     * @param request        http request
     * @return access token
     */
    public static String generateToken(final JwtUserDetails jwtUserDetails, final HttpServletRequest request) {
        jwtUserDetails.setIp(ValidateUtils.getRequestIP(request));
        jwtUserDetails.setDevId(ValidateUtils.getDeviceId(request));
        return jwtTokenProvider.generateToken(jwtUserDetails);
    }

    /**
     * 创建登录session记录
     *
     * @param jwtUserDetails {@link JwtUserDetails}
     * @param token          access token
     * @param request        http request
     */
    public static void createSession(final JwtUserDetails jwtUserDetails, final String token, final HttpServletRequest request) {
        final SessionInfo sessionInfo = SessionInfo.builder()
                .userId(jwtUserDetails.getUserId())
                .username(jwtUserDetails.getUsername())
                .status(jwtUserDetails.getStatus())
                .frozen(jwtUserDetails.getFrozen())
                .spotFrozen(jwtUserDetails.getSpotFrozen())
                .c2cFrozen(jwtUserDetails.getC2cFrozen())
                .contractsFrozen(jwtUserDetails.getContractsFrozen())
                .assetFrozen(jwtUserDetails.getAssetFrozen())
                .perpetualProtocolFlag(jwtUserDetails.getPerpetualProtocolFlag())
                .build();
        sessionService.save(token, sessionInfo);
    }

    /**
     * 生成jwt token 并 创建登录session记录
     *
     * @param jwtUserDetails {@link JwtUserDetails}
     * @param request        http request
     * @return access token
     */
    public static String generateTokenAndCreateSession(final JwtUserDetails jwtUserDetails,
                                                       final HttpServletRequest request) {
        final String accessToken = generateToken(jwtUserDetails, request);
        createSession(jwtUserDetails, accessToken, request);
        return accessToken;
    }

    /**
     * 更新用户登录会话状态
     *
     * @param newSession {@link SessionInfo}
     */
    public static void updateSession(final SessionInfo newSession) {
        sessionService.updateByUserId(newSession);
    }
}
