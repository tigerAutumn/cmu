package cc.newex.dax.users.common.exception;

import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import lombok.Getter;

/**
 * newex kyc 业务层异常类
 *
 * @author newex-team
 * @date 2018/04/18
 */
@Getter
public class UsersKycException extends RuntimeException {
    private int code;

    public UsersKycException() {
        super();
    }

    public UsersKycException(final BizErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public UsersKycException(final BizErrorCodeEnum errorCodeEnum, final String name) {
        super(LocaleUtils.getMessage("error.code.biz." + name) + " " + errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public UsersKycException(final BizErrorCodeEnum errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }

    public UsersKycException(final String key) {
        super(LocaleUtils.getMessage(key));
    }
}
