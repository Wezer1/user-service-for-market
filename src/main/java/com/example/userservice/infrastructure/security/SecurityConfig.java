package com.example.userservice.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // отключаем CSRF для API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register").permitAll() // разрешаем регистрацию
                        .anyRequest().authenticated() // остальные эндпоинты требуют аутентификации
                )
                .httpBasic(); // можно оставить Basic для теста

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

