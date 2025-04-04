package com.fis_2025_g6.entidades.sistema;

import java.util.ArrayList;
import java.util.Date;

import com.fis_2025_g6.entidades.usuarios.Adoptante;
import com.fis_2025_g6.entidades.usuarios.Refugio;

public class Donacion {
    private int id;
    private double monto;
    private Date fecha;
    private Refugio destino;

    public Donacion(int id, double monto, Date fecha, Refugio destino) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.destino = destino;
    }

    public int getId() { return id; }
    public void setId(int val) { id = val; }

    public double getMonto() { return monto; }
    public void setMonto(double val) { monto = val; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date val) { fecha = val; }

    public Refugio getDestino() { return destino; }
    public void setDestino(Refugio val) { destino = val; }

    public ArrayList<Donacion> verHistorial(Adoptante adoptante) {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
