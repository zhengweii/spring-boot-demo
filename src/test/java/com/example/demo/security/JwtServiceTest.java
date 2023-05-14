package com.example.demo.security;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.user.Role;
import com.example.demo.user.User;

@SpringBootTest
public class JwtServiceTest {
  private final JwtService jwtService;

  @Autowired
  public JwtServiceTest(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Test
  public void extractEmailSubject() {
    User mockUser = new User("test", "test@email.com", "password", Role.ADMIN);
    String mockToken = jwtService.generateToken(mockUser);

    assertEquals(jwtService.extractEmailSubject(mockToken), "test@email.com");
  }

  @Test
  public void isTokenValid() {
    User mockUser = new User("test", "test@email.com", "password", Role.ADMIN);

    assertTrue(jwtService.isTokenValid(jwtService.generateToken(mockUser), mockUser));

    HashMap<String, Object> m = new HashMap<>();
    m.put("exp", 1672502400);
    assertFalse(jwtService.isTokenValid(jwtService.generateToken(m, mockUser), mockUser));
  }
}
