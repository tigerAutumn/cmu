package cc.newex.commons.security.jwt.enums;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.i18n.LocaleUtils;

/**
 * @author newex-team
 * @date 2017/12/29
 */
public enum JwtSecurityErrorCode implements ErrorCode {
    TOKEN_EXPIRATION(401, "error.code.security.800"),
    TOKEN_INVALID(401, "error.code.security.801"),
    TOKEN_FORBIDDEN(403, "error.code.security.803"),
    TOKEN_ABNORMAL_ACCESS(401, "error.code.security.804"),
    TOKEN_BIZ_FROZEN(403, "error.code.security.805"),;

    private final int code;
    private final String msgCode;

    JwtSecurityErrorCode(final int code, final String msgCode) {
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
