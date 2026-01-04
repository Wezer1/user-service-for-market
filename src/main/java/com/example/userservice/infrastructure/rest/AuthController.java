package com.example.userservice.infrastructure.rest;

import com.example.userservice.application.UserService;
import com.example.userservice.application.command.RegisterUserCommand;
import com.example.userservice.domain.user.User;
import com.example.userservice.infrastructure.rest.dto.RegisterUserRequest;
import com.example.userservice.infrastructure.rest.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterUserRequest request) {

        RegisterUserCommand command = new RegisterUserCommand(
                request.getEmail(),
                request.getPassword()
        );

        User user = userService.register(command);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getStatus().name(),
                user.getRoles().stream().map(r -> r.getCode().name()).collect(Collectors.toSet())
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
