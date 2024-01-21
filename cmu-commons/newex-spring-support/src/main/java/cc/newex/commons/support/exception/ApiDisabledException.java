package cc.newex.commons.support.exception;

import cc.newex.commons.support.enums.ErrorCode;
import lombok.Getter;

/**
 * API不可用异常
 *
 * @author newex-team
 * @date 2017/12/09
 */
@Getter
public class ApiDisabledException extends RuntimeException {
    private static final long serialVersionUID = 234122996006267330L;
    private int code;

    public ApiDisabledException() {
        super();
    }

    public ApiDisabledException(final String msg) {
        super(msg);
    }

    public ApiDisabledException(final ErrorCode errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public ApiDisabledException(final ErrorCode errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }
}
