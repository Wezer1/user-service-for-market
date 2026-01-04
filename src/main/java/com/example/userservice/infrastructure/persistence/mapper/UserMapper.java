package com.example.userservice.infrastructure.persistence.mapper;

import com.example.userservice.domain.role.Role;
import com.example.userservice.domain.user.User;
import com.example.userservice.domain.user.UserStatus;
import com.example.userservice.infrastructure.persistence.entity.UserEntity;
import com.example.userservice.infrastructure.persistence.entity.UserRoleEntity;

import java.util.stream.Collectors;

public class UserMapper {

    public static User toDomain(UserEntity entity) {

        User user = new User(
                entity.getId(),
                entity.getEmail(),
                entity.getPasswordHash(),
                UserStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt()
        );

        for (UserRoleEntity userRole : entity.getRoles()) {
            Role role = RoleMapper.toDomain(userRole.getRole());
            user.assignRole(role);
        }

        return user;
    }
}

