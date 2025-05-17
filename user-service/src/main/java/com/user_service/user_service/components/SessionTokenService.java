package com.user_service.user_service.components;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SessionTokenService {
    
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("E93TfyoN8v4DKb6z8KnVw6vhLOn5IFqzyz3zVPF9wK8=".getBytes());
    
    public String generateSessionToken(String userEmail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "user");  
        
        return Jwts.builder()
                .setClaims(claims)  
                .setSubject(userEmail) 
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 6400000))  
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String generateSessionToken(String userEmail, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 6400000))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}