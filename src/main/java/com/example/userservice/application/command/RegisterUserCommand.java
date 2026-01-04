package com.example.userservice.application.command;

public record RegisterUserCommand(
        String email,
        String rawPassword
) {}
