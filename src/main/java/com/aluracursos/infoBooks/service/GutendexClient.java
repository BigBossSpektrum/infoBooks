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
        // Reemplazar los espacios por "&"
        String formattedTitle = title.replace(" ", "&");

        String url = "https://gutendex.com/books?search=" + formattedTitle;
        List<ExternalBookDTO> externalBooks = new ArrayList<>();

        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                // Parsear JSON usando org.json
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray results = jsonObject.optJSONArray("results");

                // Verificar si el JSON contiene resultados
                if (results != null) {
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject bookJson = results.getJSONObject(i);
                        externalBooks.add(mapToExternalBookDTO(bookJson));
                    }
                }
            }
        } catch (Exception e) {
            // Manejo de excepciones: si la API no responde o hay algún otro error
            System.out.println("Error al obtener los libros de la API: " + e.getMessage());
        }

        return externalBooks;
    }

    private ExternalBookDTO mapToExternalBookDTO(JSONObject bookJson) {
        ExternalBookDTO dto = new ExternalBookDTO();

        // Convertir id a String
        Object id = bookJson.opt("id");
        if (id != null) {
            dto.setId(id.toString());
        }

        // Obtener el título
        dto.setTitle(bookJson.optString("title", "Título no disponible"));

        // Obtener los autores
        JSONArray authorsArray = bookJson.optJSONArray("authors");
        List<String> authors = new ArrayList<>();
        if (authorsArray != null) {
            for (int i = 0; i < authorsArray.length(); i++) {
                JSONObject authorJson = authorsArray.optJSONObject(i);
                if (authorJson != null) {
                    authors.add(authorJson.optString("name", "Autor no disponible"));
                }
            }
        }
        dto.setAuthors(authors);

        return dto;
    }
}
