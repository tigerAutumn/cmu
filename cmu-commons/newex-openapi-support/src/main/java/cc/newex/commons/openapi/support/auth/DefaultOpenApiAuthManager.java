package cc.newex.commons.openapi.support.auth;

import cc.newex.commons.openapi.specs.auth.OpenApiAuthManager;
import cc.newex.commons.openapi.specs.auth.OpenApiAuthToken;
import cc.newex.commons.openapi.specs.config.OpenApiKeyConfig;
import cc.newex.commons.openapi.specs.exception.OpenApiException;
import cc.newex.commons.openapi.specs.model.OpenApiUserInfo;
import cc.newex.commons.openapi.support.enums.OpenApiErrorCodeEnum;
import cc.newex.commons.support.consts.UserAuthConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * OpenApiAuthManager Default Impl
 *
 * @author newex-team
 * @date 2018-04-28
 */
@Slf4j
public class DefaultOpenApiAuthManager implements OpenApiAuthManager {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected OpenApiKeyConfig openApiKeyConfig;

    @Override
    public boolean authenticate(final OpenApiAuthToken authToken) {
        final OpenApiUserInfo userInfo = (OpenApiUserInfo) this.request.getAttribute(UserAuthConsts.CURRENT_USER);
        if (this.isNotAvailableAtFrozen(authToken, userInfo)) {
            throw new OpenApiException(OpenApiErrorCodeEnum.API_KEY_USER_STATUS_FROZEN);
        }
        return authToken.validateSign(this.openApiKeyConfig.getValidatorAlgorithm());
    }

    /**
     * 如果用户为冻结状态且api为冻结状态不可用返回true(表示不可用),否则true(表示可用）
     *
     * @param authToken {@link OpenApiAuthToken}
     * @param userInfo  {@link OpenApiUserInfo}
     * @return true|false
     */
    protected boolean isNotAvailableAtFrozen(final OpenApiAuthToken authToken, final OpenApiUserInfo userInfo) {
        return (BooleanUtils.toBoolean(ObjectUtils.defaultIfNull(userInfo.getFrozen(), 0)) &&
                BooleanUtils.isFalse(authToken.isAvailableAtFrozen()));
    }
}
