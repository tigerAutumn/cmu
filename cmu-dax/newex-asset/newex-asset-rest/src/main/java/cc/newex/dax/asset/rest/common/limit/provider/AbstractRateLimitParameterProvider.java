package cc.newex.dax.asset.rest.common.limit.provider;

import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.commons.ratelimiter.model.RateLimitInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.lang.reflect.Type;

/**
 * @author newex-team
 * @date 2018-08-10
 */
public abstract class AbstractRateLimitParameterProvider {
    protected final static int VALID_ARRAY_LENGTH = 2;

    /**
     * @param limitValue
     * @param targetType
     * @param body
     * @param defaultLimits
     * @return
     */
    protected RateLimitParameter getRateLimitParameter(final String limitValue,
                                                       final Type targetType,
                                                       final Object body,
                                                       final int[] defaultLimits) {
        final String rateLimitKey = this.getRateLimitKey(targetType, body);
        final RateLimitInfo rateLimitInfo = this.getRateLimitInfo(limitValue, defaultLimits);
        return RateLimitParameter.builder()
                .rateLimitKey(rateLimitKey)
                .maxRequestTimes(rateLimitInfo.getMaxRequestTimes())
                .seconds(rateLimitInfo.getSeconds()).build();
    }

    /**
     * @param limitValue
     * @param defaultLimits
     * @return
     */
    protected RateLimitInfo getRateLimitInfo(final String limitValue, final int[] defaultLimits) {
        final String[] limits = StringUtils.split(limitValue, "/");
        if (ArrayUtils.getLength(limits) == VALID_ARRAY_LENGTH) {
            return RateLimitInfo.builder()
                    .maxRequestTimes(NumberUtils.toInt(limits[0], defaultLimits[0]))
                    .seconds(NumberUtils.toInt(limits[1], defaultLimits[1]))
                    .build();
        }
        return RateLimitInfo.builder()
                .maxRequestTimes(defaultLimits[0])
                .seconds(defaultLimits[1])
                .build();
    }

    protected abstract String getRateLimitKey(final Type targetType,
                                              final Object body);
}
