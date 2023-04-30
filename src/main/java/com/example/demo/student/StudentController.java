package com.example.demo.student;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {
  private final StudentService studentService;

  @Autowired
  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping()
  public List<Student> getStudents() {
    return this.studentService.getStudents();
  }

  @GetMapping("/{email}")
  public Optional<Student> getStudentByEmail(@PathVariable String email) {
    return this.studentService.getStudentByEmail(email);
  }

  @PostMapping()
  public boolean createStudent(@RequestBody Student student) {
    return this.studentService.createStudent(student);
  }

  @PutMapping("/{email}")
  public boolean updateStudentByEmail(@PathVariable String email, @RequestBody Student student) {
    return this.studentService.updateStudentByEmail(email, student);
  }

  @DeleteMapping("/{email}")
  public boolean deleteStudentByEmail(@PathVariable String email) {
    return this.studentService.deleteStudentByEmail(email);
  }
}
