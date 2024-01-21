package cc.newex.wallet.signature.annotation;

import java.lang.annotation.*;

/**
 * @author newex-team
 * @create 2018-11-21 上午10:38
 **/
@Target({ElementType.TYPE})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HessianClient {
    String value();
}
