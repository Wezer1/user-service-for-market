package com.example.userservice.application.port;

import com.example.userservice.domain.user.User;

public interface UserRepository {

    User save(User user);

    boolean existsByEmail(String email);
}
