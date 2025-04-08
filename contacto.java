import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.ArrayList;

public class ContactoRefugiosApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        List<Refugio> refugios = cargarRefugios(); // Simulación de datos

        VBox layout = new VBox(10);
        layout.getChildren().add(new Label("Selecciona un refugio para contactar:"));

        for (Refugio refugio : refugios) {
            Button btn = new Button("Contactar a " + refugio.getNombre());
            btn.setOnAction(e -> mostrarContacto(refugio));
            layout.getChildren().add(btn);
        }

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Contacto Refugios");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private List<Refugio> cargarRefugios() {
        List<Refugio> lista = new ArrayList<>();
        lista.add(new Refugio("Refugio Esperanza", "contacto@refugioesperanza.com", "+123456789"));
        lista.add(new Refugio("Huellitas Felices", "info@huellitasfelices.com", "+987654321"));
        return lista;
    }

    private void mostrarContacto(Refugio refugio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información de Contacto");
        alert.setHeaderText(refugio.getNombre());
        alert.setContentText("Email: " + refugio.getEmail() + "\nTeléfono: " + refugio.getTelefono());
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Refugio {
    private String nombre, email, telefono;

    public Refugio(String nombre, String email, String telefono) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
}
