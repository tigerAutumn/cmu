package cc.newex.commons.openapi.support.aop;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.openapi.specs.annotation.OpenApi;
import cc.newex.commons.openapi.specs.annotation.OpenApiAuthValidator;
import cc.newex.commons.openapi.specs.auth.OpenApiAuthManager;
import cc.newex.commons.openapi.specs.config.OpenApiKeyConfig;
import cc.newex.commons.openapi.specs.enums.ContentTypeEnum;
import cc.newex.commons.openapi.specs.enums.HttpHeadersEnum;
import cc.newex.commons.openapi.specs.model.OpenApiKeyInfo;
import cc.newex.commons.openapi.support.auth.DefaultOpenApiAuthToken;
import cc.newex.commons.openapi.support.enums.OpenApiErrorCodeEnum;
import cc.newex.commons.openapi.support.utils.ExceptionUtils;
import cc.newex.commons.support.consts.UserAuthConsts;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * Open Api Auth Validator Aspect.
 * All requests and responses are application/json content type. <br/>
 * Follow typical HTTP response status codes for success and failure.<br/>
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Aspect
@Component
@OpenApi
public class OpenApiAuthValidatorAspect {
    private static final JSONObject NOTHING = new JSONObject();
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String PATCH = "PATCH";
    private static final String DELETE = "DELETE";
    private static final String HEAD = "HEAD";
    private static final String OPTIONS = "OPTIONS";

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private OpenApiAuthManager authManager;
    @Autowired
    protected OpenApiKeyConfig openApiKeyConfig;

    @Pointcut(value = "@annotation(cc.newex.commons.openapi.specs.annotation.OpenApiAuthValidator)")
    private void pointcut() {
    }

    @Around(value = "pointcut() && @annotation(validator)")
    public Object around(final ProceedingJoinPoint point, final OpenApiAuthValidator validator) throws Throwable {
        final String method = this.request.getMethod();
        switch (method) {
            case OpenApiAuthValidatorAspect.PUT:
            case OpenApiAuthValidatorAspect.PATCH:
            case OpenApiAuthValidatorAspect.DELETE:
            case OpenApiAuthValidatorAspect.POST: {
                this.validatorRequest(validator);
                break;
            }
            case OpenApiAuthValidatorAspect.GET: {
                if (validator.validate()) {
                    this.validatorRequest(validator);
                }
                break;
            }
            case OpenApiAuthValidatorAspect.HEAD:
            case OpenApiAuthValidatorAspect.OPTIONS:
            default: {
                return OpenApiAuthValidatorAspect.NOTHING;
            }
        }
        return point.proceed();
    }

    /**
     * Request validation processing.
     */
    private void validatorRequest(final OpenApiAuthValidator validator) throws Exception {
        this.request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        final DefaultOpenApiAuthToken authToken = this.createAuthToken(validator);
        authToken.valid();
        if (!this.authManager.authenticate(authToken)) {
            log.info("Auth error apiKey:{},sign:{},access sign:{}",
                    authToken.getAccessKey(), authToken.getSign(), authToken.getAccessSign());
            ExceptionUtils.getFailure(OpenApiErrorCodeEnum.API_AUTH_ERROR);
        }
    }

    /**
     * Encapsulate auth token object by request
     */
    private DefaultOpenApiAuthToken createAuthToken(final OpenApiAuthValidator validator) throws IOException {
        final OpenApiKeyInfo keyInfo = (OpenApiKeyInfo) this.request.getAttribute(UserAuthConsts.CURRENT_USER_API_KEY_INFO);
        final DefaultOpenApiAuthToken authToken = new DefaultOpenApiAuthToken();
        authToken.setAccessKey(this.formatParams(this.request.getHeader(HttpHeadersEnum.ACCESS_KEY.getName())));
        authToken.setAccessSign(this.formatParams(this.request.getHeader(HttpHeadersEnum.ACCESS_SIGN.getName())));
        authToken.setAccessTimestamp(this.formatParams(this.request.getHeader(HttpHeadersEnum.ACCESS_TIMESTAMP.getName())));
        authToken.setAccessPassphrase(this.formatParams(this.request.getHeader(HttpHeadersEnum.ACCESS_PASSPHRASE.getName())));
        authToken.setContentType(this.formatParams(this.request.getContentType()));
        authToken.setBody(this.getBody());
        authToken.setMethod(this.request.getMethod());
        authToken.setIp(IpUtil.getRealIPAddress(this.request));
        authToken.setRequestPath(this.request.getRequestURI());
        authToken.setQueryString(this.request.getQueryString());
        authToken.setApiMethod(validator.method().name());
        authToken.setAvailableAtFrozen(validator.availableAtFrozen());
        authToken.setPrefix(this.openApiKeyConfig.getPrefix());
        authToken.setExpiredSeconds(this.openApiKeyConfig.getExpiredSeconds());
        authToken.setApiSecret(keyInfo.getSecret());
        return authToken;
    }

    /**
     * get request body
     */
    private String getBody() throws IOException {
        final int contentLength = this.request.getContentLength();
        final String contentType = this.request.getContentType();

        // no request parameters
        if (contentLength < 0 || contentType == null) {
            return null;
        }

        String body = null;

        //All requests and responses are application/json content type. But temporary compatibility form.
        //application/json
        if (contentType.contains(ContentTypeEnum.APPLICATION_JSON.getContentType())) {
            final InputStream is = this.request.getInputStream();
            body = IOUtils.toString(is, StandardCharsets.UTF_8);
        }

        // application/x-www-form-urlencoded   Multilayer untested.
        if (contentType.equalsIgnoreCase(ContentTypeEnum.APPLICATION_X_WWW_FORM_URLENCODED.getContentType())) {
            final Enumeration<String> parameterNames = this.request.getParameterNames();
            final JSONObject object = new JSONObject();
            while (parameterNames.hasMoreElements()) {
                final String parameterName = parameterNames.nextElement();
                object.put(parameterName, this.request.getParameter(parameterName));
            }
            return object.toJSONString();
        }
        return body;
    }

    /**
     * Format Params: trim and replace space to empty
     */
    private String formatParams(String param) {
        if (StringUtils.isEmpty(param)) {
            return null;
        }
        param = param.trim();
        return StringUtils.replace(param, StringUtils.SPACE, StringUtils.EMPTY);
    }
}

