package com.agcodes.spring_data_jpa.repository;

import com.agcodes.spring_data_jpa.model.StudentIdCard;
import org.springframework.data.repository.CrudRepository;

public interface StudentIdCardRepository
    extends CrudRepository<StudentIdCard,Long> {

}
