package com.aluracursos.infoBooks.dto;

import com.aluracursos.infoBooks.model.Book;
import java.util.List;

public class SearchResultDTO {
    private List<Book> localBooks;
    private List<ExternalBookDTO> externalBooks;

    // Constructor
    public SearchResultDTO(List<Book> localBooks, List<ExternalBookDTO> externalBooks) {
        this.localBooks = localBooks;
        this.externalBooks = externalBooks;
    }

    // Getters y Setters
    public List<Book> getLocalBooks() {
        return localBooks;
    }

    public void setLocalBooks(List<Book> localBooks) {
        this.localBooks = localBooks;
    }

    public List<ExternalBookDTO> getExternalBooks() {
        return externalBooks;
    }

    public void setExternalBooks(List<ExternalBookDTO> externalBooks) {
        this.externalBooks = externalBooks;
    }
}
