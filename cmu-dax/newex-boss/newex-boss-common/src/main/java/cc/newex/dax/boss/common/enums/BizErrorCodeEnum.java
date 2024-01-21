package cc.newex.dax.boss.common.enums;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.i18n.LocaleUtils;
import lombok.Getter;

/**
 * @author allen
 * @date 2018/5/18
 * @des 业务统一处理错误
 */
@Getter
public enum BizErrorCodeEnum implements ErrorCode {

    ADD_SUCCESS(1000, 200),
    ADD_ERROR(1001, 400),
    FILEUPLOAD_ERROR(1002, 400),
    FILEUPLOAD_SUCCESS(1003, 400),
    UPDATE_ERROR(1004, 400),
    UPDATE_SUCCESS(1005, 400),
    NO_IMAGE(1006, 400),
    IMAGE_SUPPORT_TYPE(1007, 400),
    FILE_TOO_SMALL(1008, 400),
    FILE_TOO_LARGE_8M(1009, 400),
    API_ERROR(1010, 400),
    Parsing_SUCCESS(1011, 200),
    Parsing_ERROR(1012, 400),
    ADD_TASK_ERROR(1013, 400),
    Order_ERROR(1014, 400),
    QUERY_DETAILS(1015, 400),
    OLD_PASSWORD_ERROR(10004, 400),
    ILLEGAL_OPERATION(1016, 400),
    REMARKS_FREEZE(1017, 400),
    REMARKS_UNFREEZE(1018, 400),
    REMARKS_CANCEL(1019, 400),
    REMARKS_FINISH(1020, 400),
    LOGIN_IP_LIMITED(1021, 401),
    LOGIN_VERIFICATION_FAILURE(1022, 401),
    USER_AND_PASSWORD_ERROR(1023, 401),
    NULL_LOCK_AMOUNT(1024, 400),
    NOT_SAME_AMOUNT(1025, 400),
    ERROR_DATA_UNLOCK(1026, 400);

    private final int code;
    private final int httpStatus;

    BizErrorCodeEnum(final int code, final int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return LocaleUtils.getMessage("error.code.biz." + this.code);
    }

    public String getMessage(final Object... args) {
        return LocaleUtils.getMessage("error.code.biz." + this.code, args);
    }

    @Override
    public String toString() {
        return "[" + this.getCode() + "]" + this.getMessage();
    }
}
