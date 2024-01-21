package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/5/30 17:03
 * @Description:
 */
public enum EmailEnum {

    PLAN_EMAIL(1),
    FUTURE_PLAN_EMAIL(2),
    LOGIN_LOCKED_EMAIL(3),
    QUESTION_CLOSED_EMAIL(4),
    SECURITY_SETTINGS_EMAIL(5),
    GOLD_USER_DEADLINE(6),
    ACCOUNT_REGISTER_ACTIVATION(7),
    ACCOUNT_SUB_REGISTER_ACTIVATION(8),
    ACCOUNT_BIND_EMAIL(9),
    ACCOUNT_REGISTER_ACTIVATION_CN(10),
    ACCOUNT_SUB_REGISTER_ACTIVATION_CN(11),
    ACCOUNT_BIND_EMAIL_CN(12),
    ACCOUNT_RESET_PASSWORD(13),
    ACCOUNT_RESET_PASSWORD_CN(14),
    // 提现确认 国际站
    RECHARGE_CONFIRM(15),
    // 提现确认 国内站
    RECHARGE_CONFIRM_CN(16),
    // 提现地址认证确认 国际站
    RECHARGE_CNY_CONFIRM(17),
    // 提现地址认证确认 国内站
    RECHARGE_CNY_CONFIRM_CN(18),
    QUESTION_TRACE(19),
    QUESTION_TRACE_ADMIN(20),
    QUESTION_TRACE_USER(21),
    QUESTION_REPLY_USER(22),
    ID_DOCUMENT_AUDIT_1(23),
    ID_DOCUMENT_AUDIT_2(24),
    ID_DOCUMENT_AUDIT_3(25),
    ID_DOCUMENT_AUDIT_4(26),
    IP_LOCATION_NOTIFY(27),
    LOGIN_LOCATION_NOTIFY(28),
    // 充值到账
    RECHARGE_CNY_DEPOSITS(29),
    // 充币到账
    RECHARGE_COIN_DEPOSITS(30),
    ENTRUST_BE_TRIGGER(31),
    RECHARGE_USD_BE_INTERCEPTED(32),
    // 企业用户 联系我们 提醒邮件
    ENTERPRISE_CONTACT(33),
    // 新用户充值砸金蛋
    RECHARGE_EGG(34),
    USERDOCUMENT_DUE(36),
    USERDOCUMENT_OVERDUE(37);

    

    private int code;

    EmailEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static EmailEnum valueOf(int code) {
        for (EmailEnum emailEnum : values()) {
            if (emailEnum.getCode() == code) {
                return emailEnum;
            }
        }
        return null;
    }
}
