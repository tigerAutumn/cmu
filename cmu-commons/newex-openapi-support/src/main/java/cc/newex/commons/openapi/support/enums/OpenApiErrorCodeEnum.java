package cc.newex.commons.openapi.support.enums;

import cc.newex.commons.openapi.specs.enums.ErrorCodeEnum;
import cc.newex.commons.support.i18n.LocaleUtils;
import lombok.Getter;

/**
 * Open Api Error Enum
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Getter
public enum OpenApiErrorCodeEnum implements ErrorCodeEnum {
    ACCESS_KEY_EMPTY(40001, 401),
    //"请求头"ACCESS_KEY"不能为空","ACCESS_KEY header is required"
    ACCESS_SIGN_EMPTY(40002, 400),
    //"请求头"ACCESS_SIGN"不能为空","ACCESS_SIGN header is required"
    ACCESS_TIMESTAMP_EMPTY(40003, 400),
    //"请求头"ACCESS_TIMESTAMP"不能为空","ACCESS_TIMESTAMP header is required"
    ACCESS_PASSPHRASE_EMPTY(40004, 400),
    //"请求头"ACCESS_PASSPHRASE"不能为空","ACCESS_PASSPHRASE header is required"
    INVALID_ACCESS_TIMESTAMP(40005, 400),
    //"无效的ACCESS_TIMESTAMP","Invalid ACCESS_TIMESTAMP"
    INVALID_ACCESS_KEY(40006, 401),
    //"无效的ACCESS_KEY","Invalid ACCESS_KEY"
    INVALID_CONTENT_TYPE(40007, 400),
    //"无效的Content_Type，请使用“application/json”格式","Invalid Content_Type, please use the application/json format"
    ACCESS_TIMESTAMP_EXPIRED(40008, 400),
    //"请求时间戳过期","Request timestamp expired"
    SYSTEM_ERROR(40009, 500),
    //"系统错误","System error"),
    API_AUTH_ERROR(40010, 401),
    //"api 校验失败","API authentication failed"),
    ACCESS_KEY_OR_PASSPHRASE_INCORRECT(40011, 401),
    INVALID_API_KEY_USER(40012, 401),
    API_KEY_USER_STATUS_FORBIDDEN(40013, 403),
    API_KEY_USER_STATUS_FROZEN(40014, 403),
    ILLEGAL_IP_REQUEST(40015, 400),
    ;

    private final int code;
    private final int status;

    OpenApiErrorCodeEnum(final int code, final int status) {
        this.code = code;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage("open.api.error.code.biz." + this.code);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }
}
