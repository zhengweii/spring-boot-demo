package com.example.demo.user;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document
public class User implements UserDetails {
  @Id
  private String id;
  private String name;
  @Indexed(unique = true)
  private String email;
  private String password;
  private Role role;

  public User(String name, String email, String password, Role role) {
    super();
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  public Role getRole() {
    return role;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "name: " + name + "\n" + "email: " + email + "\n" + "password: " + password + "\n" + "role: " + role;
  }

  // When disabled, users are not allowed access even if their credentials are
  // correct
  @Override
  public boolean isEnabled() {
    return true;
  }

  // Indicates whether a user's credentials are still valid or not
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.toString()));
  }

  // If false, it means the user's account has expired and they should not be
  // allowed to log in
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  // If false, account is locked and user should not be allowed to log in
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
}
