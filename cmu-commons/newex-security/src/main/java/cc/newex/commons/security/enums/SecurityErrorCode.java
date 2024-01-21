package cc.newex.commons.security.enums;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.i18n.LocaleUtils;

/**
 * @author newex-team
 * @date 2017/12/29
 */
public enum SecurityErrorCode implements ErrorCode {
    ;

    private final int code;
    private final String msgCode;

    SecurityErrorCode(final int code, final String msgCode) {
        this.code = code;
        this.msgCode = msgCode;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage(this.msgCode);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }
}
