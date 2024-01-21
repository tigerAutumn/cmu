package cc.newex.commons.openapi.support.auth;

import cc.newex.commons.dictionary.consts.ApiKeyConsts;
import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.openapi.specs.auth.IpRateLimitService;
import cc.newex.commons.openapi.specs.model.IpRateLimitInfo;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author newex-team
 * @date 2018/6/21
 */
public abstract class AbstractRedisIpRateLimitService implements IpRateLimitService {

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    @Override
    public IpRateLimitInfo getRateLimitByIp(final Long ip) {
        Object value = this.getRateLimitFromRedis(ip);

        if (value == null) {
            final Long nextIp = getNextIp(ip);

            value = this.getRateLimitFromRedis(nextIp);
        }

        return JSON.parseObject(value == null ? null : value.toString(), IpRateLimitInfo.class);
    }

    /**
     * 从redis中查询对应的限流策略
     *
     * @param ip
     * @return
     */
    private Object getRateLimitFromRedis(final Long ip) {
        if (ip == null || ip <= 0) {
            return null;
        }

        final Object value = this.stringRedisTemplate.opsForHash().get(
                ApiKeyConsts.OPEN_API_KEY_IP_RATE_LIMIT_KEY, String.valueOf(ip)
        );

        return value;
    }

    /**
     * 如果当前ip没有匹配到限流策略，则尝试x.x.0.0这个ip对应的限流策略
     *
     * @param ip
     * @return
     */
    private static Long getNextIp(final Long ip) {
        final String sip = IpUtil.longToString(ip);

        final String[] array = StringUtils.split(sip, ".");

        array[2] = "0";
        array[3] = "0";

        final String nextIp = StringUtils.join(array, ".");

        return IpUtil.ipDotDec2Long(nextIp);
    }
}
