package cc.newex.dax.users.common.util;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.dax.users.common.consts.UserDetailConsts;
import org.apache.commons.lang3.StringUtils;

/**
 * NewEx-Users项目特定有的util类
 */
public class UsersUtils {


    public static String getStarEmailLoginName(final String loginName) {
        if (StringUtil.isEmail(loginName)) {
            return StringUtil.getStarEmail(loginName);
        }
        return StringUtils.EMPTY;
    }


    public static String getStarMobileLoginName(final String loginName) {
        if (StringUtil.isEmail(loginName)) {
            return StringUtils.EMPTY;
        }
        return StringUtil.getStarMobile(loginName);
    }


    public static String getStarLoginName(final String loginName) {
        if (StringUtil.isEmail(loginName)) {
            return StringUtil.getStarEmail(loginName);
        }
        return StringUtil.getStarMobile(loginName);
    }

    public static boolean toBoolean(final Long value) {
        return !(value == null || value.equals(0L));
    }

    public static String getTradePwdInputName(final int tradePwdInputFlag) {
        if (tradePwdInputFlag == UserDetailConsts.TRADE_PWD_INPUT_ALWAYS) {
            return UserDetailConsts.TRADE_PWD_INPUT_NAME_ALWAYS;
        }
        if (tradePwdInputFlag == UserDetailConsts.TRADE_PWD_INPUT_NEVER) {
            return UserDetailConsts.TRADE_PWD_INPUT_NAME_NEVER;
        }
        return UserDetailConsts.TRADE_PWD_INPUT_NAME_EVERY2_HOURS;

    }
}
