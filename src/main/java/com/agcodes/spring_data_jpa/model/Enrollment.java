package com.agcodes.spring_data_jpa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Enrollment")
@Table(name= "enrollment")
public class Enrollment {
  @EmbeddedId
  private EnrollmentId id; // Composite Key
  @ManyToOne
  @MapsId("studentId") // maps this field to part of the composite key (same id as student)
  @JoinColumn(
      name = "student_id",    // db column name in THIS table
      foreignKey = @ForeignKey(name = "enrollment_student_id_fk")
  )
  @JsonBackReference              // 👈 Fix Jackson
  @ToString.Exclude               // 👈 Fix Lombok
  @EqualsAndHashCode.Exclude      // 👈 Fix Lombok
  private Student student;

  @ManyToOne
  @MapsId("courseId") // same id as course
  @JoinColumn(
      name = "course_id",   // db column name in THIS table
      foreignKey = @ForeignKey(name = "enrollment_course_id_fk")
  )
  @JsonBackReference              // 👈 Fix Jackson
  @ToString.Exclude               // 👈 Fix Lombok
  @EqualsAndHashCode.Exclude      // 👈 Fix Lombok
  private Course course;

  @Column(
      name="created_at",
      nullable = false,
      columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
  )
  private LocalDateTime createdAt;

}
