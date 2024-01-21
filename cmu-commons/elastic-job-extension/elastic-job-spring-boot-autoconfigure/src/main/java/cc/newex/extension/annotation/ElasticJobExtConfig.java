package cc.newex.extension.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Liu Hailin
 * @create 2017-08-17 下午6:47
 **/
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticJobExtConfig {

    String cron();

    int shardingTotalCount() default 1;
}
