package cc.newex.dax.boss.web.common.config.security;

import cc.newex.commons.security.config.BasicWebSecurityConfig;
import cc.newex.dax.boss.web.common.security.BossAuthenticationEntryPoint;
import cc.newex.dax.boss.web.common.security.BossAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

/**
 * @author newex-team
 * @date 2017-03-18
 **/
@Primary
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends BasicWebSecurityConfig {
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private BossAuthenticationEntryPoint unauthorizedHandler;

    public WebSecurityConfig(final SecurityProperties security) {
        super(security);
    }

    @Autowired
    public void configureAuthentication(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(this.passwordEncoder());
    }

    @Bean
    public BossAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new BossAuthenticationTokenFilter();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(this.passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        SpringBootWebSecurityConfiguration.configureHeaders(http.headers(),
                this.security.getHeaders());
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/login", "/login/**", "/inner/**",
                        "/css/**",
                        "/custom/**",
                        "/images/**",
                        "/js/**",
                        "/vendor/**",
                        "/favicon.ico"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                //.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                //.and()
                .addFilterBefore(this.authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        //headers
        http.headers()
                .frameOptions()
                .sameOrigin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



