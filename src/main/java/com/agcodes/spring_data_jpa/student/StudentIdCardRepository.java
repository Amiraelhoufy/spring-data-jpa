package com.agcodes.spring_data_jpa.student;

import org.springframework.data.repository.CrudRepository;

public interface StudentIdCardRepository
    extends CrudRepository<StudentIdCard,Long> {

}
