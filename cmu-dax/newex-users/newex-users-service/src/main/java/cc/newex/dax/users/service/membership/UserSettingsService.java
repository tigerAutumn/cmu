package cc.newex.dax.users.service.membership;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserSettingsExample;
import cc.newex.dax.users.domain.UserSettings;

/**
 * 用户开关设置表 服务接口
 *
 * @author newex-team
 * @date 2018-04-06
 */
public interface UserSettingsService
        extends CrudService<UserSettings, UserSettingsExample, Long> {
    /**
     * @param userId
     * @param status
     * @return boolean
     * @description 开启或者关闭email验证
     * @author newex-team
     * @date 2018/4/10 下午8:47
     */
    boolean enableEmailAuthFlag(long userId, int status);

    /**
     * @param userId
     * @param status
     * @return boolean
     * @description 开启或者关闭google验证
     * @author newex-team
     * @date 2018/4/10 下午8:47
     */
    boolean enableGoogleAuthFlag(long userId, int status);

    /**
     * @param userId
     * @param status
     * @return boolean
     * @description 开启或者关闭交易验证
     * @author newex-team
     * @date 2018/4/10 下午8:47
     */
    boolean enableTradeAuthFlag(long userId, int status);

    /**
     * @param userId
     * @param status
     * @return boolean
     * @description 开启或者关闭交易验证
     * @author newex-team
     * @date 2018/4/10 下午8:47
     */
    boolean enabbeMobileAuthFlag(long userId, int status);

    /**
     * @param userId
     * @param status
     * @return boolean
     * @description 开启或者关闭二次登录验证
     * @author newex-team
     * @date 2018/4/10 下午8:47
     */
    boolean enableLoginAuthFlag(long userId, int status);

    /**
     * 用户法币支付状态设置
     *
     * @param userId
     * @param payment
     * @param status
     * @return
     */
    boolean enablePayAuthFlag(long userId, int payment, int status);

    /**
     * @param userId
     * @description 通过用户id获取用户设置
     * @date 2018/5/14 下午2:16
     */
    UserSettings getByUserId(long userId);

    boolean limitOnePay(UserSettings userSettings, int payType);

    int updatePerpetualProtocolFlag(Long userId, Integer perpetualProtocolFlag);

}
