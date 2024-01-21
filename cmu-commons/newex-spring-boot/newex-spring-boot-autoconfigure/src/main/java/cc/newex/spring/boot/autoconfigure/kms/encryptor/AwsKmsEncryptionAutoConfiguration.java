package cc.newex.spring.boot.autoconfigure.kms.encryptor;

import cc.newex.commons.kms.client.AwsKmsClient;
import cc.newex.commons.kms.client.KmsClientFactory;
import cc.newex.commons.kms.client.config.KmsClientConfiger;
import cc.newex.commons.spring.cloud.config.kms.AwsKmsTextEncryptor;
import cc.newex.spring.boot.autoconfigure.kms.KmsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This config must be applied to the bootstrap context, which is done by META-INF/spring.factories.<br/>
 * The properties here can be configured in bootstrap.[yml|xml|properties], but not in application.[yml]xml|properties]
 */
@Configuration
@ConditionalOnProperty(value = "newex.kms.aws.policy.encryptor", matchIfMissing = true)
@ConditionalOnClass(AwsKmsTextEncryptor.class)
@EnableConfigurationProperties(KmsProperties.class)
class AwsKmsEncryptionAutoConfiguration {
    private final KmsProperties.KmsConfig config;

    public AwsKmsEncryptionAutoConfiguration(final KmsProperties properties) {
        this.config = properties.getAws();
    }

    @Bean
    @ConditionalOnProperty("spring.cloud.config.enabled")
    public AwsKmsTextEncryptor awsKmsTextEncryptor() {
        return new AwsKmsTextEncryptor(this.awsKmsClient());
    }

    @Bean
    @ConditionalOnProperty("spring.cloud.config.enabled")
    public AwsKmsClient awsKmsClient() {
        return KmsClientFactory.createAwsKmsClient(
                KmsClientConfiger.createAwsKmsClientConfig(
                        this.config.getVpcEndpoint(),
                        this.config.getEndpoint(),
                        this.config.getRegion(),
                        this.config.getAccessKeyId(),
                        this.config.getAccessKeySecret(),
                        this.config.getKeyId())
        );
    }
}
