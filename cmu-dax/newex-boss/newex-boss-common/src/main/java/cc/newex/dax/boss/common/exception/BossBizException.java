package cc.newex.dax.boss.common.exception;

import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import lombok.Getter;

/**
 * @author allen
 * @date 2018/5/31
 * @des
 */
@Getter
public class BossBizException extends RuntimeException {

    private int code;

    public BossBizException() {
        super();
    }

    public BossBizException(final BizErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public BossBizException(final BizErrorCodeEnum errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }

    public BossBizException(final String key) {
        super(LocaleUtils.getMessage(key));
    }

}
