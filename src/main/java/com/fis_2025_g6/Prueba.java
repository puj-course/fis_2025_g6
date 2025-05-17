package com.fis_2025_g6;

import com.fis_2025_g6.config.AppConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class Prueba extends Application {

    private ApplicationContext springContext;

    @Override
    public void init() {
        // Inicia el contexto de Spring Boot
        springContext = new SpringApplicationBuilder(AppConfig.class).run();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pantalla_Principal.fxml"));

        // Inyecta controladores desde el contexto Spring
        loader.setControllerFactory(springContext::getBean);

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Adopción de Mascotas");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


