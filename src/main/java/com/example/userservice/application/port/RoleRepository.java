package com.example.userservice.application.port;

import com.example.userservice.domain.role.RoleCode;

import javax.management.relation.Role;

public interface RoleRepository {

    Role findByCode(RoleCode code);
}
