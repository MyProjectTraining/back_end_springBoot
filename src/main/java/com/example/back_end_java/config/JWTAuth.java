package com.example.back_end_java.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTAuth {
    private final String keyToken = System.getenv("KEY_TOKEN");
    private final long exp = 1000 * 60 * 60 ;

    private Key getSignInByte(){
        byte[] keyBytes = keyToken.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email){
        Map<String , Object> claims = new HashMap<>();
        claims.put("role" , "admin");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .signWith(getSignInByte() , SignatureAlgorithm.HS256)
                .compact();
    }
}
