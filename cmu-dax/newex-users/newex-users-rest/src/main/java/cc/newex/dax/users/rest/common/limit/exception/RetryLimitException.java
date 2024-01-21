package cc.newex.dax.users.rest.common.limit.exception;

import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import lombok.Getter;

/**
 * @author newex-team
 * @date 2018-08-10
 */
@Getter
public class RetryLimitException extends RuntimeException {
    private int code;

    public RetryLimitException() {
        super();
    }

    public RetryLimitException(final BizErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public RetryLimitException(final BizErrorCodeEnum errorCodeEnum, final String name) {
        super(LocaleUtils.getMessage("error.code.biz." + name) + " " + errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public RetryLimitException(final BizErrorCodeEnum errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }

    public RetryLimitException(final String key) {
        super(LocaleUtils.getMessage(key));
    }
}
