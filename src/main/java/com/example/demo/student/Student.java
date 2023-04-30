package com.example.demo.student;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Student {
  @Id
  private String id;
  private String name;
  @Indexed(unique = true)
  private String email;
  private List<String> favouriteSubjects;

  public Student(String name, String email, List<String> favouriteSubjects) {
    super();
    this.name = name;
    this.email = email;
    this.favouriteSubjects = favouriteSubjects;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public List<String> getFavouriteSubjects() {
    return favouriteSubjects;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setFavouriteSubjects(List<String> favouriteSubjects) {
    this.favouriteSubjects = favouriteSubjects;
  }

  @Override
  public String toString() {
    return "name: " + name + "\n" + "email: " + email;
  }
}
