package cc.newex.dax.boss.web.common.config.security;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author newex-team
 * @date 2018-06-02
 */
@Configuration
public class GoogleAuthConfig {
    @Bean
    public GoogleAuthenticator googleAuthenticator() {
        return new GoogleAuthenticator();
    }
}
