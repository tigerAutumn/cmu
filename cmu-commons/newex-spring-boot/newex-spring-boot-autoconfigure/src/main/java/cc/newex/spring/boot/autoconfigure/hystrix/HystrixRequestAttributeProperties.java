package cc.newex.spring.boot.autoconfigure.hystrix;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author newex-team
 * @date 2018-07-14
 */
@ConfigurationProperties("newex.hystrix.request-attributes")
public class HystrixRequestAttributeProperties {
    /**
     * Enable Hystrix propagate http request and response. Defaults to false.
     */
    private boolean enabled = false;

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
