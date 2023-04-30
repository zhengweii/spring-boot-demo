package com.example.demo.student;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
  private final StudentRepository studentRepository;

  @Autowired
  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public List<Student> getStudents() {
    return studentRepository.findAll();
  }

  public Optional<Student> getStudentByEmail(String email) {
    Optional<Student> student = studentRepository.findByEmail(email);
    return student;
  }

  public boolean updateStudentByEmail(String email, Student updatedStudent) {
    try {
      Optional<Student> student = this.studentRepository.findByEmail(email);
      if (student.isEmpty()) {
        throw new Exception("Student does not exist. Cannot be updated");
      }

      // Check if new email is already in use
      if (updatedStudent.getEmail() != email) {
        Optional<Student> studentTwo = this.studentRepository.findByEmail(updatedStudent.getEmail());
        if (studentTwo.isPresent()) {
          throw new Exception("New email is already taken. Please choose another email");
        }
      }

      student.get().setName(updatedStudent.getName());
      student.get().setEmail(updatedStudent.getEmail());
      student.get().setFavouriteSubjects(updatedStudent.getFavouriteSubjects());

      studentRepository.save(student.get());

      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  public boolean deleteStudentByEmail(String email) {
    try {
      Optional<Student> student = this.studentRepository.findByEmail(email);
      if (student.isEmpty()) {
        throw new Exception("Student does not exist. Cannot be deleted");
      }

      studentRepository.deleteByEmail(email);
      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  public boolean createStudent(Student student) {
    try {
      this.studentRepository.save(student);
      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }
}
