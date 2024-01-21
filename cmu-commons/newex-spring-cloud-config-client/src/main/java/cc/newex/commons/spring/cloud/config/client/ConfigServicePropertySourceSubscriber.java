package cc.newex.commons.spring.cloud.config.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@Slf4j
public class ConfigServicePropertySourceSubscriber {

    private final ConfigServicePropertySourceLocator locator;
    private final ConfigServicePropertySourceChangeListener listener;
    private final Environment environment;

    private Map<String, Object> localConfig;

    public ConfigServicePropertySourceSubscriber(final ConfigClientProperties properties,
                                                 final ConfigServicePropertySourceChangeListener listener,
                                                 final Environment environment) {
        super();
        this.locator = new ConfigServicePropertySourceLocator(properties);
        this.listener = listener;
        this.environment = environment;
    }

    @PostConstruct
    public void initializeLocalConfig() {
        log.info("initializing localConifg");
        final PropertySource<?> propertySource = this.locator.locate(this.environment);
        final Map<String, Object> remoteConfig = new HashMap<>(64);
        this.extract(propertySource, remoteConfig);
        this.localConfig = remoteConfig;
    }

    /**
     * 每15s检测一次配置是否变化
     */
    @Scheduled(
            initialDelayString = "${newex.spring.cloud.config.client.poll.initialDelay:5000}",
            fixedDelayString = "${newex.spring.cloud.config.client.poll.delay:15000}"
    )
    public void doSubscribe() {
        final PropertySource<?> propertySource = this.locator.locate(this.environment);
        final Map<String, Object> remoteConfig = new HashMap<>(64);
        this.extract(propertySource, remoteConfig);
        if (this.change(this.localConfig, remoteConfig)) {
            log.info("ConfigServicePropertySource changed");
            this.localConfig = remoteConfig;
            this.listener.onChange(propertySource);
            return;
        }
        log.debug("ConfigServicePropertySource not changed");
    }

    /**
     * 将PropertySource中的配置转换成Map
     */
    private void extract(final PropertySource<?> parent, final Map<String, Object> result) {
        if (parent instanceof CompositePropertySource) {
            try {
                final List<PropertySource<?>> sources = new ArrayList<>();
                for (final PropertySource<?> source : ((CompositePropertySource) parent).getPropertySources()) {
                    sources.add(0, source);
                }
                for (final PropertySource<?> source : sources) {
                    this.extract(source, result);
                }
            } catch (final Exception e) {
                log.warn("extract spring cloud config error", e);
            }
        } else if (parent instanceof EnumerablePropertySource) {
            for (final String key : ((EnumerablePropertySource<?>) parent).getPropertyNames()) {
                result.put(key, parent.getProperty(key));
            }
        }
    }

    /**
     * 比较本地和配置中心的配置是否有变化
     */
    private boolean change(final Map<String, Object> local, final Map<String, Object> remote) {
        if (local == null) {
            return true;
        }
        for (final Map.Entry<String, Object> entry : local.entrySet()) {
            if (!remote.containsKey(entry.getKey())) {
                return true;
            } else if (!this.equal(entry.getValue(), remote.get(entry.getKey()))) {
                return true;
            }
        }
        for (final String key : remote.keySet()) {
            if (!local.containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    private boolean equal(final Object one, final Object two) {
        return one == null && two == null || one != null && two != null && one.equals(two);
    }
}
