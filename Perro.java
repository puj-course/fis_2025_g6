package entidades;

import java.util.Calendar;
import java.util.GregorianCalendar;

import utils.Utils;

public class Perro extends Mascota {
  private Calendar fechaBanio;
  private boolean limpio;

  public void setFechaBanio(Calendar fechaBanio) {
    this.fechaBanio = fechaBanio;
  }

  public void setLimpio(boolean limpio) {
    this.limpio = limpio;
  }

  public Calendar getFechaBanio() {
    return fechaBanio;
  }

  public boolean getLimpio() {
    return this.limpio;
  }

  public Perro(String raza, Calendar fechaNacimiento, float peso, String nombre, boolean sexo) {
    super(raza, fechaNacimiento, peso, nombre, sexo);
  }

  public Perro(String raza, Calendar fechaNacimiento, float peso, String nombre, boolean sexo, Calendar fechaBanio,
      boolean limpio) {
    super(raza, fechaNacimiento, peso, nombre, sexo);
    this.fechaBanio = fechaBanio;
    this.limpio = limpio;
  }

  public void baniar() {
    this.fechaBanio = new GregorianCalendar();
    this.limpio = true;
  }

  @Override
  public void jugar() {
    int opt = Utils.printMenu(new String[] { "Tirar la pelota", "Salir al parque", "Jugar con otros perros" },
        "¿Como quieres jugar con " + super.getNombre() + "?");

    switch (opt) {
      case 1:
        Utils.clearConsole();
        System.out.println("Tiraste la pelota y " + super.getNombre() + " fue por ella, se ve feliz.");
        break;
      case 2:
        Utils.clearConsole();
        System.out.println("Saliste al parque con " + super.getNombre() + ", se ve feliz.");
        break;
      case 3:
        Utils.clearConsole();
        System.out.println(super.getNombre() + " esta jugando con otros perros, se ve feliz.");
        this.limpio = false;
        break;
    }

    Utils.pause();
  }
}