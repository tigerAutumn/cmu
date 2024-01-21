package cc.newex.commons.openapi.specs.annotation;

import cc.newex.commons.openapi.specs.enums.RateLimitStrategyEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * api请求限制
 *
 * @author newex-team
 * @date 2017/12/09
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenApiRateLimit {
    /**
     * 请求限制值(times/seconds)
     * 如:(10/2)表示2秒最多10次请求
     *
     * @return (times / seconds)
     */
    String value() default "";

    /**
     * 限流策略默认按apiKey限流
     *
     * @return {@link RateLimitStrategyEnum}
     */
    RateLimitStrategyEnum strategy() default RateLimitStrategyEnum.API_KEY;
}
