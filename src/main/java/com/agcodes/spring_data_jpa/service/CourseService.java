package com.agcodes.spring_data_jpa.service;

import com.agcodes.spring_data_jpa.dto.CourseRequest;
import com.agcodes.spring_data_jpa.model.Course;
import com.agcodes.spring_data_jpa.repository.CourseRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

  private final CourseRepository courseRepository;

  public Course createCourse(CourseRequest courseRequest) {
    Course course = new Course();
    course.setName(courseRequest.name());
    course.setDepartment(courseRequest.department());
    return courseRepository.save(course);
  }

  public Optional<Course> getCourseById(Long id) {
    return courseRepository.findById(id);
  }


  public List<Course> getAllCourses() {
    return courseRepository.findAll();
  }

  public boolean deleteCourse(Long id) {
    if(courseRepository.existsById(id)){
      courseRepository.deleteById(id);
      return true;
    }
    return false;
  }
}
