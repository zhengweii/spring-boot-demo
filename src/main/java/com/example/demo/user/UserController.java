package com.example.demo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  @Secured("ADMIN")
  public List<User> getUsers() {
    return userService.getUsers();
  }

  @Secured("ADMIN")
  @DeleteMapping("/{email}")
  public boolean deleteUserByEmail(@PathVariable String email) {
    return userService.deleteUserByEmail(email);
  }
}
