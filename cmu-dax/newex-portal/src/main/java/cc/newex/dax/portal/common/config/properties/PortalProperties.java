package cc.newex.dax.portal.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 公共配置类
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "newex.portal")
public class PortalProperties {
    private Map<String, String> urls;
    @NestedConfigurationProperty
    private XFrameOptions xFrameOptions;

    @Data
    public static class XFrameOptions {
        private List<String> allowedOrigins;
    }

}
