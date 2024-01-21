package cc.newex.dax.integration.common.exception;

import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.dax.integration.common.enums.BizErrorCodeEnum;
import lombok.Getter;

/**
 * NewEx Integration 业务层异常类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Getter
public class IntegrationBizException extends RuntimeException {
    private int code;

    public IntegrationBizException() {
        super();
    }

    public IntegrationBizException(final BizErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public IntegrationBizException(final BizErrorCodeEnum errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }

    public IntegrationBizException(final String key) {
        super(LocaleUtils.getMessage(key));
    }
}
