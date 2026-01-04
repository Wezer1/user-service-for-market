package com.example.userservice.application.port;

import com.example.userservice.domain.role.RoleCode;

import com.example.userservice.domain.role.Role;

public interface RoleRepository {

    Role findByCode(RoleCode code);
}
