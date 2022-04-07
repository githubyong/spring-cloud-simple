package org.example.scsmin;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.example.scs.common.MyAuthenticationManagerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableConfigServer
@EnableDiscoveryClient
@ComponentScan("org.example")
@SpringBootApplication
public class ConfApp extends WebSecurityConfigurerAdapter {

    @Autowired
    MyAuthenticationManagerBuilder confBuilder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        confBuilder.configure(auth);
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfApp.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}
