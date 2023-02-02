package com.google.userKeep.userKeep.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return  new BCryptPasswordEncoder();
//    }
    @Bean
    public UserDetailsService userDetailsService(){
//        UserDetails normalUser = User.withUsername("rosan")
//                .password(passwordEncoder().encode("rosan"))
//                .roles("NORMAL")
//                .build();
//        UserDetails adminUser = User.withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
        logger.error("in the security config");
        return new CustomUserDetailsService();

    }
//    @Bean
//    public UserDetailsService userDetailsService(){
////        UserDetails normalUser = User.withUsername("rosan")
////                .password(passwordEncoder().encode("rosan"))
////                .roles("NORMAL")
////                .build();
////        UserDetails adminUser = User.withUsername("admin")
////                .password(passwordEncoder().encode("admin"))
////                .roles("ADMIN")
////                .build();
////        return new InMemoryUserDetailsManager(normalUser,adminUser);
//        return new CustomUserDetailsService();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")
                .requestMatchers("/public/**")
                .permitAll()
                .and()
                .formLogin();
        return httpSecurity. build();
    }
}
