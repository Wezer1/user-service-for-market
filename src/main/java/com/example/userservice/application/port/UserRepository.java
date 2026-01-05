package com.example.userservice.application.port;

import com.example.userservice.application.filter.UserFilter;
import com.example.userservice.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    boolean existsByEmail(String email);

    Optional<User> findById(UUID id);

    List<User> findAll();

    List<User> findByFilter(UserFilter filter);
}
