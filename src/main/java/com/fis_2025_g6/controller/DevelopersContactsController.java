package com.fis_2025_g6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DevelopersContactsController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField emailField;

    @FXML
    private TextArea mensajeArea;

    @FXML
    private void enviarMensaje() {
        String nombre = nombreField.getText();
        String email = emailField.getText();
        String mensaje = mensajeArea.getText();

        if (nombre.isEmpty() || email.isEmpty() || mensaje.isEmpty()) {
            mostrarAlerta("Todos los campos deben estar completos.");
            return;
        }

        // Simulación de envío (podrías guardar en archivo o consola si deseas)
        System.out.println("Mensaje enviado:");
        System.out.println("Nombre: " + nombre);
        System.out.println("Email: " + email);
        System.out.println("Mensaje: " + mensaje);

        mostrarAlerta("Mensaje enviado con éxito. ¡Gracias por contactarnos!");

        nombreField.clear();
        emailField.clear();
        mensajeArea.clear();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Contacto");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

