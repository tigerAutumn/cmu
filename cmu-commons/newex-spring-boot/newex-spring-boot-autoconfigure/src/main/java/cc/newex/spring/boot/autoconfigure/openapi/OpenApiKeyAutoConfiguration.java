package cc.newex.spring.boot.autoconfigure.openapi;

import cc.newex.commons.openapi.specs.config.OpenApiKeyConfig;
import cc.newex.commons.openapi.specs.enums.HmacAlgorithmEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author newex-team
 * @date 2017/11/20
 */
@Order(102)
@Configuration
@EnableConfigurationProperties({OpenApiKeyProperties.class})
@ConditionalOnClass(OpenApiKeyConfig.class)
public class OpenApiKeyAutoConfiguration {
    private final OpenApiKeyProperties openApiKeyProperties;

    public OpenApiKeyAutoConfiguration(final OpenApiKeyProperties openApiKeyProperties) {
        this.openApiKeyProperties = openApiKeyProperties;
    }

    @Bean
    public OpenApiKeyConfig openApiKeyConfig() {
        return OpenApiKeyConfig.builder()
                .prefix(this.openApiKeyProperties.getPrefix())
                .expiredSeconds(this.openApiKeyProperties.getExpiredSeconds())
                .keyAlgorithm(HmacAlgorithmEnum.forName(this.openApiKeyProperties.getKeyAlgorithm()))
                .secretAlgorithm(HmacAlgorithmEnum.forName(this.openApiKeyProperties.getSecretAlgorithm()))
                .passphraseAlgorithm(HmacAlgorithmEnum.forName(this.openApiKeyProperties.getPassphraseAlgorithm()))
                .validatorAlgorithm(HmacAlgorithmEnum.forName(this.openApiKeyProperties.getValidatorAlgorithm()))
                .passphraseSaltConfig(OpenApiKeyConfig.PassphraseSaltConfig.builder()
                        .algorithm(HmacAlgorithmEnum.forName(this.openApiKeyProperties.getPassphraseSalt().getAlgorithm()))
                        .hashIterations(this.openApiKeyProperties.getPassphraseSalt().getHashIterations())
                        .startIndex(this.openApiKeyProperties.getPassphraseSalt().getStartIndex())
                        .endIndex(this.openApiKeyProperties.getPassphraseSalt().getEndIndex()).build()
                ).build();
    }
}
