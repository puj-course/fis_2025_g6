package controller;

import service.AutenticacionService;

public class ControladorAutenticacion {

    private final AutenticacionService authService = new AutenticacionService();

    public boolean loginAdoptante(String email, String password) {
        return authService.verificarAdoptante(email, password);
    }

    public boolean loginRefugio(String email, String password) {
        return authService.verificarRefugio(email, password);
    }
}
