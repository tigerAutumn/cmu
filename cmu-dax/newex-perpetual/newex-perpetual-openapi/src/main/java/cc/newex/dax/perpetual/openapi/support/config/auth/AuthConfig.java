package cc.newex.dax.perpetual.openapi.support.config.auth;

import cc.newex.dax.perpetual.openapi.support.auth.OpenApiAuthManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author newex-team
 * @date 2018-06-16
 */
@Configuration
public class AuthConfig {
    @Bean("customOpenApiAuthManager")
    public static OpenApiAuthManager customOpenApiAuthManager() {
        return new OpenApiAuthManager();
    }
}
