package cc.newex.dax.users.service.cache.impl;

import cc.newex.commons.dictionary.consts.ApiKeyConsts;
import cc.newex.commons.openapi.specs.model.IpRateLimitInfo;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.ucenter.model.SessionInfo;
import cc.newex.dax.users.common.consts.RedisConsts;
import cc.newex.dax.users.domain.UserBehavior;
import cc.newex.dax.users.domain.UserIpRateLimit;
import cc.newex.dax.users.dto.kyc.KycChinaCacheDTO;
import cc.newex.dax.users.dto.kyc.KycForeignCacheDTO;
import cc.newex.dax.users.dto.membership.UserFiatResDTO;
import cc.newex.dax.users.service.cache.AppCacheService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 应用所有操作redis缓存调用都集中封装到该类
 *
 * @author newex-team
 * @date 2018-06-18
 */
@Slf4j
@Service
public class RedisAppCacheServiceImpl implements AppCacheService {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Override
    public void setUserBehaviorConf(final String name, final UserBehavior object) {
        this.redisTemplate.opsForValue().set(RedisConsts.USER_BEHAVIOR_CONF_KEY + name, JSON.toJSONString(object));
    }

    @Override
    public UserBehavior getUserBehaviorConf(final String name) {
        return JSON.parseObject(
                this.redisTemplate.opsForValue().get(RedisConsts.USER_BEHAVIOR_CONF_KEY + name),
                UserBehavior.class
        );
    }

    @Override
    public void setImageVerificationCode(final String serialNO, final String code) {
        final String key = RedisConsts.VERIFICATION_CODE_KEY + serialNO;
        this.redisTemplate.opsForValue().set(key, code, RedisConsts.DURATION_SECONDS_OF_3_MINTUES, TimeUnit.SECONDS);
    }

    @Override
    public String getImageVerificationCode(final String serialNO) {
        return this.redisTemplate.opsForValue().get(RedisConsts.VERIFICATION_CODE_KEY + serialNO);
    }

    @Override
    public void deleteImageVerificationCode(final String serialNO) {
        this.redisTemplate.delete(RedisConsts.VERIFICATION_CODE_KEY + serialNO);
    }

    @Override
    public void setResetPwdLoginName(final String serialNO, final String loginName) {
        this.redisTemplate.opsForValue().set(RedisConsts.USER_RESET_CODE_KEY + serialNO,
                loginName, RedisConsts.DURATION_SECONDS_OF_3_MINTUES, TimeUnit.SECONDS);
    }

    @Override
    public String getResetPwdLoginName(final String serialNO) {
        return this.redisTemplate.opsForValue().get(RedisConsts.USER_RESET_CODE_KEY + serialNO);
    }

    @Override
    public void deleteResetPwdLoginName(final String serialNO) {
        this.redisTemplate.delete(RedisConsts.USER_RESET_CODE_KEY + serialNO);
    }

    @Override
    public void setTwoFactorLoginUserId(final String key, final long userId) {
        this.redisTemplate.opsForValue().set(RedisConsts.USER_STEP2_LOGIN_KEY + key,
                String.valueOf(userId), RedisConsts.DURATION_SECONDS_OF_5_MINTUES, TimeUnit.SECONDS);
    }

    @Override
    public String getTwoFactorLoginUserId(final String key) {
        return this.redisTemplate.opsForValue().get(RedisConsts.USER_STEP2_LOGIN_KEY + key);
    }

    @Override
    public void setChinaUserSecondKyc(final long userId, final String token) {
        this.redisTemplate.opsForValue().set(RedisConsts.USER_KYC_SECOND_KEY + userId, token,
                RedisConsts.DURATION_SECONDS_OF_15_MINTUES, TimeUnit.SECONDS);
    }

    @Override
    public String getChinaUserSecondKyc(final long userId) {
        return this.redisTemplate.opsForValue().get(RedisConsts.USER_KYC_SECOND_KEY + userId);
    }

    @Override
    public void deleteChinaUserSecondKyc(final long userId) {
        this.redisTemplate.delete(RedisConsts.USER_KYC_SECOND_KEY + userId);
    }

    @Override
    public void setForeignUserSecondKyc(final long userId, final String token) {
        this.redisTemplate.opsForValue().set(RedisConsts.USER_FOREIGN_KYC_SECOND_KEY + userId, token,
                RedisConsts.DURATION_SECONDS_OF_15_MINTUES, TimeUnit.SECONDS);
    }

    @Override
    public String getForeignUserSecondKyc(final long userId) {
        return this.redisTemplate.opsForValue().get(RedisConsts.USER_FOREIGN_KYC_SECOND_KEY + userId);
    }

    @Override
    public void setChinaUserKycInfo(final long userId, final KycChinaCacheDTO dto) {
        this.redisTemplate.opsForValue().set(RedisConsts.USER_KYC_CHINA_KEY + userId, JSON.toJSONString(dto),
                RedisConsts.DURATION_SECONDS_OF_15_MINTUES, TimeUnit.SECONDS);
    }

