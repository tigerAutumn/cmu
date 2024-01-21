package cc.newex.dax.users.service.security;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserLimitedBehaviorExample;
import cc.newex.dax.users.domain.UserLimitedBehavior;
import cc.newex.dax.users.limited.BehaviorTheme;

/**
 * 用户受限制行为 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface UserLimitedBehaviorService
        extends CrudService<UserLimitedBehavior, UserLimitedBehaviorExample, Long> {

    /**
     * 用户IP限制
     * 没有则添加新纪录，有则更新次数，超时直接重置。
     *
     * @param ipAddress
     * @param type      5:找回登录密码6:验证码
     * @return 错误次数
     */
    int updateUserLimitedBehaviorByIp(long ipAddress, int type, int limitNum, int freezeMin);

    /**
     * 用户名(登录后userID，登录前loginName)限制
     * 没有则添加新纪录，有则更新次数，超时直接重置。
     *
     * @param loginName 用户登录名/userId
     * @param type
     * @return 错误次数
     */
    int updateUserLimitedBehaviorByName(String loginName, int type, int limitNum, int freezeMin);

    int updateUserLimitedBehaviorByName(long userId, int type, int limitNum, int freezeMin);

    /**
     * 设备登录限制
     *
     * @param deviceId
     * @param behaviorTheme
     * @return
     */
    int updateUserLimitedBehaviorByDeviceId(String deviceId, BehaviorTheme behaviorTheme);

    /**
     * 用户ip操作记录
     *
     * @param ipAddress
     * @param type      5:找回登录密码6:验证码
     * @return true:限制 false:不限制
     */
    boolean isUserLimitedBehavior(long ipAddress, int type, int limitNum, int freezeMin);

    /**
     * loginName限制错误次数
     * -------如果此类型达到次数限制，超过两小时则返回0
     * -------返回错误次数
     *
     * @param loginName
     * @param type      0:登录密码1:交易密码2:短信验证码3:google验证码
     * @return true:限制 false:不限制
     */
    boolean isUserLimitedBehavior(String loginName, int type, int limitNum, int freezeMin);

    /**
     * 设备相关错误限制
     *
     * @param deviceId
     * @param behaviorTheme
     * @return
     */
    boolean isUserLimitedBehaviorByDeviceId(String deviceId, BehaviorTheme behaviorTheme);


    /**
     * 删除错误计数
     *
     * @param loginName
     * @param codeType
     */
    boolean resetUserLimitedBehavior(String loginName, int codeType);
}
