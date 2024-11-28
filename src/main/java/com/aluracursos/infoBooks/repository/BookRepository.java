package com.aluracursos.infoBooks.repository;

import com.aluracursos.infoBooks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String query);
}
