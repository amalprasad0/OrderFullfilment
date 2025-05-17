package com.product_service.product_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SessionTokenService {
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("E93TfyoN8v4DKb6z8KnVw6vhLOn5IFqzyz3zVPF9wK8=".getBytes());
    
    
    
  
    public Claims decodeToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
   
    public boolean isTokenValid(String token) {
        try {
            Claims claims = decodeToken(token);
            Date expirationDate = claims.getExpiration();
            return !expirationDate.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public String getUserEmailFromToken(String token) {
        Claims claims = decodeToken(token);
        return claims.getSubject();
    }
}