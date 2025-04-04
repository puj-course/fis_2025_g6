package com.fis_2025_g6.entidades.sistema;

// (GoF): Singleton. Si solo necesita una conexión, garantiza una única instancia.
public class SistemaAutenticacion {
    private static SistemaAutenticacion sistemaAutenticacion;

    private SistemaAutenticacion() { }

    public static SistemaAutenticacion getInstancia() {
        if (sistemaAutenticacion == null) {
            sistemaAutenticacion = new SistemaAutenticacion();
        }
        return sistemaAutenticacion;
    }

    public void verificarCredenciales(String email, String contrasena) {
        // TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
