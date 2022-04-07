package org.example.auth.service;

import org.example.auth.dao.UserRepository;
import org.example.auth.model.ScsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScsUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<ScsUser> scsUsers =  userRepository.findByUsername(username);
        if(scsUsers.size()==0){
            throw new UsernameNotFoundException(String.format("user %s not found", username));
        }
        ScsUser scsUser = scsUsers.get(0);
        return new User(scsUser.getUsername(),scsUser.getPassword(),new ArrayList<>());
    }
}
