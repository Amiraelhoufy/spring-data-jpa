package com.agcodes.spring_data_jpa.service;

import com.agcodes.spring_data_jpa.dto.EnrollmentRequest;
import com.agcodes.spring_data_jpa.model.Course;
import com.agcodes.spring_data_jpa.model.Enrollment;
import com.agcodes.spring_data_jpa.model.EnrollmentId;
import com.agcodes.spring_data_jpa.model.Student;
import com.agcodes.spring_data_jpa.repository.CourseRepository;
import com.agcodes.spring_data_jpa.repository.EnrollmentRepository;
import com.agcodes.spring_data_jpa.repository.StudentRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

  private final EnrollmentRepository enrollmentRepository;
  private final StudentRepository studentRepository;
  private final CourseRepository courseRepository;

  public Enrollment enrollStudentToCourse(EnrollmentRequest request) {
    Student student= studentRepository.findById(request.studentId())
        .orElseThrow(()-> new IllegalArgumentException("Student not found with id " + request.studentId()));

    Course course = courseRepository.findById(request.courseId())
        .orElseThrow(()-> new IllegalArgumentException("Course not found with id " + request.courseId()));

    EnrollmentId enrollmentId = new EnrollmentId(student.getId(), course.getId());
    Enrollment enrollment = new Enrollment(enrollmentId, student, course, LocalDateTime.now());
    return enrollmentRepository.save(enrollment);
  }

  public List<Enrollment> getAllEnrollments() {
    return enrollmentRepository.findAll();
  }

  public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
    return enrollmentRepository.findByStudent_Id(studentId);
  }

  public List<Enrollment> getEnrollmentsByCourseId(Long courseId) {
    return enrollmentRepository.findByCourse_Id(courseId);
  }
}
