package com.fis_2025_g6.entidades.mascotas;

import java.util.Date;

public class Gato extends Mascota {
    public Gato(int id, String nombre, int edad, String sexo, String descripcion, Date fechaRegistro) {
        super(id, nombre, edad, sexo, descripcion, fechaRegistro);
    }

    @Override
    public String getTipo() {
        return "Gato";
    }
}
