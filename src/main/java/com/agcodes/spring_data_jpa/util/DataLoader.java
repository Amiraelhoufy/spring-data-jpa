package com.agcodes.spring_data_jpa.util;

import com.agcodes.spring_data_jpa.student.Book;
import com.agcodes.spring_data_jpa.student.Student;
import com.agcodes.spring_data_jpa.student.StudentIdCard;
import com.agcodes.spring_data_jpa.student.StudentIdCardRepository;
import com.agcodes.spring_data_jpa.student.StudentRepository;
import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

  private final StudentRepository studentRepository;
  private final StudentIdCardRepository studentIdCardRepository;
  public DataLoader(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository) {
    this.studentRepository = studentRepository;
    this.studentIdCardRepository = studentIdCardRepository;
  }


  public void loadStudentsV1(){

    Student maria = new Student(
        "Maria",
        "Ahmed",
        "maria@gmail.edu",
        25
    );

    Student ahmed = new Student(
        "Ahmed",
        "Ali",
        "ahmed.ali@gmail.edu",
        12
    );


    Student maria2 = new Student(
        "Maria",
        "Omar",
        "maria.omar@gmail.edu",
        18
    );

    System.out.println("Adding maria & ahmed");
    studentRepository.saveAll(List.of(maria, ahmed,maria2));

    System.out.print("Number of students: ");
    System.out.println(studentRepository.count());
  }

  public void getStudentsCount(){
    System.out.print("Number of students:");
    System.out.println(studentRepository.count());
  }

  public void printStudentById(Long id){
    studentRepository
        .findById(id)
        .ifPresentOrElse(
            student -> System.out.println(student),
            () -> System.out.println("Student with id "+ id + " not found!")
        );
  };

  public void deleteStudentById(long id){
    System.out.println("Delete student with id: " + id);
    studentRepository.deleteById(id);

  }

  public void printAllStudents() {
    System.out.println("Select all students");
    List<Student> students = studentRepository.findAll();
    students.forEach(System.out::println);
  }


  // Section 2:
  // CRUD operations using JPARepository
  public void section2() {
    loadStudentsV1();
    getStudentsCount();

    printStudentById(2L);
    printStudentById(3L);

    deleteStudentById(1L);
    getStudentsCount();

    printAllStudents();
  }

  // Section 3:
  // Querying Data (JPA Methods + JPQL Queries [Custom Queries] + @Query [JPQL or Native] )
  public void section3(){

    String email = "maria@gmail.edu";
    System.out.println("delete student with email: " + email);
    studentRepository
        .findStudentByEmail(email)
        .ifPresentOrElse(
            student -> System.out.println(student),
            ()->{System.out.println("Couldn't find any student with this email: " + email);
            });


    System.out.println("Finding students with first name 'maria' and age exactly 18:");

    System.out.println("1- Using JPQL:");
    studentRepository.findStudentsByFirstNameEqualsAndAgeEquals("Maria",18)
        .forEach(System.out::println);

    System.out.println("2- Using @Query JPQL:");
    studentRepository.selectStudentWhereFirstNameAndAgeEquals("Maria",18)
        .forEach(System.out::println);

    System.out.println("2- Using @Query Native:");
    studentRepository.selectStudentWhereFirstNameAndAgeEqualsNative("Maria",18)
        .forEach(System.out::println);


    System.out.println("Finding students with first name 'maria' and age greater than 18:");
    studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThan("Maria",18)
        .forEach(System.out::println);

    System.out.println("Finding students with first name 'maria' and age greater than or equal to 18:");
    studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual("Maria",18)
        .forEach(System.out::println);

    System.out.println("Deleting student with id: 1L");
    System.out.println(studentRepository.deleteStudentById(1L));

  }

  // Section 4
  // Generating students with Faker library and saving them to database
  // Sorting & pagination
  public void section4(){

    Faker faker = new Faker();
    List<Student> studentList = new ArrayList<>();
    for (int i=0; i<20 ;i++){
      String firstName = faker.name().firstName();
      String lastName = faker.name().lastName();
      String email = String.format("%s.%s@gmail.edu",firstName,lastName);
      Student student = new Student(
          firstName,
          lastName,
          email,
          faker.number().numberBetween(17, 55)
      );
      studentList.add(student);
    }

    studentRepository.saveAll(studentList);
//    studentRepository.findAll().forEach(student -> System.out.println(student));

//    sorting();
//    pagingAndSorting();

  }

  private void sorting() {

    Sort sortByFirstNameAsc = Sort.by(Direction.ASC, "firstName");
    Sort sortByFirstNameAscAndAgeDesc = Sort.by("firstName").ascending()
        .and(Sort.by("age").descending());

    studentRepository
        .findAll(sortByFirstNameAscAndAgeDesc)
        .forEach(student -> {
          System.out.println("FirstName: "+ student.getFirstName() + "| Age: " + student.getAge());
        });
  }


  private void pagingAndSorting() {

    PageRequest pageRequest = PageRequest.of(
        0,
        5,
        Sort.by("firstName").ascending().and(Sort.by(Order.desc("age")))
    );

    Page<Student> page = studentRepository.findAll(pageRequest);
    System.out.println(page);
  }

  // loadStudentIdCards | Relationships [1-1]
  public void loadStudentsV2(){

    Faker faker = new Faker();
    List<Student> students = new ArrayList<>();
    for (int i=0; i<20 ;i++){
      String firstName = faker.name().firstName();
      String lastName = faker.name().lastName();
      String email = String.format("%s.%s@gmail.edu",firstName,lastName);
      Student student = new Student(
          firstName,
          lastName,
          email,
          faker.number().numberBetween(17, 55)
      );

      // Add books to student
      student.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(4)));
      student.addBook(new Book("OOP", LocalDateTime.now()));
      student.addBook(new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

      // Create and set the StudentIdCard
      String cardNumber = faker.numerify("###############");  // Generates a 15-digit card number
      StudentIdCard studentIdCard = new StudentIdCard(cardNumber, student);

      // Set the card to the student
      student.setStudentIdCard(studentIdCard);  // Bidirectional relationship

      /*
        Save studentIdCard before saving student manually
        or
        set OneToOne Relationship to {CascadeType.PERSIST,CascadeType.REMOVE}
      */

//      studentIdCardRepository.save(studentIdCard);


      students.add(student);
    }

    studentRepository.saveAll(students);


//    sorting();
//    pagingAndSorting();

  }

  // Relationships [1-1]
  public void removeOrphan(){
//  Load data
    loadStudentsV2();

    studentRepository.deleteById(1L);


  }

  public void testingOneToManyBiDirectionalRelation(){

    Faker faker = new Faker();

    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = String.format("%s.%s@gmail.edu", firstName, lastName);
    Student student = new Student(
        firstName,
        lastName,
        email,
        faker.number().numberBetween(17, 55));

    student.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(4)));
    student.addBook(new Book("OOP", LocalDateTime.now()));
    student.addBook(new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));

    StudentIdCard studentIdCard = new StudentIdCard("123456789", student);

    studentRepository.save(student);

    studentRepository.findById(1L)
        .ifPresent(s -> {
          System.out.println("fetch book lazy...");
          List<Book> books = student.getBooks();
          books.forEach(book -> {
            System.out.println(
                s.getFirstName() + " borrowed " + book.getBookName());
          });
        });
  }
}
