package com.agcodes.spring_data_jpa.controller;

import com.agcodes.spring_data_jpa.dto.StudentRequest;
import com.agcodes.spring_data_jpa.model.Student;
import com.agcodes.spring_data_jpa.service.StudentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  // Create Student
  @PostMapping
  public ResponseEntity<Student> createStudent(@RequestBody StudentRequest request){

    Student savedStudent = studentService.createStudent(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(savedStudent);
  }

  // Get All Students
  @GetMapping
  public ResponseEntity<List<Student>> getAllStudents(){
    return ResponseEntity.ok(studentService.getAllStudents());
  }

  // Get by ID
  @GetMapping("/{id}")
  public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
    return studentService.getStudentById(id)
        .map(ResponseEntity::ok) // 200 OK
        .orElse(ResponseEntity.notFound().build()); // 404
  }
  @DeleteMapping("/{id}")
  private ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
    boolean deleted = studentService.deleteStudent(id);
    return deleted ?
        ResponseEntity.noContent().build() :
        ResponseEntity.notFound().build();
  }
}
