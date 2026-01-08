package com.example.userservice.infrastructure.rest.dto;

public class UpdateUserRequest {

    private String email;
    private String password;

    public UpdateUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
