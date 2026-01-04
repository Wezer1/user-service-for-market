package com.example.userservice.infrastructure.persistence.mapper;

import com.example.userservice.domain.role.Role;
import com.example.userservice.domain.role.RoleCode;
import com.example.userservice.infrastructure.persistence.entity.RoleEntity;

public class RoleMapper {

    public static Role toDomain(RoleEntity entity) {
        return new Role(
                entity.getId(),
                RoleCode.valueOf(entity.getCode()),
                entity.getDescription()
        );
    }
}

