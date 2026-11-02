package org.jagadeesh.springsecuritypractise.dto;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresInMs
) {
}