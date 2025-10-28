package com.agcodes.spring_data_jpa.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Student")
@Table(
    name="student",
    uniqueConstraints = {
        @UniqueConstraint(name = "student_email_unique",columnNames = "email")
})
public class Student {
  @Id
  @SequenceGenerator(
      name = "student_sequence",         // Generator name, used by @GeneratedValue
      sequenceName = "student_sequence", // Database sequence name
      allocationSize = 1                 // Increment by 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,            // Generation strategy. SEQUENCE means it uses a database sequence.
      generator = "student_sequence" // The name of the sequence generator defined in: @SequenceGenerator.
  )
  @Column(
      name = "id",
      updatable = false
  )
  private Long id;
  @Column(
      name = "first_name",
      nullable = false,
      columnDefinition = "TEXT"
  )
  private String firstName;
  @Column(
      name = "last_name",
      nullable = false,
      columnDefinition = "TEXT"
  )
  private String lastName;
  @Column(
      name = "email",
      nullable = false,
      columnDefinition = "TEXT"
  )
  private String email;
  @Column(
      name = "age",
      nullable = false
  )
  private int age;


  // Bidirectional relationship
@OneToOne(mappedBy = "student", // "student": refers to the repository object found in studentIdCard class
    orphanRemoval = true, //  The StudentIdCard will be deleted automatically
    cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
@JsonManagedReference           // 👈 Fix Jackson
@ToString.Exclude               // 👈 Fix Lombok
@EqualsAndHashCode.Exclude      // 👈 Fix Lombok
  private StudentIdCard studentIdCard;

@OneToMany(
    mappedBy = "student",
    orphanRemoval = true,
    cascade = {CascadeType.PERSIST,CascadeType.REMOVE}
)
@JsonManagedReference           // 👈 Fix Jackson
@ToString.Exclude               // 👈 Fix Lombok
@EqualsAndHashCode.Exclude      // 👈 Fix Lombok
  private List<Book> books= new ArrayList<>();

  /*@ManyToMany(
      cascade = {CascadeType.PERSIST,CascadeType.REMOVE}
  )
  @JoinTable(name = "enrollment",
  joinColumns = @JoinColumn(
      name = "student_id",
      foreignKey = @ForeignKey(name = "enrollment_student_id_fk")
  ),
      inverseJoinColumns = @JoinColumn(
          name = "course_id",
          foreignKey = @ForeignKey(name = "enrollment_course_id_fk")
      )
  )

  private List<Course> courses = new ArrayList<>();
  */

  @OneToMany(
      cascade = {CascadeType.PERSIST,CascadeType.REMOVE},
      mappedBy = "student" // in the enrollment entity
  )
  @JsonManagedReference           // 👈 Fix Jackson
  @ToString.Exclude               // 👈 Fix Lombok
  @EqualsAndHashCode.Exclude      // 👈 Fix Lombok
  private List<Enrollment> enrollments = new ArrayList<>();

  public Student(String firstName, String lastName, String email, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.age = age;
  }
  public void addBook(Book book){
    if(!this.books.contains(book)){
      this.books.add(book);
      book.setStudent(this); // keeping both ways in sync (Bidirectional Relationships)
    }
  }

  public void removeBook(Book book){
    if(this.books.contains(book)){
      this.books.remove(book);
      book.setStudent(null); // keeping both ways in sync (Bidirectional Relationships)
    }
  }
  /*

  public void addCourse(Course course){
    courses.add(course);
    courses.getStudents().add(this);
  }

  public void removeCourse(Course course){
    if(courses.contains(course)){
      courses.remove(course);
      courses.getStudents().remove(this);
    }
  }

  */

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
