package com.fis_2025_g6.dto;

import lombok.Data;

@Data
public class UsuarioDto {
    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;
    private String direccion;
}
