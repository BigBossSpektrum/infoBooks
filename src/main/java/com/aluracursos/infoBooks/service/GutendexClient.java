package com.aluracursos.infoBooks.service;

import com.aluracursos.infoBooks.dto.ExternalBookDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GutendexClient {
    private final RestTemplate restTemplate;

    public GutendexClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<ExternalBookDTO> getBooksByTitle(String title) {
        String url = "https://gutendex.com/books?search=" + title;
        String jsonResponse = restTemplate.getForObject(url, String.class);

        // Parsear JSON usando org.json
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray results = jsonObject.getJSONArray("results");

        List<ExternalBookDTO> externalBooks = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject bookJson = results.getJSONObject(i);
            externalBooks.add(mapToExternalBookDTO(bookJson));
        }
        return externalBooks;
    }

    private ExternalBookDTO mapToExternalBookDTO(JSONObject bookJson) {
        ExternalBookDTO dto = new ExternalBookDTO();

        // Convertir id a String
        Object id = bookJson.get("id");
        dto.setId(id.toString());

        // Obtener el título
        dto.setTitle(bookJson.getString("title"));

        // Obtener los autores
        JSONArray authorsArray = bookJson.getJSONArray("authors");
        List<String> authors = new ArrayList<>();
        for (int i = 0; i < authorsArray.length(); i++) {
            JSONObject authorJson = authorsArray.getJSONObject(i);
            authors.add(authorJson.getString("name"));
        }
        dto.setAuthors(authors);

        return dto;
    }
}
