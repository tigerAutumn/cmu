package cc.newex.dax.users.common.consts;

/**
 * 用户中redis相关常量与KEY定义
 *
 * @author newex-team
 * @date 2018/03/18
 */
public class RedisConsts {
    //===================Redis Const Start================================================================
    /**
     * 3分钟的秒数
     */
    public static final long DURATION_SECONDS_OF_3_MINTUES = 3 * 60;

    /**
     * 5分钟的秒数
     */
    public static final long DURATION_SECONDS_OF_5_MINTUES = 5 * 60;

    /**
     * 10分钟的秒数
     */
    public static final long DURATION_SECONDS_OF_10_MINTUES = 10 * 60;

    /**
     * 15分钟的秒数
     */
    public static final long DURATION_SECONDS_OF_15_MINTUES = 15 * 60;

    /**
     * 30分钟的秒数
     */
    public static final long DURATION_SECONDS_OF_30_MINTUES = 30 * 60;

    //===================Redis Const END================================================================

    //===================Redis Key Prefix Start================================================================

    /**
     * 谷歌验证码申请前缀
     */
    public static final String GOOGLE_CODE_APPLY_KEY = "users_google_code_apply_";
    /**
     * 用户注册/登录/验证码
     */
    public static final String VERIFICATION_CODE_KEY = "users_verification_code_";

    /**
     * 用户行为配置
     */
    public static final String USER_BEHAVIOR_CONF_KEY = "user_behavior_conf_";
    /**
     * 找回密码
     */
    public static final String USER_RESET_CODE_KEY = "users_reset_code_";
    /**
     * 登录二次验证
     */
    public static final String USER_STEP2_LOGIN_KEY = "users_step2_login_code_";
    /**
     * kyc二级认证
     */
    public static final String USER_KYC_SECOND_KEY = "users_kyc_second_";
    /**
     * foreign kyc二级认证
     */
    public static final String USER_FOREIGN_KYC_SECOND_KEY = "users_foreign_kyc_second_";
    /**
     * kyc国内认证证件缓存key
     */
    public static final String USER_KYC_CHINA_KEY = "users_kyc_china_";
    /**
     * kyc国内认证证件缓存key
     */
    public static final String USER_KYC_FOREIGN_KEY = "users_kyc_foreign_";
    /**
     * 用户法币
     */
    public static final String USER_FIAT_KEY = "users_fiat_";

    //===================Redis Key Prefix END================================================================

}
