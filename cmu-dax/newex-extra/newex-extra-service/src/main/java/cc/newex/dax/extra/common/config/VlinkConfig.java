package cc.newex.dax.extra.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author gi
 * @date 12/6/18
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "vlink")
public class VlinkConfig {
    private  String domain;
    private  String appKey;
    private  String secretKey;
    private  Long userId;
    private String walletUrl;
}
