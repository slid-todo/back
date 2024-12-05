package com.codeit.todo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/swagger/**").permitAll()
                    .requestMatchers("/**").permitAll()
                    .anyRequest().permitAll()
//                    .anyRequest().authenticated() // 인증 구현시, 주석 해제
            );
        return http.build();
    }
}
