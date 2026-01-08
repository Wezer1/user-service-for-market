package com.example.userservice.infrastructure.rest.mapper;

import com.example.userservice.domain.user.User;
import com.example.userservice.infrastructure.rest.dto.UserResponse;

import java.util.stream.Collectors;

public class UserResponseMapper {

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getStatus().name(),
                user.getRoles().stream()
                        .map(r -> r.getCode().name())
                        .collect(Collectors.toSet())
        );
    }
}
