package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.dtos.AuthResponseDto;
import com.example.demo.auth.dtos.SignInDto;
import com.example.demo.user.User;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  private AuthService authService;

  @PostMapping
  public ResponseEntity<AuthResponseDto> createAccount(@RequestBody User user) {
    return authService.createAccount(user);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<AuthResponseDto> signIn(@RequestBody SignInDto signInDto) {
    return authService.signIn(signInDto);
  }
}
