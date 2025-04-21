package view;

import controller.ControladorAutenticacion;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView extends Application {

    private final ControladorAutenticacion controlador = new ControladorAutenticacion();

    @Override
    public void start(Stage primaryStage) {
        Label lblTipoUsuario = new Label("Selecciona tipo de usuario:");
        ToggleGroup grupo = new ToggleGroup();
        RadioButton radioAdoptante = new RadioButton("Adoptante");
        radioAdoptante.setToggleGroup(grupo);
        radioAdoptante.setSelected(true);
        RadioButton radioRefugio = new RadioButton("Refugio");
        radioRefugio.setToggleGroup(grupo);

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Correo electrónico");

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");

        Button btnIngresar = new Button("Iniciar Sesión");
        Label lblResultado = new Label();

        btnIngresar.setOnAction(e -> {
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            boolean esAdoptante = radioAdoptante.isSelected();

            boolean exito = esAdoptante
                ? controlador.loginAdoptante(email, password)
                : controlador.loginRefugio(email, password);

            if (exito) {
                lblResultado.setText("Inicio de sesión exitoso ✅");
            } else {
                lblResultado.setText("Email o contraseña incorrectos ❌");
            }
        });

        VBox root = new VBox(10, lblTipoUsuario, radioAdoptante, radioRefugio, txtEmail, txtPassword, btnIngresar, lblResultado);
        root.setPadding(new Insets(20));
        Scene scene = new Scene(root, 300, 350);

        primaryStage.setTitle("Login de Usuario");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
