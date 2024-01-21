package cc.newex.springcloud.config.environment.aws;

import cc.newex.springcloud.config.environment.AbstractProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author newex-team
 * @date 2018-07-17
 */
@ConfigurationProperties(prefix = "spring.cloud.config.server.s3")
public class AwsS3Properties extends AbstractProperties {

}
