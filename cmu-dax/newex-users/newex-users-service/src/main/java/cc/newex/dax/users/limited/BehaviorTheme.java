package cc.newex.dax.users.limited;

import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.users.domain.behavior.enums.BehaviorCategoryEnum;
import org.apache.commons.lang3.StringUtils;

public enum BehaviorTheme {
    USERS1_REGISTER_EMAIL_1(BehaviorCategoryEnum.USERS, 10, 1, MessageTemplateConsts.MAIL_USERS_REGISTER_CODE, null),
    USERS1_REGISTER_PHONE_2(BehaviorCategoryEnum.USERS, 10, 2, null, MessageTemplateConsts.SMS_USERS_MOBILE_REGISTER_CODE),
    USERS1_REGISTER_ACCUMULATION_3(BehaviorCategoryEnum.USERS, 10, 3, null, null),
    USERS1_REGISTER_CHECKNAME_4(BehaviorCategoryEnum.USERS, 10, 4, null, null),

    USERS2_LOGIN_1(BehaviorCategoryEnum.USERS, 20, 1, null, null),
    USERS2_LOGIN_STEP2AUTH_2(BehaviorCategoryEnum.USERS, 20, 2, MessageTemplateConsts.MAIL_USERS_LOGIN_STEP2AUTH_CODE, MessageTemplateConsts.SMS_USERS_LOGIN_STEP2AUTH_CODE),
    USERS2_LOGIN_MODIFY_PASSWORD_3(BehaviorCategoryEnum.USERS, 20, 3, null, MessageTemplateConsts.SMS_USERS_LOGIN_PWD_MODIFY_CODE),
    USERS2_LOGIN_RESET_PASSWORD_4(BehaviorCategoryEnum.USERS, 20, 4, MessageTemplateConsts.MAIL_USERS_LOGIN_PWD_RESET_CODE, MessageTemplateConsts.SMS_USERS_LOGIN_PWD_RESET_CODE),
    USERS2_LOGIN_STEP2AUTH_SWITCH(BehaviorCategoryEnum.USERS, 20, 5, null, null),

    USERS3_PHONE_BIND_1(BehaviorCategoryEnum.USERS, 30, 1, null, MessageTemplateConsts.SMS_USERS_MOBILE_BIND_CODE),
    USERS3_PHONE_MODIFY_2(BehaviorCategoryEnum.USERS, 30, 2, null, MessageTemplateConsts.SMS_USERS_MOBILE_MODIFY_CODE),
    USERS3_PHONE_TRADE_SWITCH_3(BehaviorCategoryEnum.USERS, 30, 3, null, MessageTemplateConsts.SMS_USERS_MOBILE_VERIFY_OPEN_CODE),
    USERS3_PHONE_TRADE_SWITCH_4(BehaviorCategoryEnum.USERS, 30, 4, null, MessageTemplateConsts.SMS_USERS_MOBILE_VERIFY_CLOSE_CODE),

    USERS4_EMAIL_BIND_1(BehaviorCategoryEnum.USERS, 40, 1, MessageTemplateConsts.MAIL_USERS_ACTIVE_CODE, null),
    USERS4_EMAIL_ACTIVE_2(BehaviorCategoryEnum.USERS, 40, 2, null, null),
    USERS4_EMAIL_UPDATE_3(BehaviorCategoryEnum.USERS, 40, 3, MessageTemplateConsts.MAIL_USERS_MODIFY_CODE, null),
    USERS4_EMAIL_SETVERIFY_4(BehaviorCategoryEnum.USERS, 40, 4, null, null),

    USERS5_GOOGLE_BIND_1(BehaviorCategoryEnum.USERS, 50, 1, MessageTemplateConsts.MAIL_USERS_GOOGLE_BIND_CODE, MessageTemplateConsts.SMS_USERS_GOOGLE_BIND_CODE),
    USERS5_GOOGLE_RESET_2(BehaviorCategoryEnum.USERS, 50, 2, MessageTemplateConsts.MAIL_USERS_GOOGLE_RESET_CODE, MessageTemplateConsts.SMS_USERS_GOOGLE_RESET_CODE),
    USERS5_GOOGLE_TRADE_SWITCH_3(BehaviorCategoryEnum.USERS, 50, 3, null, null),
    USERS5_GOOGLE_LOGIN_SWITCH_4(BehaviorCategoryEnum.USERS, 50, 4, MessageTemplateConsts.MAIL_USERS_GOOGLE_VERIFY_OPEN_CODE, MessageTemplateConsts.SMS_USERS_GOOGLE_VERIFY_OPEN_CODE),
    USERS5_GOOGLE_LOGIN_SWITCH_5(BehaviorCategoryEnum.USERS, 50, 5, MessageTemplateConsts.MAIL_USERS_GOOGLE_VERIFY_CLOSE_CODE, MessageTemplateConsts.SMS_USERS_GOOGLE_VERIFY_CLOSE_CODE),

    USERS6_MONEY_PASSWORD_CREATE_1(BehaviorCategoryEnum.USERS, 60, 1, null, null),
    USERS6_MONEY_RESET_PASSWORD_2(BehaviorCategoryEnum.USERS, 60, 2, null, null),
    USERS6_MONEY_PASSWORD_UPDATE_3(BehaviorCategoryEnum.USERS, 60, 3, null, null),
    USERS6_MONEY_PASSWORD_FREQUENCY_4(BehaviorCategoryEnum.USERS, 60, 4, null, null),

