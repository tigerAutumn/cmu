package cc.newex.spring.boot.autoconfigure.broker;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "newex.broker")
public class BrokerProperties {
    private Boolean enabled;
    private String urlPatterns = "/v1/**,/api/v1/**";
    private String excludeUrlPatterns = "/customError/**,/admin/**,/inner/**";

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(String urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public String getExcludeUrlPatterns() {
        return excludeUrlPatterns;
    }

    public void setExcludeUrlPatterns(String excludeUrlPatterns) {
        this.excludeUrlPatterns = excludeUrlPatterns;
    }
}
