package cc.newex.commons.spring.cloud.config.client;

import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.core.env.PropertySource;

/**
 * @author newex-team
 * @date 2017/12/09
 */
public class ContextRefreshConfigServicePropertySourceChangeListener
        implements ConfigServicePropertySourceChangeListener {

    private final ContextRefresher contextRefresher;

    public ContextRefreshConfigServicePropertySourceChangeListener(final ContextRefresher contextRefresher) {
        super();
        this.contextRefresher = contextRefresher;
    }

    @Override
    public void onChange(final PropertySource<?> propertySource) {
        this.contextRefresher.refresh();
    }
}
