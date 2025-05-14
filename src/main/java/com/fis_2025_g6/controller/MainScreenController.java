package com.fis_2025_g6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {
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

}

