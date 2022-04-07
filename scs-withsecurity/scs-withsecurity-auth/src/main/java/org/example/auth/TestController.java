package org.example.auth;

import org.example.auth.service.ScsClientDetailsService;
import org.example.auth.service.ScsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    ScsUserDetailsService scsUserDetailsService;

    @Autowired
    ScsClientDetailsService scsClientDetailsService;

    @GetMapping("/now")
    public String now(){
        return System.currentTimeMillis()+"";
    }


    @GetMapping("/user/{name}")
    public UserDetails user(@PathVariable("name")String name){
        return scsUserDetailsService.loadUserByUsername(name);
    }

    @GetMapping("/client/{name}")
    public ClientDetails client(@PathVariable("name")String name){
        return scsClientDetailsService.loadClientByClientId(name);
    }
}
