package cc.newex.dax.users.service.behavior;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserBehaviorExample;
import cc.newex.dax.users.domain.UserBehavior;
import cc.newex.dax.users.domain.behavior.model.UserBehaviorResult;

/**
 * 行为操作配置表 服务接口
 *
 * @author newex-team
 * @date 2018-04-13
 */
public interface UserBehaviorService
        extends CrudService<UserBehavior, UserBehaviorExample, Integer> {

    /**
     * 加载数据到缓存
     */
    void loadUserBehaviorToCache();

    /**
     * 从缓存中获取行为配置
     *
     * @param name {@link cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum}
     * @return {@link UserBehavior}
     */
    UserBehavior getUserBehaviorFromCache(final String name);

    /**
     * 获取登录态的用户行为审查项
     *
     * @param name   {@link cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum}
     * @param userId 用户id
     * @return {@link UserBehaviorResult}
     */
    UserBehaviorResult getUserCheckRuleBehavior(final String name, final long userId);

    /**
     * 获取登录态的用户行为审查项
     *
     * @param conf   {@link UserBehavior}
     * @param userId 用户id
     * @return {@link UserBehaviorResult}
     */
    UserBehaviorResult getUserCheckRuleBehavior(final UserBehavior conf, final long userId);
}