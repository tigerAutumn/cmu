package cc.newex.dax.perpetual.openapi.support.aop;


import cc.newex.dax.perpetual.common.enums.V1ErrorCodeEnum;

/**
 * Open Api交易业务异常类
 *
 * @author newex-team
 * @date 2018/10/18
 */
public class PerpetualOpenApiException extends RuntimeException {
    private static final long serialVersionUID = -6405122635757503846L;

    private final V1ErrorCodeEnum codeEnum;
    private int code;

    public PerpetualOpenApiException(final V1ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.codeEnum = errorCode;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public V1ErrorCodeEnum getCodeEnum() {
        return this.codeEnum;
    }
}
