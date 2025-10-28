package com.agcodes.spring_data_jpa.repository;

import com.agcodes.spring_data_jpa.model.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
