package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
  private final String SECRET_KEY;

  public JwtService(@Value("${SECRET_KEY}") String SECRET_KEY) {
    this.SECRET_KEY = SECRET_KEY;
  }

  public String extractEmailSubject(String token) {
    return getClaims(token).getSubject();
  }

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setClaims(claims)
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    try {
      // parseClaimsJws() will automatically throw an error if the JWT has already
      // expired. No need to check manually
      String email = extractEmailSubject(token);
      return email.equals(userDetails.getUsername());
    } catch (ExpiredJwtException e) {
      return false;
    }
  }

  private Claims getClaims(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      throw e;
    }
  }

  private Key getSigningKey() {
    byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
