package cc.newex.dax.users.common.consts;

import java.util.Arrays;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public class UserConsts {

    /**
     * 正常用户
     **/
    public static final int USER_TYPE_NORMAL = 0;
    /**
     * 内部用户
     **/
    public static final int USER_TYPE_INTERNAL = 1;

    /**
     * 二次验证 GOOGLE
     **/
    public static final int LOGIN_STEP2_GOOGLE = 1;
    /**
     * 二次验证 短信/邮件 验证码验证
     **/
    public static final int LOGIN_STEP2_MSG = 2;

    /**
     * 手机注册
     **/
    public static final String USER_REGISTER_TYPE_MOBILE = "mobile";

    /**
     * 邮箱组册
     **/
    public static final String USER_REGISTER_TYPE_EMAIL = "email";

    /**
     * 邮箱激活状态：未激活
     **/
    public static final int USER_EMAIL_VALIDATE_FLAG_NO = 0;

    /**
     * 邮箱激活状态：已激活
     **/
    public static final int USER_EMAIL_VALIDATE_FLAG_YES = 1;

    /**
     * 限制注册的国家代号列表
     */
    public static final List<Integer> LIMITED_REG_COUNTRIES = Arrays.asList(850, 963, 249, 880, 591, 593, 996);
}
