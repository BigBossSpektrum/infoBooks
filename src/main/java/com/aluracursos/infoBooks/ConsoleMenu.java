package com.aluracursos.infoBooks;

import com.aluracursos.infoBooks.dto.SearchResultDTO;
import com.aluracursos.infoBooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleMenu {

    private final BookService bookService;
    private final Scanner scanner;

    // Constructor con inyección de dependencias
    @Autowired
    public ConsoleMenu(BookService bookService) {
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int option = -1;

        // Menú principal
        while (option != 0) {
            System.out.println("\n===== MENÚ =====");
            System.out.println("1. Buscar libros por título");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    searchBooksByTitle();
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida, intenta nuevamente.");
            }
        }
    }

    private void searchBooksByTitle() {
        System.out.print("Introduce el título del libro que deseas buscar: ");
        String title = scanner.nextLine();

        // Llamar al servicio para obtener los resultados de la búsqueda
        SearchResultDTO result = bookService.searchBooks(title);

        // Mostrar los resultados
        if (result.getLocalBooks().isEmpty() && result.getExternalBooks().isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
        } else {
            System.out.println("\nResultados de la búsqueda:");
            System.out.println("Libros locales:");
            result.getLocalBooks().forEach(book -> System.out.println("- " + book.getTitle()));

            System.out.println("\nLibros de la API externa:");
            result.getExternalBooks().forEach(externalBook ->
                    System.out.println("- " + externalBook.getTitle() + " (Autor: " + String.join(", ", externalBook.getAuthors()) + ")"));
        }
    }
}
