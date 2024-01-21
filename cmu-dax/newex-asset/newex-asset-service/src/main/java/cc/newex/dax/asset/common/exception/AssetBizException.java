package cc.newex.dax.asset.common.exception;

import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import lombok.Getter;

/**
 * NewEx Asset 业务层异常类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Getter
public class AssetBizException extends RuntimeException {
    private int code;

    public AssetBizException() {
        super();
    }

    public AssetBizException(final BizErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public AssetBizException(final BizErrorCodeEnum errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }

    public AssetBizException(final String key) {
        super(LocaleUtils.getMessage(key));
    }
}
