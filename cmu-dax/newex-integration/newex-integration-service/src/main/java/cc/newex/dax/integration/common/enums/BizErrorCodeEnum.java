package cc.newex.dax.integration.common.enums;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.i18n.LocaleUtils;

/**
 * 业务错误码
 *
 * @author newex-team
 * @date 2018/03/18
 */
public enum BizErrorCodeEnum implements ErrorCode {
    GET_IP_ADDRESS_FAILURE(1000);

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
