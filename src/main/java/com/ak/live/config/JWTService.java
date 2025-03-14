package com.ak.live.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import java.security.Key;

public class JWTService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()  // Use parser() for JJWT 0.9.1
                .setSigningKey(getSignKey())  // Set the signing key
                .parseClaimsJws(token)  // Parse the JWT
                .getBody();  // Get the claims from the token
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            // Ensure you have a logger initialized
            System.err.println("Invalid or expired JWT token: " + e.getMessage());
            return false;
        }
    }

    public String generateToken(String userName) {
        return createToken(new HashMap<>(), userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, getSignKey())  // Sign with the HS256 algorithm
                .compact();  // Return the compact token
    }

    private Key getSignKey() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);  
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");  
            return secretKeySpec;  
        } catch (Exception e) {
            throw new RuntimeException("Error while generating the signing key", e);
        }
    }
}
