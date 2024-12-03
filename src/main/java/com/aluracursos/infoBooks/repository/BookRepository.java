package com.aluracursos.infoBooks.repository;

import com.aluracursos.infoBooks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Búsqueda exacta por título
    List<Book> findByTitleIgnoreCase(String title);

    // Búsqueda por autor
    List<Book> findByAuthorIgnoreCase(String author);

    // Búsqueda de títulos similares (usando SQL LIKE)
    @Query("SELECT b FROM Book b WHERE upper(b.title) LIKE upper(concat('%', :title, '%'))")
    List<Book> findSimilarTitles(@Param("title") String title);

    // Búsqueda de títulos que contengan el término en cualquier parte del título
    List<Book> findByTitleContainingIgnoreCase(String query);
}
