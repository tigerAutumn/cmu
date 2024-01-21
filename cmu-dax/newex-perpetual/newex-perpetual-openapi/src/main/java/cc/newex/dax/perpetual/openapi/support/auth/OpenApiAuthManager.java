package cc.newex.dax.perpetual.openapi.support.auth;

import cc.newex.commons.openapi.specs.auth.OpenApiAuthToken;
import cc.newex.commons.openapi.specs.model.OpenApiUserInfo;
import cc.newex.commons.openapi.support.auth.DefaultOpenApiAuthManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;

/**
 * OpenApiAuthManager
 *
 * @author newex-team
 * @date 2018-04-28
 */
@Slf4j
public class OpenApiAuthManager
        extends DefaultOpenApiAuthManager implements cc.newex.commons.openapi.specs.auth.OpenApiAuthManager {

    /**
     * 如果用户为冻结状态且api为冻结状态不可用返回true(表示不可用),否则true(表示可用）
     *
     * @param authToken {@link OpenApiAuthToken}
     * @param userInfo  {@link OpenApiUserInfo}
     * @return true|false
     */
    @Override
    protected boolean isNotAvailableAtFrozen(final OpenApiAuthToken authToken, final OpenApiUserInfo userInfo) {
        return ((BooleanUtils.toBoolean(ObjectUtils.defaultIfNull(userInfo.getFrozen(), 0)) ||
                BooleanUtils.toBoolean(ObjectUtils.defaultIfNull(userInfo.getSpotFrozenFlag(), 0))) &&
                BooleanUtils.isFalse(authToken.isAvailableAtFrozen()));
    }
}
