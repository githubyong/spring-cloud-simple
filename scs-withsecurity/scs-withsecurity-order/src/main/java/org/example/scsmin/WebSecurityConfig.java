package org.example.scsmin;

import org.example.scs.common.MyAuthenticationManagerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Order(SecurityProperties.BASIC_AUTH_ORDER - 20)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyAuthenticationManagerBuilder confBuilder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        confBuilder.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.requestMatchers()
                .requestMatchers(
                        new OrRequestMatcher(
                                new AntPathRequestMatcher("/actuator/**")
                        )
                )
                .and()
                .authorizeRequests().antMatchers("/actuator/health").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}