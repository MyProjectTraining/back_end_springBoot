package com.example.back_end_java.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTAuth {

    private final SecretKey signInKey;

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
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + exp))
                .signWith(signInKey)
                .compact();
    }

    public String authentification(String token){

        Claims claims = Jwts.parser()
                .verifyWith(signInKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
}
