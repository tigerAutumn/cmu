package cc.newex.dax.users.service.security;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserLoginEventExample;
import cc.newex.dax.users.domain.UserLoginEvent;

/**
 * 用户登录日志 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface UserLoginEventService extends CrudService<UserLoginEvent, UserLoginEventExample, Long> {

    long addUserLoginEvent(long userId, String ip, String deviceId, String sessionId);

    UserLoginEvent getLastLoginLog(long userId);

    /**
     * 获取用户当天是否登录
     *
     * @param userId
     * @return
     */
    Boolean isLoginWithTodayByUserId(Long userId);
}
