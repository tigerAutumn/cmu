package cc.newex.dax.push.exception;

import cc.newex.dax.push.bean.enums.ErrorCodeEnum;

/**
 * push异常
 *
 * @author xionghui
 * @date 2018/09/21
 */
public class PushRuntimeException extends RuntimeException {
  private static final long serialVersionUID = 2155638014727813293L;

  private final ErrorCodeEnum errorCodeEnum;

  public PushRuntimeException(ErrorCodeEnum errorCodeEnum) {
    super();
    this.errorCodeEnum = errorCodeEnum == null ? ErrorCodeEnum.EXCEPTION : errorCodeEnum;
  }

  public PushRuntimeException(ErrorCodeEnum errorCodeEnum, Throwable cause) {
    super(cause);
    this.errorCodeEnum = errorCodeEnum == null ? ErrorCodeEnum.EXCEPTION : errorCodeEnum;
  }

  public ErrorCodeEnum getErrorCodeEnum() {
    return this.errorCodeEnum;
  }
}
