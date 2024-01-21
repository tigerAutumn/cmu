package cc.newex.dax.users.rest.common.limit.enums;

public enum RetryLimitTypeEnum {
    /**
     * 验证码限次
     */
    VERIFY_CODE,
    /**
     * 登录限次
     */
    LOGIN,
    /**
     * 忘记密码
     */
    FORGET_PASSWORD,
    /**
     * 注册
     */
    REGISTER,
    /**
     * 登录二次验证
     */
    VERIFY_LOGIN,
    /**
     * 谷歌验证码绑定
     */
    GOOGLE_CODE_BIND,
    /**
     * 谷歌验证码解绑
     */
    GOOGLE_CODE_UNBIND,
    /**
     * 邮箱绑定
     */
    EMAIL_BIND,
    /**
     * 手机号绑定
     */
    MOBILE_BIND,
    /**
     * 手机号更新
     */
    MOBILE_UPDATE,
    /**
     * 开启或关闭手机验证
     */
    ENABLE_VALIDATE,
    /**
     * api key
     */
    API_SECRETS,
    /**
     * 法币设置
     */
    FIAT,
    /**
     * 安全
     */
    SECURITY_VERIFY_CODE,
}