package cc.newex.dax.market.rest.common.config.security;

import cc.newex.commons.security.config.BasicWebSecurityConfig;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
@Primary
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends BasicWebSecurityConfig {
    public WebSecurityConfig(final SecurityProperties security) {
        super(security);
    }
}


