package cc.newex.wallet.annotation;

import java.lang.annotation.*;

/**
 * @author newex-team
 * @data 01/04/2018
 */
@Target({ElementType.TYPE})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StartThread {
    String value() default "";
}
