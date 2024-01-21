package cc.newex.dax.users.rest.common.config.business;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author newex-team
 * @date 2018/7/26
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "newex.users.channelink")
public class ChannelinkProperties {

    private String uri;

}