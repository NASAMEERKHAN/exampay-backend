package com.sameer.exampay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig {

    @Bean
    public UserDetailsService users() {

        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        UserDetails student = User.withUsername("student")
                .password("{noop}student123")
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(admin, student);
    }
}