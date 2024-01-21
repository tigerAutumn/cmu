package cc.newex.commons.support.enums;

import cc.newex.commons.support.i18n.LocaleUtils;

import java.util.Arrays;

/**
 * @author newex-team
 * @date 2017/12/09
 **/
public enum HttpStatusCode implements ErrorCode {
    BAD_REQUEST(400, "error.code.httpstatus.400"),
    UNAUTHORIZED(401, "error.code.httpstatus.401"),
    FORBIDDEN(403, "error.code.httpstatus.403"),
    NOT_FOUND(404, "error.code.httpstatus.404"),
    METHOD_NOT_ALLOWED(405, "error.code.httpstatus.405"),
    UNSUPPORTED_MEDIA_TYPE(415, "error.code.httpstatus.415"),
    INTERNAL_SERVER_ERROR(500, "error.code.httpstatus.500");

    private final int code;
    private final String message;

    HttpStatusCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @param code
     * @return
     */
    public static HttpStatusCode valueOf(final int code) {
        return Arrays.stream(values())
                .filter(x -> x.getCode() == code)
                .findFirst()
                .orElse(HttpStatusCode.INTERNAL_SERVER_ERROR);
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
