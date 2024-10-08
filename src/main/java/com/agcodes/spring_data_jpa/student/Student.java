package com.agcodes.spring_data_jpa.student;

import static jakarta.persistence.GenerationType.SEQUENCE;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Student")
@Table(
    name="student",
    uniqueConstraints = {
        @UniqueConstraint(name = "student_email_unique",columnNames = "email")
})
public class Student {
  @Id
  @SequenceGenerator(
      name = "student_sequence",
      sequenceName = "student_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = SEQUENCE,
      generator = "student_sequence"
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
@OneToOne(mappedBy = "student", // "student": refers to the student object found in studentIdCard class
    orphanRemoval = true, //  The StudentIdCard will be deleted automatically
    cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
  private StudentIdCard studentIdCard;

@OneToMany(
    mappedBy = "student",
    orphanRemoval = true,
    cascade = {CascadeType.PERSIST,CascadeType.REMOVE}
)
  private List<Book> books= new ArrayList<>();

  public Student() {

  }
  public Student(
                  String firstName,
                  String lastName,
                  String email,
                  int age) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.age = age;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public StudentIdCard getStudentIdCard(){
    return this.studentIdCard;
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

  public void setStudentIdCard(StudentIdCard studentIdCard) {
    this.studentIdCard = studentIdCard;
  }

  public List<Book> getBooks() {
    return books;
  }



  @Override
  public String toString() {
    return "Student{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", age=" + age +
        '}';
  }
}
