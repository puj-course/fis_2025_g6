package com.fis_2025_g6.entidades.sistema.adopcion;

import java.util.Date;

import com.fis_2025_g6.entidades.mascotas.Mascota;
import com.fis_2025_g6.entidades.usuarios.Adoptante;

public class SolicitudAdopcion {
    private int id;
    private Date fecha;
    private EstadoSolicitud estado;
    private Adoptante adoptante;
    private Mascota mascota;

    public SolicitudAdopcion(int id, Date fecha, Adoptante adoptante, Mascota mascota) {
        this.id = id;
        this.fecha = fecha;
        this.estado = EstadoSolicitud.PENDIENTE;
        this.adoptante = adoptante;
        this.mascota = mascota;
    }

    public int getId() { return id; }
    public void setId(int val) { id = val; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date val) { fecha = val; }

    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud val) { estado = val; }

    public Adoptante getAdoptante() { return adoptante; }
    public void setAdoptante(Adoptante val) { adoptante = val; }

    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota val) { mascota = val; }
}
