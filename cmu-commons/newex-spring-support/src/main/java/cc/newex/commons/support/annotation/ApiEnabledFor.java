package cc.newex.commons.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * api是否启用
 *
 * @author newex-team
 * @date 2017/12/09
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiEnabledFor {
    /**
     * 应用名称(取值为newex.cc）
     *
     * @return newex.cc
     */
    String value() default "";
}
