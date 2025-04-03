package com.fis_2025_g6.entidades.usuarios;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String contrasena;
    private String telefono;
    private String direccion;

    public Usuario(int id, String nombre, String email, String contrasena, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getId() { return id; }
    public void setId(int val) { id = val; }

    public String getNombre() { return nombre; }
    public void setNombre(String val) { nombre = val; }

    public String getEmail() { return email; }
    public void setEmail(String val) { email = val; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String val) { contrasena = val; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String val) { telefono = val; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String val) { direccion = val; }

    public void registrarse() {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void iniciarSesion() {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void actualizarPerfil() {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void cerrarSesion() {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
