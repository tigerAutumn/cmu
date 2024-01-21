package cc.newex.dax.users.common.consts;

public class UserDetailConsts {
    // 0正常
    public static final int NORAML = 0;
    // 1临时删除
    public static final int TEMPORARILY_DELETE = 1;
    // 2永久删除
    public static final int PERMANENTLY_DELETE = 2;
    /**
     * 关闭二次验证
     */
    public static final int DISABLE_LOGIN_AUTH = 0;
    /**
     * 开启二次验证
     */
    public static final int ENABLE_LOGIN_AUTH = 1;
    /**
     * 未绑定
     */
    public static final int DISABLE_AUTH = 0;
    /**
     * 绑定google
     */
    public static final int ENABLE_GOOGLE_AUTH = 1;
    /**
     * 绑定email
     */
    public static final int ENABLE_EMAIL_AUTH = 1;
    /**
     * 绑定短信
     */
    public static final int ENABLE_PHONE_AUTH = 1;


    //==============交易密码输入情况 START=================================

    public static final String TRADE_PWD_INPUT_NAME_ALWAYS = "always";

    public static final String TRADE_PWD_INPUT_NAME_EVERY2_HOURS = "every2hours";

    public static final String TRADE_PWD_INPUT_NAME_NEVER = "never";

    /**
     * 每次输入交易密码
     */
    public static final int TRADE_PWD_INPUT_ALWAYS = 0;
    /**
     * 每次2小时输入交易密码
     */
    public static final int TRADE_PWD_INPUT_EVERY2_HOURS = 1;
    /**
     * 从不输入交易密码
     */
    public static final int TRADE_PWD_INPUT_NEVER = 2;

    //==============交易密码输入情况 END=================================
}