    USERS7_API_CREATE_1(BehaviorCategoryEnum.USERS, 70, 1, MessageTemplateConsts.MAIL_USERS_API_CREATE_CODE, MessageTemplateConsts.SMS_USERS_API_CREATE_CODE),
    USERS7_API_DETAIL_2(BehaviorCategoryEnum.USERS, 70, 2, MessageTemplateConsts.MAIL_USERS_API_VIEW_CODE, MessageTemplateConsts.SMS_USERS_API_VIEW_CODE),
    USERS7_API_UPDATE_3(BehaviorCategoryEnum.USERS, 70, 3, MessageTemplateConsts.MAIL_USERS_API_RESET_CODE, MessageTemplateConsts.SMS_USERS_API_RESET_CODE),
    USERS7_API_DELETE_4(BehaviorCategoryEnum.USERS, 70, 4, MessageTemplateConsts.MAIL_USERS_API_DELETE_CODE, MessageTemplateConsts.SMS_USERS_API_DELETE_CODE),

    USERS8_COMMON_CAPTCHA_1(BehaviorCategoryEnum.USERS, 80, 1, null, null),

    //提现类型
    USERS8_COMMON_WITHDRAW(BehaviorCategoryEnum.USERS, 90, 1, MessageTemplateConsts.MAIL_ASSET_WITHDRAW_CODE, MessageTemplateConsts.SMS_ASSET_WITHDRAW_CODE),

    // 点卡转让
    USERS8_POINTCARD_TRANSFER(BehaviorCategoryEnum.USERS, 90, 2, MessageTemplateConsts.MAIL_CARDPOINT_TRANSFER_CODE, MessageTemplateConsts.SMS_CARDPOINT_TRANSFER_CODE),

    // 兑换验证码
    USERS8_ASSET_EXCHANGE_CODE(BehaviorCategoryEnum.USERS, 90, 3, MessageTemplateConsts.MAIL_ASSET_EXCHANGE_CODE, MessageTemplateConsts.SMS_ASSET_EXCHANGE_CODE),

    // 兑换通知
    USERS8_ASSET_EXCHANGE_SUCCESS(BehaviorCategoryEnum.USERS, 90, 4, MessageTemplateConsts.MAIL_ASSET_EXCHANGE_SUCCESS, MessageTemplateConsts.SMS_ASSET_EXCHANGE_SUCCESS),

    // 发红包验证码
    USERS8_ASSET_REDPACKET_CODE(BehaviorCategoryEnum.USERS, 90, 5, MessageTemplateConsts.MAIL_ASSET_REDPACKET_CODE, MessageTemplateConsts.SMS_ASSET_REDPACKET_CODE),

    USERS9_FIAT_BANK_SETTING(BehaviorCategoryEnum.USERS, 100, 1, MessageTemplateConsts.MAIL_USERS_FIAT_BANK_SETTING_CODE, MessageTemplateConsts.SMS_USERS_FIAT_BANK_SETTING_CODE),//法币交易-设置银行卡
    USERS9_FIAT_ALIPAY_SETTING(BehaviorCategoryEnum.USERS, 100, 2, MessageTemplateConsts.MAIL_USERS_FIAT_ALIPAY_SETTING_CODE, MessageTemplateConsts.SMS_USERS_FIAT_ALIPAY_SETTING_CODE),//法币交易-设置alipay
    USERS9_FIAT_WEPAY_SETTING(BehaviorCategoryEnum.USERS, 100, 3, MessageTemplateConsts.MAIL_USERS_FIAT_WEPAY_SETTING_CODE, MessageTemplateConsts.SMS_USERS_FIAT_WEPAY_SETTING_CODE),//法币交易-设置微信支付

    USERS10_KYC_AUDIT_FAIL(BehaviorCategoryEnum.USERS, 110, 1, MessageTemplateConsts.MAIL_USERS_KYC2_AUDIT_FAIL_SUCCESS, MessageTemplateConsts.SMS_USERS_KYC2_AUDIT_FAIL_SUCCESS),//KYC审核失败

    ;

    /**
     * 业务类型
     */
    private final BehaviorCategoryEnum business;
    /**
     * 主题
     */
    private final int theme;
    /**
     * 行为
     */
    private final int type;
    /**
     * 邮箱发送码
     */
    private String emailCode;
    /**
     * 短信发送吗
     */
    private String mobileCode;

    BehaviorTheme(final BehaviorCategoryEnum business, final int theme, final int type,
                  final String emailCode, final String mobileCode) {
        this.business = business;
        this.theme = theme;
        this.type = type;
        this.emailCode = emailCode;
        this.mobileCode = mobileCode;
    }

    /**
     * @param behavior
     * @description 获取行为枚举
     */
    public static BehaviorTheme getBehavior(final int behavior) {
        if (behavior <= 0) {
            return null;
        }
        for (final BehaviorTheme be : BehaviorTheme.values()) {
            if (be.getBehavior() == behavior) {
                if (StringUtils.isEmpty(be.getMobileCode())) {
                    be.mobileCode = MessageTemplateConsts.SMS_USERS_MOBILE_BIND_CODE;
                }
                if (StringUtils.isEmpty(be.getEmailCode())) {
                    be.emailCode = MessageTemplateConsts.MAIL_USERS_REGISTER_CODE;
                }
                return be;
            }
        }
        return null;
    }

    /**
     * 获取行为编号
     *
     * @return
     */
    public int getBehavior() {
        return this.business.getId() + this.theme + this.type;
    }

    public String getEmailCode() {
        return this.emailCode;
    }

    public String getMobileCode() {
        return this.mobileCode;
    }

}
