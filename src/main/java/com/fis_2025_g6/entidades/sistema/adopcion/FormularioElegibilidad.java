package com.fis_2025_g6.entidades.sistema.adopcion;

import com.fis_2025_g6.entidades.usuarios.Adoptante;

public class FormularioElegibilidad {
    private int id;
    private Adoptante adoptante;
    private boolean tieneOtrasMascotas;
    private String tipoVivienda;
    private boolean tieneEspacio;
    private int horasFueraDeCasa;
    private boolean compromisoVacunacion;
    private boolean compromisoEsterilizacion;
    private String experienciaPreviaCuidado;

    public FormularioElegibilidad(int id, Adoptante adoptante, boolean tieneOtrasMascotas, String tipoVivienda, boolean tieneEspacio, int horasFueraDeCasa, boolean compromisoVacunacion, boolean compromisoEsterilizacion, String experienciaPreviaCuidado) {
        this.id = id;
        this.adoptante = adoptante;
        this.tieneOtrasMascotas = tieneOtrasMascotas;
        this.tipoVivienda = tipoVivienda;
        this.tieneEspacio = tieneEspacio;
        this.horasFueraDeCasa = horasFueraDeCasa;
        this.compromisoVacunacion = compromisoVacunacion;
        this.compromisoEsterilizacion = compromisoEsterilizacion;
        this.experienciaPreviaCuidado = experienciaPreviaCuidado;
    }

    public int getId() { return id; }
    public void setId(int val) { id = val; }

    public Adoptante getAdoptante() { return adoptante; }
    public void setAdoptante(Adoptante val) { adoptante = val; }

    public boolean isTieneOtrasMascotas() { return tieneOtrasMascotas; }
    public void setTieneOtrasMascotas(boolean val) { tieneOtrasMascotas = val; }

    public String getTipoVivienda() { return tipoVivienda; }
    public void setTipoVivienda(String val) { tipoVivienda = val; }

    public boolean isTieneEspacio() { return tieneEspacio; }
    public void setTieneEspacio(boolean val) { tieneEspacio = val; }

    public int getHorasFueraDeCasa() { return horasFueraDeCasa; }
    public void setHorasFueraDeCasa(int val) { horasFueraDeCasa = val; }

    public boolean isCompromisoVacunacion() { return compromisoVacunacion; }
    public void setCompromisoVacunacion(boolean val) { compromisoVacunacion = val; }

    public boolean isCompromisoEsterilizacion() { return compromisoEsterilizacion; }
    public void setCompromisoEsterilizacion(boolean val) { compromisoEsterilizacion = val; }

    public String getExperienciaPreviaCuidado() { return experienciaPreviaCuidado; }
    public void setExperienciaPreviaCuidado(String val) { experienciaPreviaCuidado = val; }

    public boolean evaluarFormulario() {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public String mostrarFormulario() {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
