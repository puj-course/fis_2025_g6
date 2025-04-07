package com.fis_2025_g6.factory;

import org.springframework.stereotype.Component;

import com.fis_2025_g6.entity.Refugio;
import com.fis_2025_g6.entity.Usuario;

@Component
public class RefugioFactory extends UsuarioFactory {
    @Override
    public Usuario createUsuario(String nombreRefg, String correo, String contrasena, String telefono, String direccion) {
        Refugio adoptante = new Refugio();
        adoptante.setNombre(nombreRefg);
        adoptante.setCorreo(correo);
        adoptante.setContrasena(contrasena);
        adoptante.setTelefono(telefono);
        adoptante.setDireccion(direccion);
        adoptante.setNombreRefg(nombreRefg);
        return adoptante;
    }
}
