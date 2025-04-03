package com.fis_2025_g6.entidades.usuarios;

public class Administrador extends Usuario {
    public Administrador(int id, String nombre, String email, String contrasena, String telefono, String direccion) {
        super(id, nombre, email, contrasena, telefono, direccion);
    }

    public void gestionarUsuarios(Usuario usuario) {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void gestionarRefugios(Refugio refugio) {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void gestionarSistema() {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
