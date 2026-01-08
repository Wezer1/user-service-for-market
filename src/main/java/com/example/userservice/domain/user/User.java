package com.example.userservice.domain.user;

import com.example.userservice.domain.role.Role;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User {
    private final UUID id;
    private String email;
    private String passwordHash;
    private UserStatus status;
    private final Instant createdAt;

    private final Set<Role> roles = new HashSet<>();

    public User(UUID id, String email, String passwordHash, UserStatus status, Instant createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void block() {
        if (this.status == UserStatus.BLOCKED) {
            throw new IllegalStateException("User already blocked");
        }
        this.status = UserStatus.BLOCKED;
    }

    public void unblock() {
        if (this.status == UserStatus.ACTIVE) {
            throw new IllegalStateException("User is not blocked");
        }
        this.status = UserStatus.ACTIVE;
    }


    public void assignRole(Role role) {
        roles.add(role);
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public void changePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Set<Role> getRoles() {
        return Set.copyOf(roles);
    }
}