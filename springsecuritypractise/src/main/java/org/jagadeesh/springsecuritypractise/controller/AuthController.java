package org.jagadeesh.springsecuritypractise.controller;

import org.jagadeesh.springsecuritypractise.dto.LoginRequest;
import org.jagadeesh.springsecuritypractise.dto.LoginResponse;
import org.jagadeesh.springsecuritypractise.security.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        if (request.username() == null
            || request.username().isBlank()
            || request.password() == null
            || request.password().isBlank()) {

            return ResponseEntity.badRequest()
                    .body("Username and password are required");
        }

        try {
            // Step 1: Authenticate the supplied credentials.
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );

            // Step 2: Load the authenticated user's details.
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(
                            request.username()
                    );

            // Step 3: Generate a JWT.
            String token = jwtService.generateToken(userDetails);

            // Step 4: Return the token.
            return ResponseEntity.ok(
                    new LoginResponse(
                            token,
                            "Bearer",
                            jwtService.getExpirationMs()
                    )
            );

        } catch (AuthenticationException exception) {

            // Don't reveal whether the username or password was wrong.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }
}