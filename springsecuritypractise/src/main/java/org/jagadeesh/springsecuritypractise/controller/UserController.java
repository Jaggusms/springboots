package org.jagadeesh.springsecuritypractise.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class UserController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Hello! This is a public API.";
    }

    @GetMapping("/user/profile")
    public Map<String, Object> userProfile(
            Authentication authentication) {

        return Map.of(
                "message", "You accessed a protected API",
                "username", authentication.getName(),
                "authorities", authentication.getAuthorities()
                        .stream()
                        .map(authority -> authority.getAuthority())
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "Welcome to the admin dashboard!";
    }
}