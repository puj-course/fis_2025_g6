package com.fis_2025_g6.service;

import com.fis_2025_g6.dto.PetCatalogDto;
import com.fis_2025_g6.entity.Pet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class MascotasService {
    private static final String BASE_URL = "http://localhost:8080/mascotas";

    public List<PetCatalogDto> obtenerMascotasDisponibles() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            List<Pet> mascotas = mapper.readValue(response.body(), new TypeReference<List<Pet>>() {});

            return mascotas.stream()
                    .map(pet -> new PetCatalogDto(pet.getNombre(), pet.getFotoUrl()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
