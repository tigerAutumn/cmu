package cc.newex.dax.users.rest.common.limit.provider;

import cc.newex.commons.ratelimiter.RateLimitParameter;

import java.lang.reflect.Type;

/**
 * @author newex-team
 * @date 2018-08-10
 */
public interface RateLimitParameterProvider {

    RateLimitParameter createRateLimitParameter(final String limitValue,
                                                final Type targetType,
                                                final Object body);
}
