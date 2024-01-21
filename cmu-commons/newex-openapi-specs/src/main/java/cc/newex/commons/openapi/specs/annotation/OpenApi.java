package cc.newex.commons.openapi.specs.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 1.mark this class is open api. <br/>
 * 2.Exception global handle.  <br/>
 * Created by newex-team on 2018/2/5 20:24
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenApi {
}
