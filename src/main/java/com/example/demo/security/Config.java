package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.user.UserRepository;

@Configuration
public class Config {
  private final UserRepository userRepository;

  @Autowired
  public Config(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // This implementation will be used instead of the default implementation
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      return userRepository.findByEmail(username).orElseThrow(
          () -> new UsernameNotFoundException("User not found with email " + username + " when loading by username"));
    };
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
