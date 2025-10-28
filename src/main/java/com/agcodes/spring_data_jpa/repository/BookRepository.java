package com.agcodes.spring_data_jpa.repository;

import com.agcodes.spring_data_jpa.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository
    extends CrudRepository<Book, Long> {

}
