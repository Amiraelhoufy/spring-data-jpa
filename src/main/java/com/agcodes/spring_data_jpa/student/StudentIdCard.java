package com.agcodes.spring_data_jpa.student;

import static jakarta.persistence.GenerationType.SEQUENCE;

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

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(
    name = "student_id",             // in this table
    referencedColumnName = "id",     // in the student table
      foreignKey = @ForeignKey(      // Renaming foreign key
          name = "student_id_fk"
      )
 )
  private Student student;
  @Column(
      name = "card_number",
      nullable = false,
      length = 15)
  private String cardNumber;


  public StudentIdCard() {
  }

  public StudentIdCard(long id, String cardNumber) {
    Id = id;
    this.cardNumber = cardNumber;

  }

  public StudentIdCard(String cardNumber,Student student) {
    this.cardNumber = cardNumber;
    this.student = student;
  }

  @Override
  public String toString() {
    return "StudentIdCard{" +
        "Id=" + Id +
        ", student=" + student +
        ", cardNumber='" + cardNumber + '\'' +
        '}';
  }

  public long getId() {
    return Id;
  }

  public String getCardNumber() {
    return cardNumber;
  }
}
