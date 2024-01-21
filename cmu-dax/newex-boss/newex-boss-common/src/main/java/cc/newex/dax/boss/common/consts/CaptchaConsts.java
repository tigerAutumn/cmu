package cc.newex.dax.boss.common.consts;

/**
 * @author newex-team
 * @date 2017-03-18
 */
public class CaptchaConsts {
    /**
     * 验证方式
     */
    //google验证
    public static int CHECK_WAY_GOOGLE = 0;

    //手机验证
    public static int CHECK_WAY_PHONE = 1;

    //邮箱验证
    public static int CHECK_WAY_EMAIL = 2;

    //图片验证
    public static int CHECK_WAY_IMAGE = 3;

    /**
     * 使用状态
     */
    //未使用（有效）
    public static int CHECK_CODE_STATUS_VALID = 0;

    //已使用（无效）
    public static int CHECK_CODE_STATUS_INVALID = 1;

    /**
     * 验证码（业务）类型
     * 默认验证码类型（其他由业务方自定义，如：注册、登录、修改密码等）
     */
    public static int CHECK_CODE_TYPE_DEFAULT = 0;
}
