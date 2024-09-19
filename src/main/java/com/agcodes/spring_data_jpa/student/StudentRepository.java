package com.agcodes.spring_data_jpa.student;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

  @Query("SELECT s FROM Student s WHERE s.email =?1")
  Optional<Student> findStudentByEmail(String email);

  List<Student>findStudentsByFirstNameEqualsAndAgeEquals(String firstName,Integer age);

  @Query("SELECT s FROM Student s WHERE s.firstName =?1 and s.age=?2")
  List<Student> selectStudentWhereFirstNameAndAgeEquals(String firstName,Integer age);

  @Query(value = "SELECT * FROM student WHERE first_name =?1 and age=?2",nativeQuery = true)
  List<Student> selectStudentWhereFirstNameAndAgeEqualsNative(String firstName,Integer age);
  @Query(value = "SELECT * FROM student WHERE first_name =:firstName and age>:age",nativeQuery = true)
  List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThan(
      @Param("firstName") String firstName,
      @Param("age") Integer age);

  List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual(String firstName,Integer age);
  @Transactional
  @Modifying
  @Query("DELETE FROM Student s WHERE s.id = :id")
  int deleteStudentById(@Param("id") Long id);


}
