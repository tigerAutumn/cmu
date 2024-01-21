package cc.newex.dax.asset.rest.common.limit.annotation;

import cc.newex.dax.asset.rest.common.limit.enums.RetryLimitTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryLimit {

    /**
     * @return 限次类型
     */
    RetryLimitTypeEnum type();

    /**
     * @return 如(10 / 1 表示每秒10次)
     */
    String value() default "";

}
