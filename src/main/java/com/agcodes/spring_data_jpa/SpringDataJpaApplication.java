package com.agcodes.spring_data_jpa;

import com.agcodes.spring_data_jpa.student.StudentIdCardRepository;
import com.agcodes.spring_data_jpa.student.StudentRepository;
import com.agcodes.spring_data_jpa.util.DataLoader;
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
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository) {
		return args -> {

			DataLoader dataLoader = new DataLoader(studentRepository,studentIdCardRepository);

//			dataLoader.initializeData();
/******			CRUD operations using JPARepository ******/

//			dataLoader.section2();

/******	Querying Data (JPA Methods + JPQL Queries [Custom Queries] + @Query [JPQL or Native] ) ******/
//			dataLoader.section3();


//			dataLoader.section4();


//			dataLoader.removeOrphan();


//			dataLoader.testingOneToManyBiDirectionalRelation();

			dataLoader.loadStudentsV2();

		};



	};
	}


