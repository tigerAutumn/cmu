package cc.newex.springcloud.config.environment.aliyun;

import cc.newex.springcloud.config.environment.Constants;
import cc.newex.springcloud.config.environment.util.YamlPropertiesConverter;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author newex-team
 * @date 2018-07-17
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "spring.cloud.config.server.oss.endpoint")
@EnableConfigurationProperties(AliyunOssProperties.class)
public class AliyunOssEnvironmentRepository implements EnvironmentRepository {
    @Autowired
    private AliyunOssProperties ossProperties;
    private final YamlPropertiesConverter yamlPropertiesConverter = new YamlPropertiesConverter();

    @Override
    public Environment findOne(final String application, final String profile, final String label) {
        final Environment environment = new Environment(application, profile);

        final String filename = String.join(Constants.CHAR_MINUS, Constants.FILENAME_PREFIX, profile);
        String filePath = String.join(Constants.CHAR_FORWARD_SLASH, this.ossProperties.getBasedir(), application, filename);
        if (StringUtils.isNotEmpty(label)) {
            filePath = String.join(Constants.CHAR_MINUS, filePath, label);
        }

        final OSSClient ossClient = new OSSClient(
                this.ossProperties.getEndpoint(),
                this.ossProperties.getAccessKeyId(),
                this.ossProperties.getAccessKeySecret()
        );
        for (final String suffix : Constants.FILE_SUFFIX) {
            final String fullFileName = String.join(Constants.CHAR_DOT, filePath, suffix);
            try {
                if (ossClient.doesObjectExist(this.ossProperties.getBucketName(), fullFileName)) {
                    final OSSObject ossObject = ossClient.getObject(this.ossProperties.getBucketName(), fullFileName);
                    final Properties properties = this.yamlPropertiesConverter.getProperties(suffix, ossObject.getObjectContent());
                    environment.add(new PropertySource(String.join(Constants.CHAR_MINUS, application, profile), properties));
                    break;
                }
            } catch (final Exception e) {
                log.error("exception occurs loading properties from aliyun oss", e);
            }
        }
        ossClient.shutdown();

        return environment;
    }
}
