package com.project.user_serivce.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
//    For jwt
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth->auth
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .anyRequest().authenticated()
//                ).build();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

//    For Prometheus & Grafana
     @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Allow get /actuator/prometheus don't need token
                        .requestMatchers("/actuator/prometheus").permitAll()

                        // Allow all actuator endpoints (like /actuator/health, /actuator/info) don't need token
                        .requestMatchers("/actuator/**").permitAll()

                        // APIs that require authentication
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(resource -> resource
                        .jwt(Customizer.withDefaults()) // use JWT for authentication from Keycloak
                )
                .csrf(AbstractHttpConfigurer::disable); // Turn off CSRF for prometheus (only GET methods)

        return http.build();
    }
}
