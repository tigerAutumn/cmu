package cc.newex.dax.market.common.exception;

import cc.newex.dax.market.common.enums.BizErrorCodeEnum;
import lombok.Getter;

/**
 * 业务层异常类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Getter
public class MarketBizException extends MarketException {
    private int code;

    public MarketBizException() {
        super();
    }

    public MarketBizException(final BizErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public MarketBizException(final BizErrorCodeEnum errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
    }
}
