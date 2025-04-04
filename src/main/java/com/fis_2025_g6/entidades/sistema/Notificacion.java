package com.fis_2025_g6.entidades.sistema;

import java.util.Date;

import com.fis_2025_g6.entidades.usuarios.Usuario;

public class Notificacion {
    private int id;
    private String mensaje;
    private Date fecha;
    private Usuario usuarioDestino;

    public Notificacion(int id, String mensaje, Date fecha, Usuario usuarioDestino) {
        this.id = id;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.usuarioDestino = usuarioDestino;
    }

    public int getId() { return id; }
    public void setId(int val) { id = val; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String val) { mensaje = val; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date val) { fecha = val; }

    public Usuario getUsuarioDestino() { return usuarioDestino; }
    public void setUsuarioDestino(Usuario val) { usuarioDestino = val; }

    public void enviar(Usuario usuario, String mensaje) {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public String verHistorial(Usuario usuario) {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
