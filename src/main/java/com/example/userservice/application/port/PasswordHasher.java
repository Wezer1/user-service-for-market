package com.example.userservice.application.port;

public interface PasswordHasher {
    String hash(String rawPassword);
}