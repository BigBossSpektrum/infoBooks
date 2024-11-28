package com.aluracursos.infoBooks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InfoBooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoBooksApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext ctx) {
		return args -> {
			// Obtener el menú desde el contexto de Spring y mostrarlo
			ConsoleMenu consoleMenu = ctx.getBean(ConsoleMenu.class);
			consoleMenu.showMenu();
		};
	}
}
