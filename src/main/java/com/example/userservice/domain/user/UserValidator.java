package com.example.userservice.domain.user;

public class UserValidator {
    public void validateForRegistration(String email, String rawPassword) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be empty");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email has invalid format");
        }

        if (rawPassword == null || rawPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
    }
}
