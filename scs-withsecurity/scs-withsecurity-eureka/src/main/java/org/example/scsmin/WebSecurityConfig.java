package org.example.scsmin;

import org.example.scs.common.MyAuthenticationManagerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
        http.authorizeRequests().antMatchers("/actuator/health").permitAll()
                .antMatchers("/actuator/**","/**").authenticated()
                .and()
                .httpBasic();
    }
}