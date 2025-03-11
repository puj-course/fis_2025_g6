package entidades;

import java.util.Calendar;

import utils.Utils;

public class Gato extends Mascota {

  private boolean uniasLargas;

  public void setUniasLargas(boolean uniasLargas) {
    this.uniasLargas = uniasLargas;
  }

  public boolean getUniasLargas() {
    return this.uniasLargas;
  }

  public Gato(String raza, Calendar fechaNacimiento, float peso, String nombre, boolean sexo) {
    super(raza, fechaNacimiento, peso, nombre, sexo);
  }

  public Gato(String raza, Calendar fechaNacimiento, float peso, String nombre, boolean sexo, boolean uniasLargas) {
    super(raza, fechaNacimiento, peso, nombre, sexo);
    this.uniasLargas = uniasLargas;
  }

  public void cortarUnias() {
    this.uniasLargas = false;
  }

  @Override
  public void jugar() {
    int opt = Utils.printMenu(new String[] { "Afilar uñas", "Acariciar" },
        "¿Como quieres jugar con " + super.getNombre() + "?");

    switch (opt) {
      case 1:
        Utils.clearConsole();
        System.out.println("Afilaste las uñas de " + super.getNombre() + ", tus brazos no estan felices...");
        this.uniasLargas = true;
        break;
      case 2:
        Utils.clearConsole();
        System.out.println("Acariciaste a " + super.getNombre() + " y no queria :(, así son los gatos...");
        break;
    }

    Utils.pause();
  }
}