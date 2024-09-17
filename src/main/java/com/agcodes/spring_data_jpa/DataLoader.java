package com.agcodes.spring_data_jpa;

import com.agcodes.spring_data_jpa.student.Student;
import com.agcodes.spring_data_jpa.student.StudentRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

  private final StudentRepository studentRepository;

  public DataLoader(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public void initializeData(){

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

  public void run() {
    initializeData();
    getStudentsCount();

    printStudentById(2L);
    printStudentById(3L);

    deleteStudentById(1L);
    getStudentsCount();

    printAllStudents();
  }

}
