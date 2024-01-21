package cc.newex.dax.users.common.enums.security;

import lombok.Getter;

/**
 * 用户校验类型
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Getter
public enum UserVerifyTypeEnum {
    TYPE_USE_NULL(-1),

    // 仅使用谷歌验证码
    TYPE_USE_GOOGLE_ONLY(0),

    //仅使用短信验证码
    TYPE_USE_SMS_ONLY(1),

    //仅使用邮件验证码
    TYPE_USE_EMAIL_ONLY(2),

    TYPE_USE_GOOGLE_SMS(3),

    TYPE_USE_GOOGLE_EMAIL(4),

    TYPE_USE_SMS_EMAIL(5),

    TYPE_USE_GOOGLE_SMS_EMAIL(6),

    TYPE_USE_LOGIN_PASSWORD_ONLY(7),

    TYPE_USE_TRADE_PASSWORD_ONLY(8);

    private final int type;

    UserVerifyTypeEnum(final int type) {
        this.type = type;
    }

    /**
     * 找回密码校验类型
     *
     * @param loginName
     * @param authTrade
     * @return
     */
    public static UserVerifyTypeEnum getResetPwdVerifyType(final String loginName, final int authTrade) {
        return TYPE_USE_GOOGLE_ONLY;
    }
}
