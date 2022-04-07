package org.example.auth.config;

import org.example.scs.common.MyAuthenticationManagerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Primary
@Order(SecurityProperties.BASIC_AUTH_ORDER-10)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	ScsAuthenticationProvider authenticationProvider;

	@Autowired
	MyAuthenticationManagerBuilder confBuilder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		confBuilder.configure(auth);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider);
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();
		http.authorizeRequests()
				.antMatchers("/tokens/**","/token/**","/test/**","/h2-console/**","/actuator/health").permitAll()
				.and()
				.authorizeRequests().anyRequest().authenticated()
				.and()
				.httpBasic();

//				.antMatchers("/actuator/**").access("@actuatorservice.hasPermission()")
	}
}
