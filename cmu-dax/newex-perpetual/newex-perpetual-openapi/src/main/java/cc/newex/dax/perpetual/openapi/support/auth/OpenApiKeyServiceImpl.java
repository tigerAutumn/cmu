package cc.newex.dax.perpetual.openapi.support.auth;

import cc.newex.commons.broker.service.BrokerService;
import cc.newex.commons.dictionary.consts.ApiKeyConsts;
import cc.newex.commons.openapi.specs.auth.OpenApiKeyService;
import cc.newex.commons.openapi.specs.model.OpenApiKeyInfo;
import cc.newex.commons.openapi.specs.model.OpenApiUserInfo;
import cc.newex.commons.openapi.support.auth.AbstractRedisOpenApiKeyService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.common.enums.V1ErrorCodeEnum;
import cc.newex.dax.perpetual.openapi.support.aop.PerpetualOpenApiException;
import cc.newex.dax.perpetual.service.cache.CacheService;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.dax.users.dto.apisecret.UserApiSecretResDTO;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author newex-team
 * @date 2018-05-01
 */
@Slf4j
@Service
public class OpenApiKeyServiceImpl
        extends AbstractRedisOpenApiKeyService implements OpenApiKeyService {

    @Autowired
    private UsersClient usersClient;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private BrokerService brokerService;

    @Override
    public OpenApiUserInfo getApiUserInfo(final String apiKey, final long userId) {
        if (StringUtils.isBlank(apiKey)) {
            return null;
        }

        OpenApiUserInfo userInfo = this.getApiUserInfoFromCache(apiKey);
        if (userInfo == null) {
            userInfo = this.getApiUserInfoFromClient(apiKey, userId);
        }

        try {
            if (!userInfo.getBrokerId().equals(this.brokerService.getBrokerId())) {
                OpenApiKeyServiceImpl.log.error("api key brokerId : {}, host brokerId : {}", userInfo.getBrokerId(), this.brokerService.getBrokerId());

                throw new PerpetualOpenApiException(V1ErrorCodeEnum.Secretkey_not_exist);
            }
        } catch (final ExecutionException e) {
            throw new RuntimeException(e);
        }

        return userInfo;
    }

    @Override
    protected OpenApiKeyInfo getApiKeyInfoFromClient(final String apiKey) {
        final String cacheKey = StringUtils.join(ApiKeyConsts.OPEN_API_KEY_PREFIX, apiKey);

        final ResponseResult<UserApiSecretResDTO> result = this.usersClient.getUserApiSecret(apiKey);

        if (result == null || result.getCode() != 0) {
            log.error("getApiKeyInfoFromClient apiKey does not exist ,key={} ", apiKey);
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Secretkey_not_exist);
        }

        final UserApiSecretResDTO userApiSecretResDTO = result.getData();

        final OpenApiKeyInfo openApiKeyInfo = OpenApiKeyInfo.builder()
                .label(userApiSecretResDTO.getLabel())
                .userId(userApiSecretResDTO.getUserId())
                .apiKey(userApiSecretResDTO.getApiKey())
                .secret(userApiSecretResDTO.getSecret())
                .passphrase(userApiSecretResDTO.getPassphrase())
                .authorities(userApiSecretResDTO.getAuthorities())
                .expiredTime(userApiSecretResDTO.getExpiredTime())
                .rateLimit(userApiSecretResDTO.getRateLimit())
                .ipWhiteLists(userApiSecretResDTO.getIpWhiteLists())
                .brokerId(userApiSecretResDTO.getBrokerId())
                .build();

        this.cacheService.setCacheValueExpireTime(cacheKey, JSON.toJSONString(openApiKeyInfo), 10, TimeUnit.MINUTES);

        return openApiKeyInfo;
    }

    @Override
    protected OpenApiUserInfo getApiUserInfoFromClient(final String apiKey, final long userId) {
        final String cacheKey = StringUtils.join(ApiKeyConsts.OPEN_API_KEY_USER_ID_PREFIX, apiKey);

        final ResponseResult<UserInfoResDTO> userInfoResult = this.usersClient.getUserDetailInfo(userId, null);

        if (userInfoResult == null || userInfoResult.getCode() != 0 || userInfoResult.getData() == null) {
            log.error("getApiUserInfoFromClient  apiKey does not exist ,key={} ", apiKey);
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Secretkey_not_exist);
        }

        final ResponseResult<UserApiSecretResDTO> clientUserApiSecret = this.usersClient.getUserApiSecret(apiKey);
        if (clientUserApiSecret == null || clientUserApiSecret.getCode() != 0) {
            log.error("getApiUserInfoFromClient  apiKey2 does not exist ,key={} ", apiKey);
            throw new PerpetualOpenApiException(V1ErrorCodeEnum.Secretkey_not_exist);
        }

        final OpenApiUserInfo openApiUserInfo = OpenApiUserInfo.builder()
                .id(userInfoResult.getData().getId())
                .status(userInfoResult.getData().getStatus())
                .frozen(userInfoResult.getData().getFrozen())
                .spotFrozenFlag(userInfoResult.getData().getSpotFrozenFlag())
                .c2cFrozenFlag(userInfoResult.getData().getC2cFrozenFlag())
                .contractsFrozen(userInfoResult.getData().getContractsFrozenFlag())
                .assetFrozen(userInfoResult.getData().getAssetFrozenFlag())
                .authorities(clientUserApiSecret.getData().getAuthorities())
                .brokerId(clientUserApiSecret.getData().getBrokerId())
                .perpetualProtocolFlag(userInfoResult.getData().getPerpetualProtocolFlag())
                .build();

        this.cacheService.setCacheValueExpireTime(cacheKey, JSON.toJSONString(openApiUserInfo), 10, TimeUnit.MINUTES);

        return openApiUserInfo;
    }
}
