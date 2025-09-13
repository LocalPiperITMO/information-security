package itmo.localpiper.iblab1.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final Key key;
    private final long expirationMs;

    private JwtService(Key key, long expirationMs) {
        this.key = key;
        this.expirationMs = expirationMs;
    }

    public static JwtService create(String secret, long expirationMs) {
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("JWT secret cannot be null or empty");
        }

        Key key;
        try {
            key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create JWT key", e);
        }

        return new JwtService(key, expirationMs);
    }


    public String generateToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(now)
        .setExpiration(exp)
        .signWith(key)
        .compact();
    }

    public String validateAndGetUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException ex) {
            return null;
        }
    }
}
