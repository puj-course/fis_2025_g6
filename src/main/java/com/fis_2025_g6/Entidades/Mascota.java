package com.fis_2025_g6.Entidades;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Mascota implements Serializable {
    private String nombre;
    private int edad;
    private String especie;
    private String raza;
    private String salud;
    private boolean adoptada;
    private GregorianCalendar fechaAdopcion;

    // Constructor
    public Mascota(String nombre, int edad, String especie, String raza, String salud) {
        this.nombre = nombre;
        this.edad = edad;
        this.especie = especie;
        this.raza = raza;
        this.salud = salud;
        this.adoptada = false;  // Al inicio la mascota está disponible
        this.fechaAdopcion = null;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public String getSalud() { return salud; }
    public void setSalud(String salud) { this.salud = salud; }

    public boolean isAdoptada() { return adoptada; }
    public void setAdoptada(boolean adoptada) { this.adoptada = adoptada; }

    public GregorianCalendar getFechaAdopcion() { return fechaAdopcion; }
    public void setFechaAdopcion(GregorianCalendar fechaAdopcion) { this.fechaAdopcion = fechaAdopcion; }

    // Método para adoptar una mascota
    public void adoptar() {
        if (!adoptada) {
            this.adoptada = true;
            this.fechaAdopcion = new GregorianCalendar();
            System.out.println("Mascota " + nombre + " ha sido adoptada.");
        } else {
            System.out.println("Error: La mascota " + nombre + " ya ha sido adoptada.");
        }
    }

    // Método para mostrar la información de la mascota
    public void mostrarInfo() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Edad: " + edad + " años");
        System.out.println("Especie: " + especie);
        System.out.println("Raza: " + raza);
        System.out.println("Estado de salud: " + salud);
        System.out.println("Estado: " + (adoptada ? "Adoptada" : "Disponible"));
        if (adoptada) {
            System.out.println("Fecha de adopción: " + fechaAdopcion.getTime());
        }
    }
}

