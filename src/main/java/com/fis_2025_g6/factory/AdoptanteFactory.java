package com.fis_2025_g6.factory;

import org.springframework.stereotype.Component;

import com.fis_2025_g6.entity.Adoptante;
import com.fis_2025_g6.entity.Usuario;

@Component
public class AdoptanteFactory extends UsuarioFactory {
    @Override
    public Usuario createUsuario(String nombreAdopt, String correo, String contrasena, String telefono, String direccion) {
        Adoptante adoptante = new Adoptante();
        adoptante.setNombre(nombreAdopt);
        adoptante.setCorreo(correo);
        adoptante.setContrasena(contrasena);
        adoptante.setTelefono(telefono);
        adoptante.setDireccion(direccion);
        adoptante.setNombreAdopt(nombreAdopt);
        return adoptante;
    }
}
