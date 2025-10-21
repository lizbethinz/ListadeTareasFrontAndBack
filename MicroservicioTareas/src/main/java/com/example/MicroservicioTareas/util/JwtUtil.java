package com.example.MicroservicioTareas.util;

import java.util.HashMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwt.secret:clavesecret4jtwtdebeteneralmenos44caracteres}")
    private String SECRET_KEY;
    //tendrá 10 horas de validez
    public static final long JWT_TOKEN_VALIDITY = 10 * 60 * 60; 


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                                  .setSigningKey(getSignInKey())
                                  .build()
                                  .parseClaimsJws(token)
                                  .getBody();
        return claimsResolver.apply(claims);
    }


    //Extraer el Username (Subject)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    //Generar el Token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) // 10h
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    //Validar el Token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    private Boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
