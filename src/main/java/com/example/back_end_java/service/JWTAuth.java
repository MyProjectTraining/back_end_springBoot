package com.example.back_end_java.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTAuth {

    private final Key signInKey;

    public JWTAuth(@Value("${KEY_TOKEN}") String keyToken) {
        if (keyToken == null || keyToken.isBlank()){
            throw new IllegalStateException("token null");
        }
        System.out.println("KEY chargée au démarrage : " + keyToken);
        this.signInKey = Keys.hmacShaKeyFor(keyToken.getBytes(StandardCharsets.UTF_8));
    }


    public final long exp = 1000 * 60 * 60 ;

    public String generateToken(String email){

        Map<String , Object> claims = new HashMap<>();
        claims.put("role" , "admin");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .signWith(signInKey , SignatureAlgorithm.HS256)
                .compact();
    }

    public String authentification(String token){

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signInKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
