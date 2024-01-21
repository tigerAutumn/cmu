package cc.newex.commons.security.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
public class BasicWebSecurityConfig extends WebSecurityConfigurerAdapter {
    protected SecurityProperties security;

    public BasicWebSecurityConfig(final SecurityProperties security) {
        this.security = security;
    }

    @Override
    public void configure(final WebSecurity web) {
        //web.httpFirewall(new DefaultHttpFirewall());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        SpringBootWebSecurityConfiguration.configureHeaders(http.headers(), this.security.getHeaders());

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .anyRequest()
                .permitAll();
    }
}
