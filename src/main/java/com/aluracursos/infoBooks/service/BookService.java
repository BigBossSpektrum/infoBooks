package com.aluracursos.infoBooks.service;

import com.aluracursos.infoBooks.dto.ExternalBookDTO;
import com.aluracursos.infoBooks.dto.SearchResultDTO;
import com.aluracursos.infoBooks.model.Book;
import com.aluracursos.infoBooks.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final GutendexClient gutendexClient;

    // Constructor con inyección de dependencias
    public BookService(BookRepository bookRepository, GutendexClient gutendexClient) {
        this.bookRepository = bookRepository;
        this.gutendexClient = gutendexClient;
    }

    // Buscar libros combinando datos locales y externos
    public SearchResultDTO searchBooks(String query) {
        // Buscamos libros por título
        List<Book> localBooksByTitle = bookRepository.findByTitleContainingIgnoreCase(query);

        // Buscamos libros por autor si no se encontraron por título
        List<Book> localBooksByAuthor = localBooksByTitle.isEmpty() ?
                bookRepository.findByAuthorIgnoreCase(query) :
                List.of();

        // Si no encontramos resultados exactos, buscamos títulos similares
        if (localBooksByTitle.isEmpty()) {
            localBooksByTitle = bookRepository.findSimilarTitles(query);
        }

        // Libros externos (API)
        List<ExternalBookDTO> externalBooks = gutendexClient.getBooksByTitle(query);

        // Retornar resultados combinados
        return new SearchResultDTO(localBooksByTitle, externalBooks);
    }

    // Guardar un libro externo en la base de datos local
    public Book saveExternalBook(ExternalBookDTO externalBookDTO) {
        Book book = new Book();
        book.setTitle(externalBookDTO.getTitle());
        book.setAuthor(String.join(", ", externalBookDTO.getAuthors()));
        book.setGenre("Desconocido"); // Por defecto, género desconocido
        book.setYear(0); // Por defecto, año desconocido
        return bookRepository.save(book);
    }

    // Obtener todos los libros locales
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Guardar un libro local en la base de datos
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Búsqueda exacta por título en datos locales
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleIgnoreCase(title);
    }

    // Búsqueda por autor en datos locales
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthorIgnoreCase(author);
    }

    // Búsqueda de títulos similares en datos locales
    public List<Book> findSimilarTitles(String title) {
        return bookRepository.findSimilarTitles(title); // Usar el método adecuado en el repositorio
    }
}
