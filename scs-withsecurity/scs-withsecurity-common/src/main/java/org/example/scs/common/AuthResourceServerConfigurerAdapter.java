package org.example.scs.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Slf4j
@EnableResourceServer
@Conditional(OauthOnConditional.class)
@Order(SecurityProperties.BASIC_AUTH_ORDER + 10)
@Configuration
public class AuthResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {


    @Value("${security.oauth2.signingKey}")
    private String signingKey;

    @Value("${spring.application.name}")
    private String moduleName;

    @Value("${svc.token.allowedClockSkewSeconds:300}")
    private Integer allowedClockSkewSeconds;


    @Override
    public void configure(final HttpSecurity http) throws Exception {

//        log.info("OauthOnResourceServerConfigurerAdapter ignore urls:" + ignorePropertiesConfig.getIgnoreUrls());

        http.requestMatchers()
                .requestMatchers(
                        new NegatedRequestMatcher(
                                new OrRequestMatcher(
                                        new AntPathRequestMatcher("/actuator/**")
                                )
                        )
                )
                .and()
                .authorizeRequests().antMatchers( "/test/**").permitAll()
                .antMatchers("/swagger/**", "/swagger*", "/webjars/**", "/swagger-*/**", "/v2/api*").permitAll()
                .antMatchers("/tokens/**").permitAll()
//                .antMatchers(ignorePropertiesConfig.getIgnoreUrls().toArray(new String[0])).permitAll()
                .anyRequest().access("#oauth2.hasScope('" + moduleName + "')")
                .and().formLogin().permitAll();
        ;

    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.tokenServices(tokenServices());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }


    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());

//        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
//        tokenStore.setPrefix(SecurityConstants.OAUTH_PREFIX);
//        return tokenStore;
    }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        converter.setAccessTokenConverter(new AllowedClockSkewAccessTokenConverter(allowedClockSkewSeconds));
        return converter;
    }
}
