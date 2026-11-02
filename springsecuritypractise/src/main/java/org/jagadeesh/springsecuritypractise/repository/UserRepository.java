package org.jagadeesh.springsecuritypractise.repository;

import org.jagadeesh.springsecuritypractise.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}