package com.example.userservice.infrastructure.persistence.entity;

import java.io.Serializable;
import java.util.UUID;

public class UserRoleId implements Serializable {

    private UUID user;
    private UUID role;

    protected UserRoleId() {
    }
}

