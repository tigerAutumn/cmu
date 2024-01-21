package cc.newex.commons.spring.cloud.config.client;

import org.springframework.core.env.PropertySource;

/**
 * @author newex-team
 * @date 2017/12/09
 */
public interface ConfigServicePropertySourceChangeListener {
    void onChange(PropertySource<?> propertySource);
}
