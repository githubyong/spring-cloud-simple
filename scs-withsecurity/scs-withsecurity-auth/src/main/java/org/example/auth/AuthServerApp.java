package org.example.auth;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@ComponentScan("org.example")
@EnableJdbcRepositories
@EnableDiscoveryClient
@SpringBootApplication
public class AuthServerApp  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        System.setProperty("authserver_self","true");
        SpringApplication.run(AuthServerApp.class, args);
    }

}
