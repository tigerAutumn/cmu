package cc.newex.dax.push.bean.enums;

/**
 * error编码
 *
 * @author xionghui
 * @date 2018/09/12
 */
public enum ErrorCodeEnum {
  RATE_LIMIT(10005, "Rate Limit"), //
  INVALID_LOGIN(10008, "Invalid login token"), //
  INVALID_LOGIN_APIKEY(10009, "api_key or passphrase is invalid"), //
  EXCEPTION(500, "exception"), //
  SESSION_INVILD(10100, "Invalid session"), //
  IP_LIMIT(10102, "Ip used to much sessionId"), //
  SESSION_LIMIT(10104, "SessionId register to much"), //
  ;

  private final int code;
  private final String errorMsg;

  ErrorCodeEnum(final int code, final String msg) {
    this.code = code;
    this.errorMsg = msg;
  }

  public int code() {
    return this.code;
  }

  public String errorMsg() {
    return this.errorMsg;
  }

  @Override
  public String toString() {
    return "{\"error_code\":" + this.code + ",\"error_msg\":\"" + this.errorMsg + "\",\"result\":"
        + false + "}";
  }
}
