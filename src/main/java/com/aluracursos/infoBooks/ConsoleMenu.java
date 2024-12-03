package com.aluracursos.infoBooks;

import com.aluracursos.infoBooks.model.Book;
import com.aluracursos.infoBooks.service.BookService;
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

        List<Book> books = bookService.findByTitle(title);
        if (books.isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
            suggestSimilarTitles(title);
        } else {
            System.out.println("Libros encontrados:");
            books.forEach(System.out::println);
        }
    }

    private void searchByAuthor() {
        System.out.print("Introduce el autor del libro que deseas buscar: ");
        String author = scanner.nextLine();

        List<Book> books = bookService.findByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("No se encontraron libros de ese autor.");
        } else {
            System.out.println("Libros encontrados:");
            books.forEach(System.out::println);
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
