package com.example.userservice.infrastructure.persistence.specification;

import com.example.userservice.infrastructure.persistence.entity.RoleEntity;
import com.example.userservice.infrastructure.persistence.entity.UserEntity;
import com.example.userservice.infrastructure.persistence.entity.UserRoleEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<UserEntity> withEmail(String email) {
        return (root, query, cb) ->
                email == null ? null :
                        cb.like(
                                cb.lower(root.get("email")),
                                "%" + email.toLowerCase() + "%"
                        );
    }

    public static Specification<UserEntity> withStatus(Enum<?> status) {
        return (root, query, cb) ->
                status == null ? null :
                        cb.equal(root.get("status"), status);
    }

    public static Specification<UserEntity> withRole(String role) {
        return (root, query, cb) -> {
            if (role == null) {
                return null;
            }
            Join<UserEntity, UserRoleEntity> userRoles = root.join("roles", JoinType.LEFT);
            Join<UserRoleEntity, RoleEntity> roleJoin = userRoles.join("role", JoinType.LEFT);
            return cb.equal(roleJoin.get("code"), role);
        };
    }
}

