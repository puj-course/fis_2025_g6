package com.fis_2025_g6.Entidades;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Cita implements Serializable {

    private String nombreCliente;
    private String refugio;
    private GregorianCalendar fechaHora;
    private String estado; // Pendiente, Confirmada, Cancelada

    // Constructor
    public Cita(String nombreCliente, String refugio, GregorianCalendar fechaHora) {
        this.nombreCliente = nombreCliente;
        this.refugio = refugio;
        this.fechaHora = fechaHora;
        this.estado = "Pendiente"; // Estado inicial
    }

    // Métodos para cambiar estado
    public void confirmar() {
        this.estado = "Confirmada";
    }

    public void cancelar() {
        this.estado = "Cancelada";
    }

    // Getters y Setters
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getRefugio() { return refugio; }
    public void setRefugio(String refugio) { this.refugio = refugio; }

    public GregorianCalendar getFechaHora() { return fechaHora; }
    public void setFechaHora(GregorianCalendar fechaHora) { this.fechaHora = fechaHora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    
    public void mostrarInfo() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.println("--------------------------------------------");
        System.out.println("Cliente: " + nombreCliente);
        System.out.println("Refugio: " + refugio);
        System.out.println("Fecha y hora: " + formato.format(fechaHora.getTime()));
        System.out.println("Estado de la cita: " + estado);
        System.out.println("--------------------------------------------");
    }
}
