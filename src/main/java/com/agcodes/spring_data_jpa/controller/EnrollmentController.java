package com.agcodes.spring_data_jpa.controller;

import com.agcodes.spring_data_jpa.dto.EnrollmentRequest;
import com.agcodes.spring_data_jpa.model.Enrollment;
import com.agcodes.spring_data_jpa.service.EnrollmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

  private final EnrollmentService enrollmentService;

  @PostMapping
  public ResponseEntity<Enrollment> createEnrollment(EnrollmentRequest request) {

    Enrollment enrollment = enrollmentService.enrollStudentToCourse(request);
    return ResponseEntity.ok(enrollment);
  }

  @GetMapping
  public ResponseEntity<List<Enrollment>> getAllEnrollments() {
    return ResponseEntity.ok(enrollmentService.getAllEnrollments());
  }

  @GetMapping("student/{studentId}")
  public ResponseEntity<List<Enrollment>> getEnrollmentsByStudentId(@PathVariable Long studentId) {
    return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudentId(studentId));
  }

  @GetMapping("course/{courseId}")
  public ResponseEntity<List<Enrollment>> getEnrollmentsByCourseId(@PathVariable Long courseId) {
    return ResponseEntity.ok(enrollmentService.getEnrollmentsByCourseId(courseId));
  }
}
