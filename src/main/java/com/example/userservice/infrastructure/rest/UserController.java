package com.example.userservice.infrastructure.rest;

import com.example.userservice.application.UserService;
import com.example.userservice.application.filter.UserFilter;
import com.example.userservice.domain.user.User;
import com.example.userservice.infrastructure.rest.dto.UpdateUserRequest;
import com.example.userservice.infrastructure.rest.dto.UserFilterRequest;
import com.example.userservice.infrastructure.rest.dto.UserResponse;
import com.example.userservice.infrastructure.rest.mapper.UserResponseMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        User user = userService.findById(id);

        return UserResponseMapper.toResponse(userService.findById(id));
    }

    @GetMapping()
    public List<UserResponse> searchUsers(@ModelAttribute UserFilterRequest request) {

        System.out.println("STATUS = " + request.getStatus());
        System.out.println("ROLE = " + request.getRole());
        System.out.println("EMAIL = " + request.getEmail());

        UserFilter filter = new UserFilter(
                request.getEmail(),
                request.getStatus(),
                request.getRole()
        );

        return userService.search(filter)
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getStatus().name(),
                        user.getRoles().stream()
                                .map(r -> r.getCode().name())
                                .collect(Collectors.toSet())
                ))
                .toList();
    }

    @PatchMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable UUID id,
            @RequestBody UpdateUserRequest request
    ) {
        User user = userService.updateUser(id, request.getEmail(), request.getPassword());

        return UserResponseMapper.toResponse(userService.findById(id));
    }
    @PostMapping("/{id}/block")
    public UserResponse blockUser(@PathVariable UUID id) {
        userService.blockUser(id);

        return UserResponseMapper.toResponse(userService.findById(id));
    }

    @PostMapping("/{id}/unblock")
    public UserResponse unblockUser(@PathVariable UUID id) {
        userService.unblockUser(id);

        return UserResponseMapper.toResponse(userService.findById(id));
    }
}

