package com.example.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public boolean deleteUserByEmail(String email) {
    try {
      Optional<User> user = userRepository.findByEmail(email);

      if (user.isPresent()) {
        userRepository.delete(user.get());
      } else {
        throw new Exception("User does not exist. Cannot be deleted");
      }

      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }
}
