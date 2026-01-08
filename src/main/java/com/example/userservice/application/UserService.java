package com.example.userservice.application;

import com.example.userservice.application.command.RegisterUserCommand;
import com.example.userservice.application.filter.UserFilter;
import com.example.userservice.application.port.PasswordHasher;
import com.example.userservice.application.port.RoleRepository;
import com.example.userservice.application.port.UserRepository;
import com.example.userservice.domain.role.RoleCode;
import com.example.userservice.domain.user.User;
import com.example.userservice.domain.user.UserStatus;
import com.example.userservice.domain.user.UserValidator;
import com.example.userservice.domain.role.Role;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordHasher passwordHasher;
    private final UserValidator validator;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordHasher passwordHasher, UserValidator validator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordHasher = passwordHasher;
        this.validator = validator;
    }


    public User register(RegisterUserCommand command) {

        validator.validateForRegistration(command.email(), command.rawPassword());

        if (userRepository.existsByEmail(command.email())) {
            throw new IllegalStateException("User with this email already exists");
        }

        Role defaultRole = roleRepository.findByCode(RoleCode.CLIENT);

        User user = new User(
                UUID.randomUUID(),
                command.email(),
                passwordHasher.hash(command.rawPassword()),
                UserStatus.ACTIVE,
                Instant.now()
        );

        user.assignRole(defaultRole);

        return userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found: " + id));
    }

    public List<User> search(UserFilter filter) {
        return userRepository.findByFilter(filter);
    }
    public User updateUser(UUID userId, String newEmail, String newRawPassword) {

        if (newEmail == null && newRawPassword == null) {
            throw new IllegalArgumentException("Nothing to update");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found: " + userId));

        if (newEmail != null) {
            validator.validateEmail(newEmail);

            if (!newEmail.equals(user.getEmail())
                    && userRepository.existsByEmail(newEmail)) {
                throw new IllegalStateException("User with this email already exists");
            }

            user.changeEmail(newEmail);
        }

        if (newRawPassword != null) {
            validator.validatePassword(newRawPassword);

            String hashedPassword = passwordHasher.hash(newRawPassword);
            user.changePassword(hashedPassword);
        }

        return userRepository.save(user);
    }

    public User blockUser(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found: " + userId));

        if (user.getStatus() == UserStatus.BLOCKED) {
            throw new IllegalStateException("User is already blocked");
        }

        user.block();

        return userRepository.save(user);
    }

    public User unblockUser(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found: " + userId));

        if (user.getStatus() == UserStatus.ACTIVE) {
            throw new IllegalStateException("User is not blocked");
        }

        user.unblock();

        return userRepository.save(user);
    }

}
