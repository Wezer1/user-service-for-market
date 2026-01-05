package com.example.userservice;


import com.example.userservice.domain.role.RoleCode;
import com.example.userservice.domain.user.UserStatus;
import com.example.userservice.infrastructure.persistence.entity.RoleEntity;
import com.example.userservice.infrastructure.persistence.entity.UserEntity;
import com.example.userservice.infrastructure.persistence.entity.UserRoleEntity;
import com.example.userservice.infrastructure.persistence.repository.JpaRoleRepository;
import com.example.userservice.infrastructure.persistence.repository.JpaUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private JpaRoleRepository roleRepository;

    @AfterEach
    void cleanupUsers() {
        userRepository.deleteAll();
    }

    // ---------------------------------------
    // Тест регистрации пользователя
    // ---------------------------------------
    @Test
    void testRegisterUser() throws Exception {
        Map<String, String> request = Map.of(
                "email", "user@example.com",
                "password", "password123"
        );

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.roles[0]").value("CLIENT"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    // ---------------------------------------
    // Тест получения всех пользователей
    // ---------------------------------------
    @Test
    void testGetAllUsers() throws Exception {
        UserEntity user1 = new UserEntity(UUID.randomUUID(), "user1@example.com", "hash", UserStatus.ACTIVE, Instant.now());
        UserEntity user2 = new UserEntity(UUID.randomUUID(), "user2@example.com", "hash", UserStatus.BLOCKED, Instant.now());
        userRepository.saveAll(List.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // ---------------------------------------
    // Тест фильтрации по статусу
    // ---------------------------------------
    @Test
    void testGetUsersFilterByStatus() throws Exception {
        UserEntity activeUser = new UserEntity(UUID.randomUUID(), "active@example.com", "hash", UserStatus.ACTIVE, Instant.now());
        UserEntity blockedUser = new UserEntity(UUID.randomUUID(), "blocked@example.com", "hash", UserStatus.BLOCKED, Instant.now());
        userRepository.saveAll(List.of(activeUser, blockedUser));

        mockMvc.perform(get("/users")
                        .param("status", "ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].email").value("active@example.com"));
    }

    // ---------------------------------------
    // Тест фильтрации по email + статус
    // ---------------------------------------
    @Test
    void testGetUsersFilterByEmailAndStatus() throws Exception {
        UserEntity user1 = new UserEntity(UUID.randomUUID(), "user1@example.com", "hash", UserStatus.ACTIVE, Instant.now());
        UserEntity user2 = new UserEntity(UUID.randomUUID(), "user2@example.com", "hash", UserStatus.BLOCKED, Instant.now());
        userRepository.saveAll(List.of(user1, user2));

        mockMvc.perform(get("/users")
                        .param("status", "ACTIVE")
                        .param("email", "user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].email").value("user1@example.com"));
    }

    // ---------------------------------------
    // Тест фильтрации по роли
    // ---------------------------------------
    @Test
    void testGetUsersFilterByRole() throws Exception {
        // Берём существующие роли из JPA-базы
        RoleEntity clientRole = roleRepository.findByCode(RoleCode.CLIENT.name()).orElseThrow();
        RoleEntity adminRole = roleRepository.findByCode(RoleCode.ADMIN.name()).orElseThrow();

        UserEntity user1 = new UserEntity(UUID.randomUUID(), "user1@example.com", "hash", UserStatus.ACTIVE, Instant.now());
        user1.addRole(new UserRoleEntity(user1, clientRole));

        UserEntity user2 = new UserEntity(UUID.randomUUID(), "user2@example.com", "hash", UserStatus.ACTIVE, Instant.now());
        user2.addRole(new UserRoleEntity(user2, adminRole));

        userRepository.saveAll(List.of(user1, user2));

        mockMvc.perform(get("/users")
                        .param("role", "CLIENT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].email").value("user1@example.com"));
    }


}
