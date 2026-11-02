package org.jagadeesh.springsecuritypractise.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Hello, everyone! This is a public API.";
    }

    @GetMapping("/user/profile")
    public String userProfile() {
        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        return "Welcome " + authentication.getName()
               + "! You have accessed the user profile.";
//        return "Welcome! You have accessed the user profile.";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        return "Welcome " + authentication.getName()
               + "! You have accessed the admin dashboard.";
//        return "Welcome to the admin dashboard!";
    }
}