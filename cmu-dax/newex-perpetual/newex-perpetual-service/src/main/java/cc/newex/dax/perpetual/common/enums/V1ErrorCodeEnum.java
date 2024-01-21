package cc.newex.dax.perpetual.common.enums;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.i18n.LocaleUtils;

/**
 * @author newex-team
 * @date 2018/03/18
 */

public enum V1ErrorCodeEnum implements ErrorCode {

    /**
     * error.code.v1.10000=必选参数不能为空
     * error.code.v1.10001=用户请求过于频繁
     * error.code.v1.10002=系统错误
     * error.code.v1.10003=未在请求限制列表中,稍后请重试
     * error.code.v1.10004=IP限制不能请求该资源
     * error.code.v1.10005=密钥不存在
     * error.code.v1.10006=用户不存在
     * error.code.v1.10007=签名不匹配
     * error.code.v1.10008=非法参数
     * error.code.v1.10009=订单不存在
     * error.code.v1.10010=余额不足
     * error.code.v1.10011=重复撤单
     * error.code.v1.10012=当前密钥不支持提现
     * error.code.v1.10013=此接口只支持https请求
     * error.code.v1.10014=API鉴权失败
     * error.code.v1.10015=未绑定手机或谷歌验证
     * error.code.v1.10016=交易密码未设置
     * error.code.v1.10017=交易密码错误
     * error.code.v1.10060=ApiKey数量限制
     * error.code.v1.10061=ApiKey名称已存在
     * error.code.v1.10101=订单类型错误
     * error.code.v1.10102=不是本用户的订单
     * error.code.v1.10103=私密订单密钥错误
     * error.code.v1.10047=当前为子账户，此功能未开放
     * error.code.v1.10018=当前密钥不支持交易
     * error.code.v1.10019=当前余额不足
     */
    Parm_null(10000, 400),
    Request_too_often(10001, 429),
    Request_too_cancel(10011, 400),
    System_error(10002, 500),
    Try_again_later(10003, 400),
    Ip_restrained(10004, 400),
    Secretkey_not_exist(10005, 400),
    Partner_not_exist(10006, 400),
    Signature_not_match(10007, 400),
    Illegal_parameter(10008, 400),
    Order_not_exist(10009, 404),
    Insufficient_funds(10010, 400),
    Only_support_https_request(10013, 400),
    Api_authorization_error(10014, 400),
    Not_enabled_authenticator(10015, 400),
    Trade_password_not_set(10016, 400),
    Trade_password_error(10017, 400),
    User_to_freeze(10100, 400),
    Api_key_num_limit(10060, 400),
    Api_key_display_name_exist(10061, 400),
    OTC_Order_Type_Error(10101, 400),
    OTC_Order_UserFrom_Error(10102, 400),
    OTC_Order_Key_Error(10103, 400),
    Cannot_Access_ForAccount(10047, 400),
    API_KEY_NOT_PREMISSION_WITHDRAW(10012, 400),
    API_KEY_NOT_PREMISSION_TRADE(10018, 400),
    NO_ENOUGH_BALANCE(10019, 400),
    REMOTE_INVOKE_ERROR(10048, 500),
    MISSING_REQUEST_PARAMETER(10049, 500),
    RATE_LIMIT_IP_NOT_EXIST(10050, 400),;
    private final int code;
    private final int httpStatus;

    V1ErrorCodeEnum(int code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage("error.code.v1." + this.code);
    }

    public String getMessage(Object... args) {
        return LocaleUtils.getMessage("error.code.v1." + this.code, args);
    }

    public int getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String toString() {
        return "error_code=" + this.code + ",error_desc=" + this.getMessage() + ", http_status=" + this.httpStatus;
    }

}
