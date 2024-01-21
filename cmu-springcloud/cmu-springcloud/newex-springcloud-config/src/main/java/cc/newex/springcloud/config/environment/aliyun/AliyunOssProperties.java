package cc.newex.springcloud.config.environment.aliyun;

import cc.newex.springcloud.config.environment.AbstractProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author newex-team
 * @date 2018-07-17
 */
@ConfigurationProperties(prefix = "spring.cloud.config.server.oss")
public class AliyunOssProperties extends AbstractProperties {
}
