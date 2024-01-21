package cc.newex.commons.openapi.specs.exception;

import cc.newex.commons.openapi.specs.enums.ErrorCodeEnum;
import lombok.Getter;

/**
 * Open Api user-defined Exception.
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Getter
public class OpenApiException extends RuntimeException {

    /**
     * Http Status Codes
     */
    private final int status;
    /**
     * Biz Error Codes
     */
    private int code;

    public OpenApiException(final ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum.getStatus(), errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }

    public OpenApiException(final int status, final String message) {
        super(message);
        this.status = status;
    }

    public OpenApiException(final int status, final int code, final String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

}
