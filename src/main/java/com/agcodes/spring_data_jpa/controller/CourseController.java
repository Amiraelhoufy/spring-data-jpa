package com.agcodes.spring_data_jpa.controller;

import com.agcodes.spring_data_jpa.dto.CourseRequest;
import com.agcodes.spring_data_jpa.model.Course;
import com.agcodes.spring_data_jpa.model.Student;
import com.agcodes.spring_data_jpa.service.CourseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

  private final CourseService courseService;

  @PostMapping
  public ResponseEntity<Course> createCourse(@RequestBody CourseRequest courseRequest) {
    Course createdCourse = courseService.createCourse(courseRequest);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(createdCourse);
  }

  @GetMapping
  public ResponseEntity<List<Course>> getAllCourses() {
    return ResponseEntity.ok(courseService.getAllCourses());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
    return courseService.getCourseById(id)
        .map(ResponseEntity::ok) // 200 OK
        .orElse(ResponseEntity.notFound().build()); // 404
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
    boolean deleted = courseService.deleteCourse(id);
    return deleted ?
        ResponseEntity.noContent().build() :
        ResponseEntity.notFound().build();
  }
}