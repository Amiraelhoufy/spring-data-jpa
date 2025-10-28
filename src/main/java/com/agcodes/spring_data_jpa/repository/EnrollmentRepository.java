package com.agcodes.spring_data_jpa.repository;

import com.agcodes.spring_data_jpa.model.Enrollment;
import com.agcodes.spring_data_jpa.model.EnrollmentId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

  List<Enrollment> findByStudent_Id(Long studentId); // _ separates object and field — it’s like dot notation (student.id)
  List<Enrollment> findByCourse_Id(Long courseId); // _ separates object and field — it’s like dot notation (course.id)

}
