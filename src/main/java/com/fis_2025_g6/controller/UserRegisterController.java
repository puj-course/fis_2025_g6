package com.fis_2025_g6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserRegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField emailField;

    @FXML
    public void registrarse() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        // Aquí llamas a tu backend/service para guardar el usuario
        System.out.println("Registrando usuario: " + username + " (" + email + ")");
    }
}

