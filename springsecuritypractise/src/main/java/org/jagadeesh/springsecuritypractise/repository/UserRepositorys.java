package com.example.demo.user.repository;

import com.example.demo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositorys extends JpaRepository<User, Long> {
}