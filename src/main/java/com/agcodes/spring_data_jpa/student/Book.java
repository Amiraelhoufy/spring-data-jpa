package com.agcodes.spring_data_jpa.student;

import static jakarta.persistence.GenerationType.SEQUENCE;

import com.agcodes.spring_data_jpa.student.Student;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity(name = "Book")
@Table(name = "book")
public class Book {

  @Id
  @SequenceGenerator(
      name = "book_sequence",
      sequenceName = "book_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = SEQUENCE,
      generator = "book_sequence"
  )
  @Column(
      name = "id",
      updatable = false
  )
  private long id;

  @Column(
      name = "book_name",
      nullable = false
  )
  private String bookName;

  @Column(
      name = "created_at",
      nullable = false,
      columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"

  )
  private LocalDateTime createdAt;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(
      name = "student_id",
//      nullable = false,
      referencedColumnName = "id",
      foreignKey = @ForeignKey(
          name = "student_book_id_fk"
      )
  )
  private Student student;

  public Book() {
  }

  public Book(String bookName, LocalDateTime createdAt) {
    this.bookName = bookName;
    this.createdAt = createdAt;
  }

  public long getId() {
    return id;
  }

  public Student getStudent() {
    return student;
  }

  public String getBookName() {
    return bookName;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", student=" + student +
        ", bookName='" + bookName + '\'' +
        ", createdAt=" + createdAt +
        '}';
  }
}
