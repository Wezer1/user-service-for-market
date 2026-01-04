package com.example.userservice.infrastructure.persistence.adapter;

import com.example.userservice.application.port.UserRepository;
import com.example.userservice.domain.user.User;
import com.example.userservice.infrastructure.persistence.entity.UserEntity;
import com.example.userservice.infrastructure.persistence.entity.UserRoleEntity;
import com.example.userservice.infrastructure.persistence.mapper.UserMapper;
import com.example.userservice.infrastructure.persistence.repository.JpaRoleRepository;
import com.example.userservice.infrastructure.persistence.repository.JpaUserRepository;

import java.util.UUID;

public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository userRepository;
    private final JpaRoleRepository roleRepository;

    public UserRepositoryAdapter(JpaUserRepository userRepository,
                                 JpaRoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User save(User user) {

        UserEntity entity = new UserEntity(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getStatus().name(),
                user.getCreatedAt()
        );

        user.getRoles().forEach(role -> {
            var roleEntity = roleRepository.findById(role.getId()).orElseThrow();
            entity.addRole(new UserRoleEntity(entity, roleEntity));
        });

        return UserMapper.toDomain(userRepository.save(entity));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
