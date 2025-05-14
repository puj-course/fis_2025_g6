package com.fis_2025_g6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis_2025_g6.entity.Refuge;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class RefugesContactsController{

    @FXML
    private ListView<Refuge> listaRefugios;

    @FXML
    public void initialize() {
        cargarRefugios();
    }

    private void cargarRefugios() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/refugios"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Refuge[] refugios = new ObjectMapper().readValue(response.body(), Refuge[].class);
            ObservableList<Refuge> items = FXCollections.observableArrayList(refugios);
            listaRefugios.setItems(items);

            listaRefugios.setOnMouseClicked((MouseEvent event) -> {
                Refuge seleccionado = listaRefugios.getSelectionModel().getSelectedItem();
                if (seleccionado != null) {
                    mostrarAlerta(seleccionado);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(Refuge refugio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contacto del Refugio");
        alert.setHeaderText(refugio.getUsername());
        alert.setContentText("Email: " + refugio.getEmail() +
                "\nTeléfono: " + refugio.getPhoneNumber() +
                "\nDirección: " + refugio.getAddress());
        alert.showAndWait();
    }
}

