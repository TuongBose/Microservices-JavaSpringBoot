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
                        // 👉 CHO PHÉP TRUY CẬP /actuator/prometheus mà không cần token
                        .requestMatchers("/actuator/prometheus").permitAll()

                        // 👉 Tùy theo bạn có expose thêm gì nữa
                        .requestMatchers("/actuator/**").permitAll()

                        // 👉 Các API khác yêu cầu xác thực (có token Keycloak)
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(resource -> resource
                        .jwt(Customizer.withDefaults()) // dùng JWT để xác thực token từ Keycloak
                )
                .csrf(AbstractHttpConfigurer::disable); // Tắt CSRF cho Prometheus (chỉ GET)

        return http.build();
    }
}
