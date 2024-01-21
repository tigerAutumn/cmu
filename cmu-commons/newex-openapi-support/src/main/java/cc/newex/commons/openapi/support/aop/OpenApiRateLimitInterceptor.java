package cc.newex.commons.openapi.support.aop;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.openapi.specs.annotation.OpenApiRateLimit;
import cc.newex.commons.openapi.specs.auth.IpRateLimitService;
import cc.newex.commons.openapi.specs.enums.HttpHeadersEnum;
import cc.newex.commons.openapi.specs.enums.RateLimitStrategyEnum;
import cc.newex.commons.openapi.specs.model.IpRateLimitInfo;
import cc.newex.commons.openapi.specs.model.OpenApiKeyInfo;
import cc.newex.commons.openapi.support.config.OpenApiConfig;
import cc.newex.commons.openapi.support.constant.OpenApiRateLimitKeys;
import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.commons.ratelimiter.RateLimiter;
import cc.newex.commons.ratelimiter.model.RateLimitInfo;
import cc.newex.commons.ratelimiter.util.RateLimiterUtils;
import cc.newex.commons.support.consts.UserAuthConsts;
import com.alibaba.fastjson.JSON;
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
public class OpenApiRateLimitInterceptor extends HandlerInterceptorAdapter {

    private final static int VALID_ARRAY_LENGTH = 2;

    @Autowired
    private OpenApiConfig openApiConfig;

    @Autowired
    private RateLimiter rateLimiter;

    @Autowired
    private IpRateLimitService ipRateLimitService;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final String requestUri = request.getRequestURI();

        if (handler instanceof HandlerMethod) {
            final Method method = ((HandlerMethod) handler).getMethod();
            final String mappingUrl = RateLimiterUtils.getRequestMappingUrl(method);

            if (StringUtils.isBlank(mappingUrl)) {
                log.info("current visiting uri: {}, mappingUrl is blank", requestUri);

                return true;
            }

            String limitValue = StringUtils.EMPTY;
            RateLimitStrategyEnum strategy = RateLimitStrategyEnum.IP;
            final OpenApiRateLimit limitAnnotation = method.getAnnotation(OpenApiRateLimit.class);
            if (limitAnnotation != null) {
                limitValue = limitAnnotation.value();
                strategy = limitAnnotation.strategy();
            }

            final RateLimitParameter rateLimitParameter = this.createRateLimitParameter(request, mappingUrl, strategy, limitValue);
            final boolean canAcquire = this.rateLimiter.canAcquire(rateLimitParameter);

            if (canAcquire) {
                log.debug("rate limit key:{}", rateLimitParameter.getRateLimitKey());
                RateLimiterUtils.setResponseHeaders(response,
                        rateLimitParameter.getSeconds(),
                        rateLimitParameter.getUsed(),
                        rateLimitParameter.getMaxRequestTimes()
                );
            } else {
                log.info("current visiting uri: {}, RateLimitParameter: {}, too many requests", requestUri, JSON.toJSONString(rateLimitParameter));

                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());

                return false;
            }
        }

        return true;
    }

    protected RateLimitParameter createRateLimitParameter(final HttpServletRequest request,
                                                          final String mappingValue,
                                                          final RateLimitStrategyEnum strategy,
                                                          final String limitValue) {
        final String rateLimitKey = this.getRateLimitKey(request, mappingValue, strategy);
        final RateLimitInfo defaultRateLimit = this.getDefaultLimitation(strategy, limitValue);

        RateLimitInfo rateLimitInfo = null;
        if (RateLimitStrategyEnum.API_KEY == strategy) {
            final OpenApiKeyInfo apiKeyInfo = (OpenApiKeyInfo) request.getAttribute(UserAuthConsts.CURRENT_USER_API_KEY_INFO);
            if (apiKeyInfo != null) {
                log.debug("rate limit api key:{}", apiKeyInfo.getApiKey());
                rateLimitInfo = this.getRateLimitInfo(apiKeyInfo.getRateLimit(), defaultRateLimit.getMaxRequestTimes(), defaultRateLimit.getSeconds());
            }
        } else if (RateLimitStrategyEnum.IP == strategy) {
            final String ip = IpUtil.getRealIPAddress(request);
            log.debug("rate limit ip:{}", ip);
            if (StringUtils.isNotEmpty(ip)) {
                final IpRateLimitInfo ipRateLimitInfo = this.ipRateLimitService.getRateLimitByIp(IpUtil.toLong(ip));
                if (ipRateLimitInfo != null) {
                    rateLimitInfo = this.getRateLimitInfo(ipRateLimitInfo.getRateLimit(), defaultRateLimit.getMaxRequestTimes(), defaultRateLimit.getSeconds());
                }
            }
        }

        if (rateLimitInfo == null) {
            rateLimitInfo = RateLimitInfo.builder()
                    .maxRequestTimes(defaultRateLimit.getMaxRequestTimes())
                    .seconds(defaultRateLimit.getSeconds())
                    .build();
        }

        return RateLimitParameter.builder()
                .rateLimitKey(rateLimitKey)
                .maxRequestTimes(rateLimitInfo.getMaxRequestTimes())
                .seconds(rateLimitInfo.getSeconds()).build();
    }

    private RateLimitInfo getRateLimitInfo(final String rateLimit, final int defaultMaxRequestTimes, final int defaultSeconds) {
        final String[] rateLimits = StringUtils.split(rateLimit, '/');
        if (rateLimits.length == VALID_ARRAY_LENGTH) {
            final int rateLimitMaxRequestTimes = NumberUtils.toInt(rateLimits[0], defaultMaxRequestTimes);
            final int rateLimitSeconds = NumberUtils.toInt(rateLimits[1], defaultSeconds);
            return RateLimitInfo.builder()
                    .maxRequestTimes(Math.max(defaultMaxRequestTimes, rateLimitMaxRequestTimes))
                    .seconds(Math.max(defaultSeconds, rateLimitSeconds))
                    .build();
        }
        return null;
    }

    /**
     * @param request
     * @param mappingValue
     * @param strategy
     * @return {@link OpenApiRateLimitKeys#RATE_LIMIT_KEY_PREFIX} + apiKey(or realIP) + requestMappingValue
     */
    protected String getRateLimitKey(final HttpServletRequest request,
                                     final String mappingValue,
                                     final RateLimitStrategyEnum strategy) {
        String middleKey;
        if (RateLimitStrategyEnum.API_KEY == strategy) {
            middleKey = request.getHeader(HttpHeadersEnum.ACCESS_KEY.getName());
        } else {
            middleKey = StringUtils.joinWith("_", "IP", IpUtil.toLong(IpUtil.getRealIPAddress(request)));
        }
        middleKey = StringUtils.defaultIfBlank(middleKey, "undefined_key");
        return OpenApiRateLimitKeys.RATE_LIMIT_KEY_PREFIX + StringUtils.joinWith(":", middleKey, mappingValue);
    }

    private RateLimitInfo getDefaultLimitation(final RateLimitStrategyEnum strategy,
                                               final String limitValue) {
        final int defaultMaxRequestTimes = (strategy == RateLimitStrategyEnum.API_KEY ?
                this.openApiConfig.getApiKeyRateLimiter().getMaxRequestTimes() : this.openApiConfig.getIpRateLimiter().getMaxRequestTimes());
        final int defaultSeconds = (strategy == RateLimitStrategyEnum.API_KEY ?
                this.openApiConfig.getApiKeyRateLimiter().getSeconds() : this.openApiConfig.getIpRateLimiter().getSeconds());

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
