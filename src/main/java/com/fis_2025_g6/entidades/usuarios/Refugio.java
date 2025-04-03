package com.fis_2025_g6.entidades.usuarios;

import java.util.ArrayList;
import java.util.Date;

import com.fis_2025_g6.entidades.mascotas.Gato;
import com.fis_2025_g6.entidades.mascotas.Mascota;
import com.fis_2025_g6.entidades.mascotas.Perro;

public class Refugio extends Usuario {
    private String nombreRefugio;
    private String descripcion;
    private ArrayList<Mascota> mascotas = new ArrayList<>();

    public Refugio(
        int id, String nombre, String email, String contrasena, String telefono, String direccion,
        String nombreRefugio, String descripcion
    ) {
        super(id, nombre, email, contrasena, telefono, direccion);
        this.nombreRefugio = nombreRefugio;
        this.descripcion = descripcion;
    }

    public String getNombreRefugio() { return nombreRefugio; }
    public void setNombreRefugio(String val) { nombreRefugio = val; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String val) { descripcion = val; }

    public ArrayList<Mascota> getMacotas() { return mascotas; }
    public void setMascotas(ArrayList<Mascota> val) { mascotas = val; }

    // GRASP: Creador. Refugio es el responsable de crear mascotas.
    public Mascota registrarMascota(int id, String tipo, String nombre, int edad, String sexo, String descripcion, Date fechaRegistro) {
        Mascota mascota;
        if (tipo.equalsIgnoreCase("perro")) {
            mascota = new Perro(id, nombre, edad, sexo, descripcion, fechaRegistro);
        } else if (tipo.equalsIgnoreCase("gato")) {
            mascota = new Gato(id, nombre, edad, sexo, descripcion, fechaRegistro);
        } else {
            throw new UnsupportedOperationException("Not implemented yet.");
        }
        mascotas.add(mascota);
        return mascota;
    }

    public void gestionarSolicitudes() {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public String verEstadisticas() {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void programarCita(Adoptante adoptante, Mascota mascota, Date fecha) {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
