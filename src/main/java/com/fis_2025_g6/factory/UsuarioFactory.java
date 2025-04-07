package com.fis_2025_g6.factory;

import org.springframework.stereotype.Component;

import com.fis_2025_g6.entity.Usuario;

@Component
public abstract class UsuarioFactory {
    public Usuario create(String nombre, String correo, String contrasena, String telefono, String direccion) {
        Usuario usuario = createUsuario(nombre, correo, contrasena, telefono, direccion);
        return usuario;
    }

    protected abstract Usuario createUsuario(String nombre, String correo, String contrasena, String telefono, String direccion);
}
