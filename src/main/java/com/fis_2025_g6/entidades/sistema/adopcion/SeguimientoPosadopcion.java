package com.fis_2025_g6.entidades.sistema.adopcion;

import java.util.Date;

import com.fis_2025_g6.entidades.mascotas.Mascota;
import com.fis_2025_g6.entidades.usuarios.Adoptante;

public class SeguimientoPosadopcion {
    private int id;
    private Date fecha;
    private String notas;
    private Adoptante adoptante;
    private Mascota mascota;

    public SeguimientoPosadopcion(int id, Date fecha, String notas, Adoptante adoptante, Mascota mascota) {
        this.id = id;
        this.fecha = fecha;
        this.notas = notas;
        this.adoptante = adoptante;
        this.mascota = mascota;
    }

    public int getId() { return id; }
    public void setId(int val) { id = val; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date val) { fecha = val; }

    public String getNotas() { return notas; }
    public void setNotas(String val) { notas = val; }

    public Adoptante getAdoptante() { return adoptante; }
    public void setAdoptante(Adoptante val) { adoptante = val; }

    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota val) { mascota = val; }

    public void registrarSeguimiento(String datos) {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean evaluarCondiciones() {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
