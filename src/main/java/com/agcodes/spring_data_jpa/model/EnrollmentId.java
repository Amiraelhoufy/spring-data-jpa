package com.agcodes.spring_data_jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EnrollmentId implements Serializable {
  @Column(name="student_id")
  private long studentId;
  @Column(name = "course_id")
  private long courseId;

}
