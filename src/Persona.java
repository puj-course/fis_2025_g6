package entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import utils.Utils;

public class Persona implements Serializable {
  private String nombre;
  private int edad;
  private String residencia;
  private String cedula;

  private ArrayList<Mascota> mascotas;

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setEdad(int edad) {
    this.edad = edad;
  }

  public void setResidencia(String residencia) {
    this.residencia = residencia;
  }

  public void setCedula(String cedula) {
    this.cedula = cedula;
  }

  public void setMascotas(ArrayList<Mascota> mascotas) {
    this.mascotas = mascotas;
  }

  public String getNombre() {
    return nombre;
  }

  public int getEdad() {
    return edad;
  }

  public String getResidencia() {
    return residencia;
  }

  public String getCedula() {
    return cedula;
  }

  public ArrayList<Mascota> getMascotas() {
    return mascotas;
  }

  public Persona(String nombre, int edad, String residencia, String cedula) {
    this.nombre = nombre;
    this.edad = edad;
    this.residencia = residencia;
    this.cedula = cedula;

    this.mascotas = new ArrayList<>();
  }

  public void adoptarMascota(Mascota mascota) {
    mascotas.add(mascota);
    mascota.setDueno(this);
    mascota.setFechaAdopcion(
        new GregorianCalendar());
  }

  public void recojerMascota(Mascota mascota) {
    if (mascota.getDueno().getCedula().equals(this.getCedula()))
      mascotas.add(mascota);
  }

  public void cambiarNombreMascota(Mascota mascota, String nuevoNombre) {
    mascota.setNombre(nuevoNombre);
  }

  public void dejarAlCuidado(Mascota m) {
    mascotas.remove(m);
  }

  public void mostrarMascotas() {
    int size = 62;
    System.out.println("+----------------------------------------------------+");
    System.out.println("|                      MASCOTAS                      |");
    for (Mascota m : mascotas) {
      Utils.imprimirMascota(m, size);
    }
    System.out.println("+-------------+--------------------------------------+");
  }

  public Mascota tomarMascotaPorNombre(String nombre) {
    Mascota mascota = null;

    for (Mascota m : mascotas)
      if (m.getNombre().equals(nombre))
        mascota = m;

    return mascota;
  }

}