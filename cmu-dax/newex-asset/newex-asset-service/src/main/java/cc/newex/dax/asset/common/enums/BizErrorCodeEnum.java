package cc.newex.dax.asset.common.enums;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.i18n.LocaleUtils;

/**
 * 业务错误码
 *
 * @author newex-team
 * @date 2018/03/18
 */
public enum BizErrorCodeEnum implements ErrorCode {
    /**
     * 提现拒绝
     */
    WITHDRAW_CONTROL_REFUSE(1013, 400),
    /**
     * 未授权
     */
    UNAUTHORIZED(1014, 400),
    /**
     * 验证码错误
     */
    CHECK_VERIFICATION_ERROR(1015, 400),
    /**
     * 体现金额不足
     */
    WITHDRAW_AMOUNT_NOT_ENOUGH(1016, 400),
    /**
     * 地址错误
     */
    INVALID_ADDRESS(1017, 400),
    /**
     * 地址已存在
     */
    ADDRESS_HAS_EXISTED(1018, 400),
    /**
     * 业务类型错误
     */
    INVALID_BIZ(1019, 400),
    /**
     * 404
     */
    NOT_FOUND(1020, 400),
    /**
     * 需要验证身份证
     */
    NEED_VALIDATE_IDCARD(4036, 400),
    /**
     * 验证码验证失败
     */
    CODE_VERIFY_FAIL(1033, 400),
    /**
     * 邮箱验证码验证失败
     */
    EMAIL_CODE_VERIFY_ERROR(3333, 400),
    /**
     * 短信验证码验证失败
     */
    SMS_CODE_VERIFY_ERROR(3323, 400),
    /**
     * 未知错误
     */
    UNKNOWN_ERROR(1021, 400),
    /**
     * 用户信息错误
     */
    USER_MARK_ERROR(1022, 400),
    /**
     * 用户锁仓失败
     */
    USER_LOCK_POSITION_ERROR(1023, 400),
    /**
     * 手动解锁失败
     */
    HAND_UNLOCK_AMOUNT_ERROR(1024, 400),
    /**
     * 批量解锁未完成
     */
    BATCH_UNLOCK_NO_FINISH(1025, 400),
    /**
     * 提现数量请输入正整数
     */
    POSITIVE_INTEGER_REQUIRED(1026, 400),
    /**
     * 谷歌验证码错误
     */
    GOOGLE_CODE_VERIFY_ERROR(3314, 400),
    /**
     * 邮箱未绑定
     */
    EMAIL_NOT_BIND_ERROR(4030, 400),
    /**
     * 身份证号验证失败
     */
    CARD_NUMBER_VERIFY_ERROR(4035, 400),
    /**
     * 币种错误
     */
    INVALID_CURRENCY(4037, 400),
    /**
     * 支付保证金失败
     */
    PAY_TOKEN_FAILURE(4038, 400),
    /**
     * 保证金只支持CT
     */
    ONLY_SUPPORT_CT(4039, 400),
    /**
     * 未查询到项目信息
     */
    PROJECT_QUERY_ERROR(4040, 400),
    /**
     * 项目已经付过费
     */
    PROJECT_HAS_PAY_TOKEN(4041, 400),
    /**
     * 提现关闭
     */
    WITHDRAW_CLOSE(4043, 400),
    /**
     * 转账关闭
     */
    TRANSFER_CLOSE(4042, 400),
    /**
     * 业务线不支持
     */
    BIZ_NOT_SUPPORT(4044, 400),
    INVALID_TRADE_NO(4045, 400),
    /**
     * 充值关闭
     */
    DEPOSIT_CLOSE(4046, 400),

    LOGON_FAILURE_YOU_STILL_HAVE_CHANCES(4047, 400),

    LOGON_FAILURE_EXCEED_MAX_TIMES(4048, 400),
    UNKNOWN_DOMAIN(4049, 400),
    SNAPING_CANT_EDIT_TRANSFER(4050, 400),
    TRANSFER_FAIL(4051, 400),
    LUCKYWIN_WITHDRAW_ACOUNT_AMOUNT_NOT_ENOUGH(4052, 400),
    RECORD_NOT_FOUND(4053, 400),
    RECORD_TYPE_ERROR(4054, 400),
    //审核状态设置错误
    WITHDRAW_AUDIT_STATUS_FAIL(4055, 400),
    ;

    private final int code;
    private final int httpStatus;

    BizErrorCodeEnum(final int code, final int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public static BizErrorCodeEnum parseByCode(final int code) {
        final BizErrorCodeEnum[] var1 = values();
        final int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            final BizErrorCodeEnum bizErrorCodeEnum = var1[var3];
            if (bizErrorCodeEnum.getCode() == code) {
                return bizErrorCodeEnum;
            }
        }

        throw new RuntimeException(String.valueOf(code));
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage("error.code.biz." + this.code);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }

    public String getMessage(final Object[] args) {
        return LocaleUtils.getMessage("error.code.biz." + this.code, args);
    }
}
