package cc.newex.commons.openapi.specs.auth;

import cc.newex.commons.openapi.specs.model.OpenApiHeadersInfo;
import cc.newex.commons.openapi.specs.model.OpenApiKeyInfo;
import cc.newex.commons.openapi.specs.model.OpenApiUserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author newex-team
 * @date 2018-04-28
 */
public interface OpenApiKeyService {

    /**
     * get OpenAPI key  by current http request
     *
     * @param request {@link HttpServletRequest}
     * @return HttpHeadersInfo {@link OpenApiHeadersInfo}
     */
    OpenApiHeadersInfo getApiHeadersInfo(final HttpServletRequest request);

    /**
     * get OpenAPI key info by current http request
     *
     * @param apiKey OpenApi Key from current http request header ACCESS-KEY
     * @return {@link OpenApiKeyInfo}
     */
    OpenApiKeyInfo getApiKeyInfo(final String apiKey);

    /**
     * get OpenAPI User Info by current request's apiKey
     *
     * @param apiKey OpenApi Key
     * @param userId ApiKey owner's userId
     * @return {@link OpenApiUserInfo}
     */
    OpenApiUserInfo getApiUserInfo(final String apiKey, final long userId);
}
