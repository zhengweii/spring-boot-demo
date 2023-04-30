package com.example.demo.student;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
  public Optional<Student> findByEmail(String email);

  public Student deleteByEmail(String email);
}
