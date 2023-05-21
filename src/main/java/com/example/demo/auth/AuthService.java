package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.auth.dtos.AuthResponseDto;
import com.example.demo.auth.dtos.SignInDto;
import com.example.demo.security.JwtService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@Service
public class AuthService {
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  public ResponseEntity<AuthResponseDto> createAccount(User user) {
    try {
      // Encodes the password before saving
      user.setPassword(passwordEncoder.encode(user.getPassword()));

      // An error is thrown automatically if email is not unique
      userRepository.save(user);
      String token = jwtService.generateToken(user);

      return ResponseEntity.ok().body(new AuthResponseDto(token));
    } catch (Exception e) {
      System.out.println(e);
      return ResponseEntity.badRequest().build();
    }
  }

  public ResponseEntity<AuthResponseDto> signIn(SignInDto signInDto) {
    try {
      Authentication authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(signInDto.getEmail(),
              signInDto.getPassword()));

      String token = jwtService.generateToken((User) authentication.getPrincipal());
      return ResponseEntity.ok().body(new AuthResponseDto(token));
    } catch (Exception e) {
      System.out.println(e);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