    @Override
    public KycChinaCacheDTO getChinaUserKycInfo(final long userId) {
        final String kycInfo = this.redisTemplate.opsForValue().get(RedisConsts.USER_KYC_CHINA_KEY + userId);
        if (StringUtils.isNotEmpty(kycInfo)) {
            return JSON.toJavaObject(JSON.parseObject(kycInfo), KycChinaCacheDTO.class);
        }
        return null;
    }

    @Override
    public void deleteChinaUserKycInfo(final long userId) {
        this.redisTemplate.delete(RedisConsts.USER_KYC_CHINA_KEY + userId);
    }

    @Override
    public void setForeignUserKycInfo(final long userId, final KycForeignCacheDTO dto) {
        this.redisTemplate.opsForValue().set(RedisConsts.USER_KYC_FOREIGN_KEY + userId, JSON.toJSONString(dto),
                RedisConsts.DURATION_SECONDS_OF_15_MINTUES, TimeUnit.SECONDS);
    }

    @Override
    public KycForeignCacheDTO getForeignUserKycInfo(final long userId) {
        final String kycInfo = this.redisTemplate.opsForValue().get(RedisConsts.USER_KYC_FOREIGN_KEY + userId);
        if (StringUtils.isNotEmpty(kycInfo)) {
            return JSON.toJavaObject(JSON.parseObject(kycInfo), KycForeignCacheDTO.class);
        }
        return KycForeignCacheDTO.builder().build();
    }

    @Override
    public void deleteForeignUserKycInfo(final long userId) {
        this.redisTemplate.delete(RedisConsts.USER_KYC_FOREIGN_KEY + userId);
    }

    @Override
    public void setUserFiatInfo(final long userId, final UserFiatResDTO dto) {
        this.redisTemplate.opsForValue().set(RedisConsts.USER_FIAT_KEY + userId, JSON.toJSONString(dto),
                RedisConsts.DURATION_SECONDS_OF_3_MINTUES, TimeUnit.SECONDS);
    }

    @Override
    public UserFiatResDTO getUserFiatInfo(final long userId) {
        final String json = this.redisTemplate.opsForValue().get(RedisConsts.USER_FIAT_KEY + userId);
        if (StringUtils.isNotEmpty(json)) {
            return JSON.toJavaObject(JSON.parseObject(json), UserFiatResDTO.class);
        }
        return null;
    }

    @Override
    public void deleteUserFiatInfo(final long userId) {
        this.redisTemplate.delete(RedisConsts.USER_FIAT_KEY + userId);
    }

    @Override
    public void setGoogleSecret(final long userId, final String secret) {
        this.redisTemplate.opsForValue().set(RedisConsts.GOOGLE_CODE_APPLY_KEY + userId, secret,
                RedisConsts.DURATION_SECONDS_OF_10_MINTUES, TimeUnit.SECONDS);
    }

    @Override
    public String getGoogleSecret(final long userId) {
        return this.redisTemplate.opsForValue().get(RedisConsts.GOOGLE_CODE_APPLY_KEY + userId);
    }

    @Override
    public void deleteGoogleSecret(final long userId) {
        this.redisTemplate.delete(RedisConsts.GOOGLE_CODE_APPLY_KEY + userId);
    }

    @Override
    public Integer getGlobalFrozenStatus(final String key) {
        return JwtTokenUtils.getGlobalFrozenStatus(key);
    }

    @Override
    public void setGlobalFrozenStatus(final String key, final Integer status) {
        JwtTokenUtils.setGlobalFrozenStatus(key, status);
    }

    @Override
    public void deleteGlobalFrozenStatus(final String key) {
        JwtTokenUtils.removeGlobalFrozenStatus(key);
    }

    @Override
    public SessionInfo getSession(final long userId) {
        return JwtTokenUtils.getSession(userId);
    }

    @Override
    public void loadAllIpRateLimit(final List<UserIpRateLimit> list) {
        this.redisTemplate.delete(ApiKeyConsts.OPEN_API_KEY_IP_RATE_LIMIT_KEY);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(e -> {
                final IpRateLimitInfo ipRateLimitInfo = IpRateLimitInfo.builder()
                        .ip(e.getIp())
                        .rateLimit(e.getRateLimit())
                        .build();
                this.redisTemplate.opsForHash().put(
                        ApiKeyConsts.OPEN_API_KEY_IP_RATE_LIMIT_KEY,
                        String.valueOf(e.getIp()),
                        JSON.toJSONString(ipRateLimitInfo));
            });
        }
    }

    @Override
    public void deleteOpenApiUserInfo(final long userId) {
        final String key = StringUtils.join(ApiKeyConsts.OPEN_API_KEY_USER_ID_PREFIX, userId);
        this.redisTemplate.delete(key);
    }
}
