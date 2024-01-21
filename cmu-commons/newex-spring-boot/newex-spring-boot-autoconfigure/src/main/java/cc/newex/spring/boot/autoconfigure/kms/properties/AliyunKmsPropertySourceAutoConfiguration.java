package cc.newex.spring.boot.autoconfigure.kms.properties;

import cc.newex.commons.kms.client.AliyunKmsClient;
import cc.newex.commons.kms.client.KmsClientFactory;
import cc.newex.commons.kms.client.config.KmsClientConfiger;
import cc.newex.commons.spring.cloud.config.client.KmsConfigServicePropertySourceLocator;
import cc.newex.spring.boot.autoconfigure.kms.KmsProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

/**
 * @author newex-team
 * @date 2017/12/10
 */
@Configuration
@ConditionalOnProperty("newex.kms.aliyun.policy.properties")
@ConditionalOnClass(KmsConfigServicePropertySourceLocator.class)
@EnableConfigurationProperties(KmsProperties.class)
public class AliyunKmsPropertySourceAutoConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;

    private final KmsProperties.KmsConfig config;

    public AliyunKmsPropertySourceAutoConfiguration(final KmsProperties properties) {
        this.config = properties.getAliyun();
    }


    @Bean
    @Primary
    @Order(-1)
    @ConditionalOnProperty("spring.cloud.config.enabled")
    public PropertySourceLocator propertySourceLocator(final ConfigClientProperties defaultProperties) {
        final AliyunKmsClient kmsClient = KmsClientFactory.createAliyunKmsClient(
                KmsClientConfiger.createAliyunKmsClientConfig(
                        this.config.getVpcEndpoint(),
                        this.config.getEndpoint(),
                        this.config.getRegion(),
                        this.config.getAccessKeyId(),
                        this.config.getAccessKeySecret(),
                        this.config.getKeyId())
        );
        return new KmsConfigServicePropertySourceLocator(defaultProperties, this.applicationName, kmsClient);
    }
}
