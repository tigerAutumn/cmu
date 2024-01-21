package cc.newex.commons.support.enums;

import cc.newex.commons.support.i18n.LocaleUtils;

import java.util.Arrays;

/**
 * @author newex-team
 * @date 2017/12/09
 **/
public enum SystemErrorCode implements ErrorCode {
    SUCCESS(0, "error.code.sys.success"),
    SYSTEM_ERROR(1, "error.code.sys.error"),
    UNKNOWN_ERROR(-1, "error.code.sys.unknow"),
    API_DISABLED(-2, "error.code.sys.apidisabled"),
    FORBIDDEN(403,"error.code.sys.forbidden"),
    NOT_FOUND(404,"error.code.sys.notfound"),
    HTTP_MESSAGE_NOT_READABLE(900, "error.code.sys.900"),
    DATA_VALIDATION_FAILURE(901, "error.code.sys.901"),
    DATA_BIND_VALIDATION_FAILURE(902, "error.code.sys.902"),
    SQL_EXECUTE_FAILURE(903, "error.code.sys.903"),
    METHOD_ARGUMENT_NOT_VALID(904, "error.code.sys.902"),;

    private final int code;
    private final String message;

    SystemErrorCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @param code
     * @return
     */
    public static SystemErrorCode valueOf(final int code) {
        return Arrays.stream(values())
                .filter(x -> x.getCode() == code)
                .findFirst()
                .orElse(SystemErrorCode.UNKNOWN_ERROR);
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage(this.message);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }
}
