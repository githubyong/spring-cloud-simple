package org.example.scs.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationManagerBuilder {

    @Autowired
    SecurityProperties securityProperties;

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        SecurityProperties.User user = securityProperties.getUser();
        auth.inMemoryAuthentication().passwordEncoder(encoder)
                .withUser(user.getName()).password(encoder.encode(user.getPassword())).roles(user.getRoles().toArray(new String[0]));
    }

}
