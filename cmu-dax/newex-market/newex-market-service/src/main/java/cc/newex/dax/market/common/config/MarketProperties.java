package cc.newex.dax.market.common.config;

import cc.newex.commons.support.properties.DataSourceProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@ConfigurationProperties(prefix = "newex.market")
public class MarketProperties {
    private String dataKey;
    private String intraPrefix = "admin";
    private String intraVersion = "v1";
    @NestedConfigurationProperty
    private Map<String, DataSourceProperty> datasource = new HashMap<>();
    private Common common;

    public Map<String, DataSourceProperty> getDatasource() {
        return this.datasource;
    }

    public void setDatasource(final Map<String, DataSourceProperty> datasource) {
        this.datasource = datasource;
    }

    public Common getCommon() {
        return this.common;
    }

    public void setCommon(final Common common) {
        this.common = common;
    }

    public String getDataKey() {
        return this.dataKey;
    }

    public void setDataKey(final String dataKey) {
        this.dataKey = dataKey;
    }

    public String getIntraPrefix() {
        return this.intraPrefix;
    }

    public void setIntraPrefix(final String intraPrefix) {
        this.intraPrefix = intraPrefix;
    }

    public String getIntraVersion() {
        return this.intraVersion;
    }

    public void setIntraVersion(final String intraVersion) {
        this.intraVersion = intraVersion;
    }

    public static class Common {
    }

}
