package com.example.userservice.domain.role;

import java.util.UUID;

public class Role {
    private final UUID id;
    private final RoleCode code;
    private final String description;

    public Role(UUID id, RoleCode code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public RoleCode getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
