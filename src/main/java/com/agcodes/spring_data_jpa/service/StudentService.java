package com.agcodes.spring_data_jpa.service;

import com.agcodes.spring_data_jpa.dto.StudentRequest;
import com.agcodes.spring_data_jpa.model.Book;
import com.agcodes.spring_data_jpa.model.Student;
import com.agcodes.spring_data_jpa.model.StudentIdCard;
import com.agcodes.spring_data_jpa.repository.BookRepository;
import com.agcodes.spring_data_jpa.repository.StudentIdCardRepository;
import com.agcodes.spring_data_jpa.repository.StudentRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final BookRepository bookRepository;
  private final StudentRepository studentRepository;
  private final StudentIdCardRepository studentIdCardRepository;

  public Student createStudent(StudentRequest request){

    Student student = new Student();
    student.setFirstName(request.firstName());
    student.setLastName(request.lastName());
    student.setEmail(request.email());
    student.setAge(request.age());

    // StudentIdCard (1-1) relationship
    StudentIdCard studentIdCard = new StudentIdCard();
    studentIdCard.setCardNumber(request.cardNumber());
    studentIdCard.setStudent(student);
    student.setStudentIdCard(studentIdCard);

    // Books (1-M) relationship
    if(request.books()!=null){
      request.books().forEach(bookName -> {
        Book book = new Book();
        book.setBookName(bookName);
        book.setCreatedAt(LocalDateTime.now());
        book.setStudent(student);
        student.getBooks().add(book);
      });

    }

    return studentRepository.save(student);
  }

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  public Optional<Student> getStudentById(Long id) {
    return studentRepository.findById(id);
  }

  public boolean deleteStudent(Long id) {
    if(studentRepository.existsById(id)){
      studentRepository.deleteStudentById(id);
      return true;
    }
    return false;
  }
}
