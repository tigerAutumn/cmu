package cc.newex.commons.openapi.support.utils;

import cc.newex.commons.openapi.specs.exception.OpenApiException;
import cc.newex.commons.openapi.support.enums.OpenApiErrorCodeEnum;

/**
 * @author newex-team
 * @date 2018-06-17
 */
public class ExceptionUtils {
    /**
     * General error message exception.
     */
    public static void getFailure(final OpenApiErrorCodeEnum errorCodeEnum) {
        throw new OpenApiException(errorCodeEnum.getStatus(), errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }

}
