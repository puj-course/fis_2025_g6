package com.fis_2025_g6.entidades.usuarios;

import java.util.ArrayList;

public class Adoptante extends Usuario {
    private ArrayList<String> historialAdopciones = new ArrayList<>();
    private double montoDonadoTotal = 0;

    public Adoptante(
        int id, String nombre, String email, String contrasena, String telefono, String direccion) {
        super(id, nombre, email, contrasena, telefono, direccion);
    }

    public ArrayList<String> getHistorialAdopciones() { return historialAdopciones; }
    public void setHistorialAdopciones(ArrayList<String> val) { historialAdopciones = val; }

    public double getMontoDonadoTotal() { return montoDonadoTotal; }
    public void setMontoDonadoTotal(double val) { montoDonadoTotal = val; }

    public void solicitarAdopcion(int idMascota) {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void completarFormulario(int formulario) {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public String verRecibos() {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void realizarDonacion(double monto) {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    } 
}
