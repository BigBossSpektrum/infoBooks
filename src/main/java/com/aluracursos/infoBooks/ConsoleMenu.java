package com.aluracursos.infoBooks;

import com.aluracursos.infoBooks.model.Book;
import com.aluracursos.infoBooks.service.BookService;
import com.aluracursos.infoBooks.dto.ExternalBookDTO;
import com.aluracursos.infoBooks.dto.SearchResultDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleMenu {

    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(BookService bookService) {
        this.bookService = bookService;
    }

    public void showMenu() {
        while (true) {
            System.out.println("===== MENÚ =====");
            System.out.println("1. Buscar libros por título");
            System.out.println("2. Buscar libros por autor");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Limpia el buffer

            switch (option) {
                case 1 -> searchByTitle();
                case 2 -> searchByAuthor();
                case 0 -> {
                    System.out.println("Saliendo del sistema...");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void searchByTitle() {
        System.out.print("Introduce el título del libro que deseas buscar: ");
        String title = scanner.nextLine();

        // Usamos el método de searchBooks para buscar tanto en la base de datos local como en la API
        SearchResultDTO searchResult = bookService.searchBooks(title);

        // Mostrar los resultados locales
        List<Book> localBooks = searchResult.getLocalBooks();
        if (!localBooks.isEmpty()) {
            System.out.println("Libros encontrados en la base de datos local:");
            localBooks.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron libros en la base de datos local.");
        }

        // Mostrar los resultados externos (de la API)
        List<ExternalBookDTO> externalBooks = searchResult.getExternalBooks();
        if (!externalBooks.isEmpty()) {
            System.out.println("Libros encontrados en la API de Gutendex:");
            externalBooks.forEach(book -> System.out.println(book.getTitle() + " - " + String.join(", ", book.getAuthors())));
        } else {
            System.out.println("No se encontraron libros en la API de Gutendex.");
        }

        // Si no se encuentra ningún libro, sugerir títulos similares
        if (localBooks.isEmpty() && externalBooks.isEmpty()) {
            suggestSimilarTitles(title);
        }
    }

    private void searchByAuthor() {
        System.out.print("Introduce el autor del libro que deseas buscar: ");
        String author = scanner.nextLine();

        // Usamos el método de searchBooks para buscar tanto en la base de datos local como en la API
        SearchResultDTO searchResult = bookService.searchBooks(author);

        // Mostrar los resultados locales
        List<Book> localBooks = searchResult.getLocalBooks();
        if (!localBooks.isEmpty()) {
            System.out.println("Libros encontrados en la base de datos local:");
            localBooks.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron libros en la base de datos local.");
        }

        // Mostrar los resultados externos (de la API)
        List<ExternalBookDTO> externalBooks = searchResult.getExternalBooks();
        if (!externalBooks.isEmpty()) {
            System.out.println("Libros encontrados en la API de Gutendex:");
            externalBooks.forEach(book -> System.out.println(book.getTitle() + " - " + String.join(", ", book.getAuthors())));
        } else {
            System.out.println("No se encontraron libros en la API de Gutendex.");
        }
    }

    private void suggestSimilarTitles(String title) {
        List<Book> similarBooks = bookService.findSimilarTitles(title);
        if (!similarBooks.isEmpty()) {
            System.out.println("Tal vez te interesen estos libros con títulos similares:");
            similarBooks.forEach(System.out::println);
        }
    }
}
