package com.example.userservice.infrastructure.rest.dto;

import java.util.Set;
import java.util.UUID;

public class UserResponse {

    private UUID id;
    private String email;
    private String status;
    private Set<String> roles;

    public UserResponse(UUID id, String email, String status, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.status = status;
        this.roles = roles;
    }

    // getters

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
