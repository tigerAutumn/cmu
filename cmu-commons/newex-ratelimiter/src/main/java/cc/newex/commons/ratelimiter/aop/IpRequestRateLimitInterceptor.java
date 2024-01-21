package cc.newex.commons.ratelimiter.aop;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.commons.ratelimiter.RateLimiter;
import cc.newex.commons.ratelimiter.annotation.IpRequestRateLimit;
import cc.newex.commons.ratelimiter.config.RateLimiterConfig;
import cc.newex.commons.ratelimiter.consts.RateLimitKeys;
import cc.newex.commons.ratelimiter.model.RateLimitInfo;
import cc.newex.commons.ratelimiter.util.RateLimiterUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Api请求次数限制拦截器
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
public class IpRequestRateLimitInterceptor extends HandlerInterceptorAdapter {
    private final static int VALID_ARRAY_LENGTH = 2;

    @Autowired
    private RateLimiterConfig rateLimiterConfig;
    @Autowired
    private RateLimiter rateLimiter;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        if (handler instanceof HandlerMethod) {
            final Method method = ((HandlerMethod) handler).getMethod();
            final String mappingUrl = RateLimiterUtils.getRequestMappingUrl(method);
            if (StringUtils.isBlank(mappingUrl)) {
                return true;
            }

            String limitValue = StringUtils.EMPTY;
            final IpRequestRateLimit limitAnnotation = method.getAnnotation(IpRequestRateLimit.class);
            if (limitAnnotation != null) {
                limitValue = limitAnnotation.value();
            }

            final RateLimitParameter rateLimitParameter = this.createRateLimitParameter(request, mappingUrl, limitValue);
            final boolean canAcquire = this.rateLimiter.canAcquire(rateLimitParameter);
            if (canAcquire) {
                log.debug("ip rate limit key:{}", rateLimitParameter.getRateLimitKey());
                RateLimiterUtils.setResponseHeaders(response,
                        rateLimitParameter.getSeconds(),
                        rateLimitParameter.getUsed(),
                        rateLimitParameter.getMaxRequestTimes()
                );
            } else {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                return false;
            }
        }
        return true;
    }

    protected RateLimitParameter createRateLimitParameter(final HttpServletRequest request,
                                                          final String mappingUrl,
                                                          final String limitValue) {
        final String rateLimitKey = this.getRateLimitKey(request, mappingUrl);
        final RateLimitInfo rateLimitInfo = this.getRateLimitInfo(limitValue);
        return RateLimitParameter.builder()
                .rateLimitKey(rateLimitKey)
                .maxRequestTimes(rateLimitInfo.getMaxRequestTimes())
                .seconds(rateLimitInfo.getSeconds()).build();
    }

    protected String getRateLimitKey(final HttpServletRequest request,
                                     final String mappingUrl) {
        final String ip = IpUtil.getRealIPAddress(request);
        final long longIp = IpUtil.toLong(IpUtil.getRealIPAddress(request));
        log.debug("rate limit ip:{}({})", ip, longIp);
        return RateLimitKeys.IP_RATE_LIMITER_KEY_PREFIX + StringUtils.joinWith(":", longIp, mappingUrl);
    }

    protected RateLimitInfo getRateLimitInfo(final String limitValue) {
        final int defaultMaxRequestTimes = this.rateLimiterConfig.getIpRequestRateLimiter().getMaxRequestTimes();
        final int defaultSeconds = this.rateLimiterConfig.getIpRequestRateLimiter().getSeconds();

        final String[] limits = StringUtils.split(limitValue, "/");
        if (ArrayUtils.getLength(limits) == VALID_ARRAY_LENGTH) {
            return RateLimitInfo.builder()
                    .maxRequestTimes(NumberUtils.toInt(limits[0], defaultMaxRequestTimes))
                    .seconds(NumberUtils.toInt(limits[1], defaultSeconds))
                    .build();
        }
        return RateLimitInfo.builder()
                .maxRequestTimes(defaultMaxRequestTimes)
                .seconds(defaultSeconds)
                .build();
    }
}
