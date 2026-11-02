package org.jagadeesh.springsecuritypractise.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long expirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms:900000}") long expirationMs) {

        this.signingKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );

        this.expirationMs = expirationMs;
    }

    // Generate a signed JWT for the authenticated user.
    public String generateToken(UserDetails userDetails) {

        Date issuedAt = new Date();
        Date expiration = new Date(
                issuedAt.getTime() + expirationMs
        );

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    // Extract the username stored in the token's subject.
    public String extractUsername(String token) {

        return extractClaims(token).getSubject();
    }

    // Verify the token's signature and expiration,
    // then check that it belongs to the expected user.
    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        Claims claims = extractClaims(token);

        String username = claims.getSubject();
        Date expiration = claims.getExpiration();

        return username != null
               && username.equals(userDetails.getUsername())
               && expiration != null
               && expiration.after(new Date());
    }

    // Parse the JWT and verify its signature.
    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long getExpirationMs() {
        return expirationMs;
    }
}