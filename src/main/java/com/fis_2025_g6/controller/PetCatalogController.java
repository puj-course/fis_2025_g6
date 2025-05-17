package com.fis_2025_g6.controller;

import com.fis_2025_g6.dto.PetCatalogDto;
import com.fis_2025_g6.service.MascotasService;
import com.fis_2025_g6.service.PetService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.List;

public class PetCatalogController {

    @FXML
    private TilePane contenedorMascotas;

    private final MascotasService petService;

    public PetCatalogController(MascotasService petService) {
        this.petService = petService;
    }

    @FXML
    public void initialize() {
        List<PetCatalogDto> mascotas = petService.obtenerMascotasDisponibles();

        for (PetCatalogDto pet : mascotas) {
            VBox card = crearTarjetaMascota(pet);
            contenedorMascotas.getChildren().add(card);
        }
    }

    public void cargarMascotas(List<PetCatalogDto> mascotas) {
        contenedorMascotas.getChildren().clear();
        for (PetCatalogDto pet : mascotas) {
            VBox card = crearTarjetaMascota(pet);
            contenedorMascotas.getChildren().add(card);
        }
    }

    private VBox crearTarjetaMascota(PetCatalogDto pet) {
        VBox box = new VBox(10);
        box.setStyle("-fx-background-color: #f1f1f1; -fx-padding: 15; -fx-alignment: center; -fx-border-radius: 8; -fx-background-radius: 8; -fx-border-color: #cccccc;");
        box.setPrefWidth(200);

        ImageView imagen = new ImageView();
        try {
            imagen.setImage(new Image(pet.getPhotoUrl(), true));
        } catch (Exception e) {
            imagen.setImage(new Image("/img/placeholder.png")); // Imagen por defecto
        }
        imagen.setFitWidth(150);
        imagen.setFitHeight(150);
        imagen.setPreserveRatio(true);

        Label nombre = new Label(pet.getName());
        nombre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        box.getChildren().addAll(imagen, nombre);
        return box;
    }
}
