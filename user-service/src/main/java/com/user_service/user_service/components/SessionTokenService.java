package com.user_service.user_service.components;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class SessionTokenService {

    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("E93TfyoN8v4DKb6z8KnVw6vhLOn5IFqzyz3zVPF9wK8=".getBytes());

    public String generateSessionToken(String userEmail) {
        return Jwts.builder()
                .setSubject(userEmail)
                // .setClaims("admin", "user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 6400000))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}
// public Claims decodeToken(String token) {
//     return Jwts.parserBuilder()
//             .setSigningKey(SECRET_KEY)
//             .build()
//             .parseClaimsJws(token)
//             .getBody();
// }