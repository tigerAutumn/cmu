package cc.newex.springcloud.config.environment.aws;

import cc.newex.springcloud.config.environment.Constants;
import cc.newex.springcloud.config.environment.util.YamlPropertiesConverter;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
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
@ConditionalOnProperty(value = "spring.cloud.config.server.s3.endpoint")
@EnableConfigurationProperties(AwsS3Properties.class)
public class AwsS3EnvironmentRepository implements EnvironmentRepository {
    @Autowired
    private AwsS3Properties s3Properties;
    private final YamlPropertiesConverter yamlPropertiesConverter = new YamlPropertiesConverter();

    @Override
    public Environment findOne(final String application, final String profile, final String label) {
        final Environment environment = new Environment(application, profile);
        final String filename = String.join(Constants.CHAR_MINUS, Constants.FILENAME_PREFIX, profile);
        String filePath = String.join(Constants.CHAR_FORWARD_SLASH, this.s3Properties.getBasedir(), application, filename);
        if (StringUtils.isNotEmpty(label)) {
            filePath = String.join(Constants.CHAR_MINUS, filePath, label);
        }

        final AmazonS3 s3Client = this.createS3Client();
        for (final String suffix : Constants.FILE_SUFFIX) {
            final String fullFileName = String.join(Constants.CHAR_DOT, filePath, suffix);
            try {
                if (s3Client.doesObjectExist(this.s3Properties.getBucketName(), fullFileName)) {
                    final S3Object s3Object = s3Client.getObject(this.s3Properties.getBucketName(), fullFileName);
                    final Properties properties = this.yamlPropertiesConverter.getProperties(suffix, s3Object.getObjectContent());
                    environment.add(new PropertySource(String.join(Constants.CHAR_MINUS, application, profile), properties));
                    break;
                }
            } catch (final Exception e) {
                log.error("exception occurs loading properties from aws s3", e);
            }
        }
        s3Client.shutdown();

        return environment;
    }

    private AmazonS3 createS3Client() {
        final AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(this.s3Properties.getEndpoint(), this.s3Properties.getRegion());
        final BasicAWSCredentials credentials = new BasicAWSCredentials(
                this.s3Properties.getAccessKeyId(), this.s3Properties.getAccessKeySecret()
        );
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
