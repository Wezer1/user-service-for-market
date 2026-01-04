package com.example.userservice.infrastructure.persistence.adapter;

import com.example.userservice.application.port.RoleRepository;
import com.example.userservice.domain.role.Role;
import com.example.userservice.domain.role.RoleCode;
import com.example.userservice.infrastructure.persistence.mapper.RoleMapper;
import com.example.userservice.infrastructure.persistence.repository.JpaRoleRepository;

public class RoleRepositoryAdapter implements RoleRepository {

    private final JpaRoleRepository repository;

    public RoleRepositoryAdapter(JpaRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role findByCode(RoleCode code) {
        return repository.findByCode(code.name())
                .map(RoleMapper::toDomain)
                .orElseThrow(() -> new IllegalStateException("Role not found: " + code));
    }
}
