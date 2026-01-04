package com.example.userservice.infrastructure.rest.dto;

public class RegisterUserRequest {

    private String email;
    private String password;

    public RegisterUserRequest() {
    }

    public RegisterUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // getters and setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
