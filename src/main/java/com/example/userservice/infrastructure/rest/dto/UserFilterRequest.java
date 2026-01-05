package com.example.userservice.infrastructure.rest.dto;

import com.example.userservice.domain.user.UserStatus;

public class UserFilterRequest {

    private String email;
    private UserStatus status;
    private String role;

    public String getEmail() {
        return email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
