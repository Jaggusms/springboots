package org.jagadeesh.springsecuritypractise.dto;

public record LoginRequest(
        String username,
        String password
) {
}