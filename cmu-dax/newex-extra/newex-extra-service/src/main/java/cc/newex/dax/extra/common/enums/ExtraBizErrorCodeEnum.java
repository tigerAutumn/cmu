package cc.newex.dax.extra.common.enums;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.i18n.LocaleUtils;

/**
 * 业务层错误码
 *
 * @author newex-team
 * @date 2018/03/18
 */
public enum ExtraBizErrorCodeEnum implements ErrorCode {
    UPLOAD_FILE_NOT_EXIST(7000),
    // 参数不正确
    INCORRECT_PARAMETER(7001),
    // 用户未登录
    USER_NOT_LOGIN(7002),
    // 文件保存相关
    FILE_SAVE_FAILED(7003),
    // 保存文件类型不支持
    INCORRECT_FILE_TYPE(7004),
    // 文件过大
    INCORRECT_FILE_TO_LARGE(7005),
    // 工单不存在
    WORKORDER_NOT_EXIST(7006),
    // 工单状态不正确
    INCORRECT_WORKORDER_STATUS(7007),
    //文件太小
    INCORRECT_FILE_TO_SMALL(7008),

    //update error
    UPDATE_ERROR(7009),
    // add error
    ADD_ERROR(7010),

    // 币种简称已存在
    TOKEN_SYMBOL_EXIST(7011),
    // 币种全称已存在
    TOKEN_EXIST(7012),

    WRONG_OPERATION(8000),


    //vLink error code
    NO_EMAIL(9000),
    ORDER_CONTRACT_FAIL(9001),
    VENDOR_SERVER_ERROR(9002),
    CONTRACT_CURRENCY_NULL(9003),
    TRANSACTION_FAIL(9004);


    private final int code;

    ExtraBizErrorCodeEnum(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage("error.code.biz." + this.code);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }
}
