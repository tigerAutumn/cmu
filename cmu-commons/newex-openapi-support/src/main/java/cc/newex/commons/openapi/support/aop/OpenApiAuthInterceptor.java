package cc.newex.commons.openapi.support.aop;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.openapi.specs.auth.OpenApiKeyService;
import cc.newex.commons.openapi.specs.config.OpenApiKeyConfig;
import cc.newex.commons.openapi.specs.exception.OpenApiException;
import cc.newex.commons.openapi.specs.model.OpenApiHeadersInfo;
import cc.newex.commons.openapi.specs.model.OpenApiKeyInfo;
import cc.newex.commons.openapi.specs.model.OpenApiUserInfo;
import cc.newex.commons.openapi.specs.util.OpenApiUtil;
import cc.newex.commons.openapi.support.enums.OpenApiErrorCodeEnum;
import cc.newex.commons.support.consts.UserAuthConsts;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * OpenAPI 登录认证拦截器
 * 判断Header中的access-Key与access-Passphrase
 * 是否与用户创建的APIKey一致
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
public class OpenApiAuthInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private OpenApiKeyService apiKeyService;

    @Resource
    private OpenApiKeyConfig openApiKeyConfig;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final OpenApiHeadersInfo headers = this.formatHeaders(this.apiKeyService.getApiHeadersInfo(request));
        final OpenApiKeyInfo apiKeyInfo = this.apiKeyService.getApiKeyInfo(headers.getAccessKey());

        if (apiKeyInfo == null) {
            log.warn("Not Found Api Key:{}", headers.getAccessKey());
            throw new OpenApiException(OpenApiErrorCodeEnum.INVALID_ACCESS_KEY);
        }

        final String requestUri = request.getRequestURI();
        log.info("current visiting uri: {}, OpenApiKeyInfo: {}", requestUri, JSON.toJSONString(apiKeyInfo));

        //如果存在ip白名单则进行验证
        if (StringUtils.isNotBlank(apiKeyInfo.getIpWhiteLists())) {
            final String ip = IpUtil.getRealIPAddress(request);
            final String longIp = String.valueOf(IpUtil.toLong(ip));
            log.debug("AccessKey:[{}],request ip:{}({})", apiKeyInfo.getApiKey(), ip, longIp);
            final String[] ips = Iterables.toArray(Splitter.on(",")
                    .omitEmptyStrings()
                    .trimResults()
                    .split(apiKeyInfo.getIpWhiteLists()), String.class);
            if (!ArrayUtils.contains(ips, longIp)) {
                log.warn("AccessKey:[{}],IP:{}({}),Not In WhiteList:{}", apiKeyInfo.getApiKey(), ip, longIp, apiKeyInfo.getIpWhiteLists());
                throw new OpenApiException(OpenApiErrorCodeEnum.ILLEGAL_IP_REQUEST);
            }
        }

        //
        //如果用户当前request中的Passphrase与当前apiKeyInfo中的一致则通过验证
        //否则throw ACCESS_KEY_OR_PASSPHRASE_INCORRECT
        //
        final String salt = OpenApiUtil.generatePassphraseSalt(headers.getAccessKey(), this.openApiKeyConfig.getPassphraseSaltConfig());
        final String passphrase = OpenApiUtil.encodePassphrase(this.openApiKeyConfig.getPassphraseAlgorithm(), headers.getAccessPassphrase(), salt);
        if (StringUtil.notEquals(passphrase, apiKeyInfo.getPassphrase())) {
            log.warn("AccessKey:[{}] or Passphrase error", apiKeyInfo.getApiKey());
            throw new OpenApiException(OpenApiErrorCodeEnum.ACCESS_KEY_OR_PASSPHRASE_INCORRECT);
        }

        //验证api key用户是否合法
        final OpenApiUserInfo userInfo = this.apiKeyService.getApiUserInfo(apiKeyInfo.getApiKey(), apiKeyInfo.getUserId());
        if (userInfo == null) {
            log.warn("AccessKey:[{}] not found user", apiKeyInfo.getApiKey());
            throw new OpenApiException(OpenApiErrorCodeEnum.INVALID_API_KEY_USER);
        }

        //验证api key用户状态为不可用
        if (BooleanUtils.toBoolean(userInfo.getStatus())) {
            log.warn("AccessKey:[{}] UserId:{} is forbidden", apiKeyInfo.getApiKey(), userInfo.getId());
            throw new OpenApiException(OpenApiErrorCodeEnum.API_KEY_USER_STATUS_FORBIDDEN);
        }

        userInfo.setAuthorities(apiKeyInfo.getAuthorities());
        request.setAttribute(UserAuthConsts.CURRENT_USER, userInfo);
        request.setAttribute(UserAuthConsts.CURRENT_USER_API_KEY_INFO, apiKeyInfo);

        return true;
    }

    private OpenApiHeadersInfo formatHeaders(final OpenApiHeadersInfo headers) {
        headers.setAccessKey(StringUtils.trim(headers.getAccessKey()));
        headers.setAccessPassphrase(StringUtils.trim(headers.getAccessPassphrase()));
        return headers;
    }
}
