package com.google.userKeep.userKeep.config;

import com.google.userKeep.userKeep.model.UserModel;
import com.google.userKeep.userKeep.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        logger.error("in custom user details service");
        Optional<UserModel> userOptional = userRepository.findByName(name);
        logger.error("got the data");
        return userOptional.map(CustomUserService :: new).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
