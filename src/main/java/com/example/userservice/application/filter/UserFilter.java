package com.example.userservice.application.filter;

import com.example.userservice.domain.user.UserStatus;

public class UserFilter {

    private final String email;
    private final UserStatus status;
    private final String role;

    public UserFilter(String email, UserStatus status, String role) {
        this.email = email;
        this.status = status;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }
}

