package com.agcodes.spring_data_jpa.model;

import static jakarta.persistence.GenerationType.SEQUENCE;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name ="Course")
@Table(name = "course")
public class Course {

  @Id
  @SequenceGenerator(
      name = "course_sequence",
      sequenceName = "course_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = SEQUENCE,
      generator = "course_sequence"
  )
  @Column(
      name = "id",
      updatable = false
  )
  private Long id;

  @Column(
      name = "name",
      nullable = false,
      columnDefinition = "TEXT"
  )
  private String name;

  @Column(
      name = "department",
      nullable = false,
      columnDefinition = "TEXT"
  )
  private String department;

//  @ManyToMany(
//      mappedBy = "courses"
//  )
//  private List<Student> students = new ArrayList<>();

  @OneToMany(
      cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
      mappedBy = "course"
  )
  @JsonBackReference              // 👈 Fix Jackson
  @ToString.Exclude               // 👈 Fix Lombok
  @EqualsAndHashCode.Exclude      // 👈 Fix Lombok
  private List<Enrollment> enrollments= new ArrayList<>();
  public Course(String name, String department) {
    this.name = name;
    this.department = department;
  }

  public List<Enrollment> getEnrollments(){
    return enrollments;
  }

  public void addEnrollment(Enrollment enrollment){
    if(!enrollments.contains(enrollment)){
      enrollments.add(enrollment);
    }
  }

  public void removeEnrollment(Enrollment enrollment){
    if(enrollments.contains(enrollment)){
      enrollments.remove(enrollment);
    }
  }

}
