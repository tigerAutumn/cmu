package cc.newex.commons.ucenter.service;

import cc.newex.commons.ucenter.model.SessionInfo;

/**
 * @author newex-team
 * @date 2018-06-28
 */
public interface SessionService<S extends SessionInfo> {
    /**
     * 设置session 最大过期时间
     *
     * @param seconds 秒
     */
    void setMaxInactiveInterval(long seconds);

    /**
     * 持久化登录会话
     *
     * @param token   登录token
     * @param session {@link SessionInfo}
     */
    void save(String token, S session);

    /**
     * 更新用户登录会话状态
     *
     * @param newSession {@link SessionInfo}
     */
    void updateByUserId(S newSession);

    /**
     * 刷新登录token及关联的userId
     *
     * @param token  登录token
     */
    void refresh(String token);

    /**
     * 根据登录token获取当前登录用户的会话状态
     *
     * @param token 登录token
     * @return {@link SessionInfo}
     */
    S getByToken(String token);

    /**
     * 根据登录token及关联的userI获取当前登录用户的会话状态
     *
     * @param userId 登录token及关联的userId
     * @return {@link SessionInfo}
     */
    S getByUserId(long userId);

    /**
     * 删除当前登录会话
     *
     * @param token 登录token
     */
    void remove(final String token);

    /**
     * 根据用户id删除登录用户(会影响所有端web,android,ios等的登录状)
     *
     * @param userId 用户id
     */
    void removeSessionUser(final long userId);

    /**
     * 根据登录token获取当前登录用户的会话状态Key
     *
     * @param token 登录token
     * @return 登录用户的会话状态Key
     */
    String getSessionIdKey(final String token);

    /**
     * 根据登录token获取当前登录用户的会话状态关联的userId Key
     *
     * @param userId 用户ID
     * @return 关联的userId Key
     */
    String getUserIdKey(final long userId);

    /**
     * 设置与登录会话有关的全局变量
     *
     * @param key    缓存key名称
     * @param status 状态值
     */
    void setGlobalStatus(String key, Integer status);

    /**
     * 获取与登录会话有关的全局变量
     *
     * @param key 缓存key名称
     * @return 状态值
     */
    Integer getGlobalStatus(String key);

    /**
     * 删除登录会话有关的全局变量
     *
     * @param key 缓存key名称
     */
    void deleteGlobalStatus(String key);
}
