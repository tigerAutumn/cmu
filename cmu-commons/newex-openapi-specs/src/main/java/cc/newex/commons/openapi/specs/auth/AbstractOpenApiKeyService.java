package cc.newex.commons.openapi.specs.auth;

import cc.newex.commons.openapi.specs.enums.HttpHeadersEnum;
import cc.newex.commons.openapi.specs.model.OpenApiHeadersInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author newex-team
 * @date 2018-04-28
 */
public abstract class AbstractOpenApiKeyService implements OpenApiKeyService {

    @Override
    public OpenApiHeadersInfo getApiHeadersInfo(final HttpServletRequest request) {
        final OpenApiHeadersInfo headers = new OpenApiHeadersInfo();
        headers.setAccessKey(request.getHeader(HttpHeadersEnum.ACCESS_KEY.getName()));
        headers.setAccessSign(request.getHeader(HttpHeadersEnum.ACCESS_SIGN.getName()));
        headers.setAccessTimestamp(request.getHeader(HttpHeadersEnum.ACCESS_TIMESTAMP.getName()));
        headers.setAccessPassphrase(request.getHeader(HttpHeadersEnum.ACCESS_PASSPHRASE.getName()));
        return headers;
    }
}
