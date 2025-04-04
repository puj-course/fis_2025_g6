package com.fis_2025_g6.entidades.mascotas;

import java.util.Date;

import com.fis_2025_g6.entidades.sistema.adopcion.EstadoAdopcion;

// SOLID: Principio de abierto/cerrado (OCP). Es posible extender a nuevas especies.
public abstract class Mascota {
    private int id;
    private String nombre;
    private int edad;
    private String sexo;
    private String descripcion;
    private Date fechaRegistro;
    private EstadoAdopcion estado = EstadoAdopcion.DISPONIBLE;

    public Mascota(int id, String nombre, int edad, String sexo, String descripcion, Date fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() { return id; }
    public void setId(int val) { id = val; }

    public String getNombre() { return nombre; }
    public void setNombre(String val) { nombre = val; }

    public int getEdad() { return edad; }
    public void setEdad(int val) { edad = val; }

    public String getSexo() { return sexo; }
    public void setSexo(String val) { sexo = val; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String val) { descripcion = val; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date val) { fechaRegistro = val; }

    public EstadoAdopcion getEstado() { return estado; }
    public void setEstado(EstadoAdopcion val) { estado = val; }

    public abstract String getTipo();
}
