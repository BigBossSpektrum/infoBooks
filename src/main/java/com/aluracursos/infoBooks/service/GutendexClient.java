package com.aluracursos.infoBooks.service;

import com.aluracursos.infoBooks.dto.ExternalBookDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GutendexClient {
    private final RestTemplate restTemplate;

    public GutendexClient() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> getBooksFromGutendex(String query) {
        String url = "https://gutendex.com/books?search=" + query;
        return restTemplate.getForObject(url, Map.class);
    }

    public List<ExternalBookDTO> getBooksByTitle(String title) {
        String url = "https://gutendex.com/books?search=" + title;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        // Mapear a DTO
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
        return results.stream().map(this::mapToExternalBookDTO).toList();
    }

    private ExternalBookDTO mapToExternalBookDTO(Map<String, Object> bookData) {
        ExternalBookDTO dto = new ExternalBookDTO();
        dto.setId(bookData.get("id").toString());
        dto.setTitle(bookData.get("title").toString());
        dto.setAuthors(((List<Map<String, String>>) bookData.get("authors"))
                .stream()
                .map(author -> author.get("name"))
                .toList());
        return dto;
    }
}
