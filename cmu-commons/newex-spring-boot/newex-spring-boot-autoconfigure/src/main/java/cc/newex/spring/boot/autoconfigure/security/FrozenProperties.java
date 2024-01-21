package cc.newex.spring.boot.autoconfigure.security;

import cc.newex.commons.support.properties.InterceptorProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author newex-team
 * @date 2017/11/20
 */
@ConfigurationProperties(prefix = "newex.security.frozen")
public class FrozenProperties {
    /**
     * {@link cc.newex.commons.security.jwt.enums.BizTypeEnum}
     */
    private String bizType = "";
    @NestedConfigurationProperty
    private InterceptorProperty urlPatterns = new InterceptorProperty("", "");

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(final String bizType) {
        this.bizType = bizType;
    }

    public InterceptorProperty getUrlPatterns() {
        return this.urlPatterns;
    }

    public void setUrlPatterns(final InterceptorProperty urlPatterns) {
        this.urlPatterns = urlPatterns;
    }
}
