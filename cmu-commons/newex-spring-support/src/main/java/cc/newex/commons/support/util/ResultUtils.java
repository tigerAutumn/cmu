package cc.newex.commons.support.util;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.model.ResponseResult;

/**
 * SpringMVC Response Result 工具类
 *
 * @author newex-team
 * @date 2017/12/28
 */
public class ResultUtils {
    /**
     * @return ResponseResult<Object>
     */
    public static ResponseResult success() {
        return success(new Object());
    }

    /**
     * @param data
     * @param <T>
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> success(final T data) {
        return build(0, "success", data);
    }

    /**
     * @param msg
     * @return ResponseResult<Object>
     */
    public static ResponseResult failure(final String msg) {
        return failure(-1, msg);
    }

    /**
     * @param errorCode
     * @return ResponseResult<Object>
     */
    public static ResponseResult failure(final ErrorCode errorCode) {
        return failure(errorCode, null);
    }

    /**
     * @param errorCode
     * @param data
     * @param <T>
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> failure(final ErrorCode errorCode, final T data) {
        return build(errorCode.getCode(), errorCode.getMessage(), data);
    }


    /**
     * @param code
     * @param msg
     * @return ResponseResult<Object>
     */
    public static ResponseResult failure(final int code, final String msg) {
        return build(code, msg, null);
    }


    /**
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> build(final int code, final String msg, final T data) {
        return new ResponseResult<>(code, msg, data);
    }
}
