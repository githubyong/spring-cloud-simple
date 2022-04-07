package org.example.scsmin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@RibbonClients(defaultConfiguration = RibbonEurekaClientConfig.class)
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("org.example")
@EnableZuulProxy
public class GateApp {


    public static void main(String[] args) {
        SpringApplication.run(GateApp.class);
    }

}
