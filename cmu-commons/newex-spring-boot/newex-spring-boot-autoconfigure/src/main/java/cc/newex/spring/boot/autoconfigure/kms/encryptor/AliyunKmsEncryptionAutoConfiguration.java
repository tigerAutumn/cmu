package cc.newex.spring.boot.autoconfigure.kms.encryptor;

import cc.newex.commons.kms.client.AliyunKmsClient;
import cc.newex.commons.kms.client.KmsClientFactory;
import cc.newex.commons.kms.client.config.KmsClientConfiger;
import cc.newex.commons.spring.cloud.config.kms.AliyunKmsTextEncryptor;
import cc.newex.spring.boot.autoconfigure.kms.KmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This config must be applied to the bootstrap context, which is done by META-INF/spring.factories.<br/>
 * The properties here can be configured in bootstrap.[yml|xml|properties], but not in application.[yml]xml|properties]
 */
@Slf4j
@Configuration
@ConditionalOnProperty("newex.kms.aliyun.policy.encryptor")
@ConditionalOnClass(AliyunKmsTextEncryptor.class)
@EnableConfigurationProperties(KmsProperties.class)
class AliyunKmsEncryptionAutoConfiguration {
    private final KmsProperties.KmsConfig config;

    public AliyunKmsEncryptionAutoConfiguration(final KmsProperties properties) {
        this.config = properties.getAliyun();
    }

    @Bean
    @ConditionalOnProperty("spring.cloud.config.enabled")
    public AliyunKmsTextEncryptor aliyunKmsTextEncryptor() {
        return new AliyunKmsTextEncryptor(this.aliyunKmsClient());
    }

    @Bean
    @ConditionalOnProperty("spring.cloud.config.enabled")
    public AliyunKmsClient aliyunKmsClient() {
        return KmsClientFactory.createAliyunKmsClient(
                KmsClientConfiger.createAliyunKmsClientConfig(
                        this.config.getVpcEndpoint(),
                        this.config.getEndpoint(),
                        this.config.getRegion(),
                        this.config.getAccessKeyId(),
                        this.config.getAccessKeySecret(),
                        this.config.getKeyId())
        );
    }
}
