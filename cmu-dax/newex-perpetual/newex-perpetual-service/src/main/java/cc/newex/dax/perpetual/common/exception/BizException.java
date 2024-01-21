package cc.newex.dax.perpetual.common.exception;

import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author harry
 * @Date: 2018/6/5 09:54
 * @Description:
 */
@Getter
@Slf4j
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 234122996006267680L;
    private int code;

    private BizErrorCodeEnum codeEnum;

    public BizException() {
        super();
    }

    public BizException(final String msg) {
        super(msg);
    }

    public BizException(final BizErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
        this.codeEnum = errorCodeEnum;
    }

    public BizException(final BizErrorCodeEnum errorCodeEnum, final Object... args) {
        super(errorCodeEnum.getMessage(args));
        this.code = errorCodeEnum.getCode();
        this.codeEnum = errorCodeEnum;
    }

    public BizException(final BizErrorCodeEnum errorCodeEnum, final Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
        this.code = errorCodeEnum.getCode();
        this.codeEnum = errorCodeEnum;
    }

    public BizErrorCodeEnum getCodeEnum() {
        return this.codeEnum;
    }

}