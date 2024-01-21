package cc.newex.dax.market.common.enums;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.i18n.LocaleUtils;

/**
 * 业务错误码
 *
 * @author newex-team
 * @date 2018/03/18
 */
public enum BizErrorCodeEnum implements ErrorCode {
    TRANSACTION_SUCCESS(0),
    SYSTEM_ERROR(1),

    //从2000开始
    ERROR_CODE_ENUM(2000),

    // 市场行情从5000开始
    TICKET_PARAM_EMPTY(5000),
    TICKET_AUTH_FAIL(5001),
    TICKET_PARAM_PARSE_FAIL(5002),
    TICKET_PARAM_VALIDATE_FAIL(5003),
    TICKET_DATA_NOT_FOUND(5004),
    NOT_SUPPORT_CLIENT(5009),
    SYMBOL_ERROR(5010),
    IP_ERROR(5012),
    PORTFOLIO_NOT_EXIST(5021),;

    private final int code;

    BizErrorCodeEnum(final int code) {
        this.code = code;
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
}
