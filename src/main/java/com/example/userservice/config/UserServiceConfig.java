package com.example.userservice.config;

import com.example.userservice.application.UserService;
import com.example.userservice.application.port.PasswordHasher;
import com.example.userservice.application.port.RoleRepository;
import com.example.userservice.application.port.UserRepository;
import com.example.userservice.domain.user.UserValidator;
import com.example.userservice.infrastructure.persistence.adapter.RoleRepositoryAdapter;
import com.example.userservice.infrastructure.persistence.adapter.UserRepositoryAdapter;
import com.example.userservice.infrastructure.persistence.repository.JpaRoleRepository;
import com.example.userservice.infrastructure.persistence.repository.JpaUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfig {

    @Bean
    public RoleRepository roleRepository(JpaRoleRepository jpaRoleRepository) {
        return new RoleRepositoryAdapter(jpaRoleRepository);
    }

    @Bean
    public UserRepository userRepository(JpaUserRepository jpaUserRepository,
                                         JpaRoleRepository jpaRoleRepository) {
        return new UserRepositoryAdapter(jpaUserRepository, jpaRoleRepository);
    }

    @Bean
    public UserValidator userValidator() {
        return new UserValidator();
    }

    @Bean
    public UserService userService(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   PasswordHasher passwordHasher,
                                   UserValidator userValidator) {
        return new UserService(userRepository, roleRepository, passwordHasher, userValidator);
    }
}
