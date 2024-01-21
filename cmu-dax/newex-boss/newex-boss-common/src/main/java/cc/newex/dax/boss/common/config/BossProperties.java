package cc.newex.dax.boss.common.config;

import cc.newex.commons.support.properties.DataSourceProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author newex-team
 * @date 2017-03-18
 */
@Configuration
@ConfigurationProperties(prefix = "newex.boss")
public class BossProperties {
    @NestedConfigurationProperty
    private Map<String, DataSourceProperty> datasource = new HashMap<>();

    public Map<String, DataSourceProperty> getDatasource() {
        return this.datasource;
    }

    public void setDatasource(final Map<String, DataSourceProperty> datasource) {
        this.datasource = datasource;
    }
}
