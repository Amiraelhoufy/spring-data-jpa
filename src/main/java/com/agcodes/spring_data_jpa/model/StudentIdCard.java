package com.agcodes.spring_data_jpa.model;

import static jakarta.persistence.GenerationType.SEQUENCE;

import com.agcodes.spring_data_jpa.model.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="StudentIdCard")
@Table(
    name = "student_id_card",
    uniqueConstraints = {
        @UniqueConstraint(name = "student_id_card_number_unique",
            columnNames = "card_number")
    }
)
public class StudentIdCard {

  @Id
  @SequenceGenerator(
      name = "student_card_id_sequence",
      sequenceName = "student_card_id_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = SEQUENCE,
      generator = "student_card_id_sequence"
  )
  @Column(
      name = "id",
      updatable = false
  )
  private long Id;

  @Column(
      name = "card_number",
      nullable = false,
      length = 15)
  private String cardNumber;

  @OneToOne
  @JoinColumn(
      name = "student_id",             // db column name in THIS table
      referencedColumnName = "id",     // db column name in the Student table (foreign key points to)
      foreignKey = @ForeignKey(        // Renaming foreign key
          name = "student_id_fk"
      )
  )
  @JsonBackReference              // 👈 Fix Jackson
  @ToString.Exclude               // 👈 Fix Lombok
  @EqualsAndHashCode.Exclude      // 👈 Fix Lombok
  private Student student;

  public StudentIdCard(String cardNumber, Student student) {
  }
}
