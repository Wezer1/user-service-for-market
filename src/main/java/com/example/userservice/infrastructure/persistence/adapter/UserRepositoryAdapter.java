package com.example.userservice.infrastructure.persistence.adapter;

import com.example.userservice.application.filter.UserFilter;
import com.example.userservice.application.port.UserRepository;
import com.example.userservice.domain.user.User;
import com.example.userservice.infrastructure.persistence.entity.UserEntity;
import com.example.userservice.infrastructure.persistence.entity.UserRoleEntity;
import com.example.userservice.infrastructure.persistence.mapper.UserMapper;
import com.example.userservice.infrastructure.persistence.repository.JpaRoleRepository;
import com.example.userservice.infrastructure.persistence.repository.JpaUserRepository;
import com.example.userservice.infrastructure.persistence.specification.UserSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
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
                user.getStatus(),
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

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    @Override
    public List<User> findByFilter(UserFilter filter) {

        Specification<UserEntity> spec = Specification
                .where(UserSpecification.withEmail(filter.getEmail()))
                .and(UserSpecification.withStatus(filter.getStatus()))
                .and(UserSpecification.withRole(filter.getRole()));

        return userRepository.findAll(spec)
                .stream()
                .map(UserMapper::toDomain)
                .toList();
    }

}
