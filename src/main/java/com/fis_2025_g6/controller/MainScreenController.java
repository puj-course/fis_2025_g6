package com.fis_2025_g6.controller;

import com.fis_2025_g6.dto.PetCatalogDto;
import com.fis_2025_g6.service.MascotasService;
import com.fis_2025_g6.service.PetService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class MainScreenController {

    @Autowired
    private MascotasService petService;

    @FXML
    private void abrirRegistroUsuario() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/registro_usuario.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Registro de Usuario");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void abrirContactoRefugios() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/contacto_refugios.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Contacto con Refugios");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void abrirContactoDesarrolladores() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/contacto_desarrolladores.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Contacto con Desarrolladores");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarGuiaAdopcion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guia_adopcion.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Guía de Adopción");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarCatalogoMascotas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fis_2025_g6/fxml/CatalogoMascotas.fxml"));
            Parent root = loader.load();

            PetCatalogController controller = loader.getController();

            List<PetCatalogDto> mascotas = petService.obtenerMascotasDisponibles();

            // En vez de llamar a initialize, llamamos a un método específico para pasar los datos
            controller.cargarMascotas(mascotas);

            Stage stage = new Stage();
            stage.setTitle("Catálogo de Mascotas");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

