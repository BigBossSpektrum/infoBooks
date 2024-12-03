package com.aluracursos.infoBooks.controller;

import com.aluracursos.infoBooks.dto.ExternalBookDTO;
import com.aluracursos.infoBooks.dto.SearchResultDTO;
import com.aluracursos.infoBooks.model.Book;
import com.aluracursos.infoBooks.repository.BookRepository;
import com.aluracursos.infoBooks.service.BookService;
import com.aluracursos.infoBooks.service.GutendexClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final GutendexClient gutendexClient;

    public BookController(BookService bookService, GutendexClient gutendexClient) {
        this.bookService = bookService;
        this.gutendexClient = gutendexClient;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @GetMapping("/search")
    public SearchResultDTO searchBooks(@RequestParam String query) {
        return bookService.searchBooks(query);
    }

    @PostMapping("/saveExternal")
    public Book saveExternalBook(@RequestBody ExternalBookDTO externalBookDTO) {
        return bookService.saveExternalBook(externalBookDTO);
    }

    @GetMapping("/searchByTitle")
    public SearchResultDTO searchByTitle(@RequestParam String title) {
        // Libros externos
        List<ExternalBookDTO> externalBooks = gutendexClient.getBooksByTitle(title);

        // Libros locales
        BookRepository bookRepository = null;
        List<Book> localBooks = bookRepository.findByTitleContainingIgnoreCase(title);

        return new SearchResultDTO(localBooks, externalBooks);
    }

    @GetMapping("/allLocal")
    public List<Book> getAllLocalBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/allExternal")
    public List<ExternalBookDTO> getAllExternalBooks() {
        return gutendexClient.getBooksByTitle(""); // Sin filtro, retorna los primeros resultados
    }

}
