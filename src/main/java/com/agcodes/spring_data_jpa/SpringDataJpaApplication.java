package com.agcodes.spring_data_jpa;

import com.agcodes.spring_data_jpa.student.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
//			dataLoader.run();

			DataLoader dataLoader = new DataLoader(studentRepository);
			dataLoader.initializeData();

			String email = "maria@gmail.edu";
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

		};



	};
	}


