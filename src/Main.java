package interfaz;

import java.util.Calendar;
import java.util.Scanner;

import entidades.CentroAdopcion;
import entidades.Gato;
import entidades.Mascota;
import entidades.Perro;
import entidades.Persona;
import utils.Utils;

public class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    CentroAdopcion PAJ = new CentroAdopcion("Proteccion Animal Javeriana");

    PAJ.loadFromFile();

    while (true) {
      Utils.clearConsole();
      int opt = Utils.printMenu(new String[] {
          "Rescatar una mascota",
          "Adoptar una mascota",
          "Cambiar el nombre de tu mascota",
          "Guarderia",
          "Ver clientes",
          "Salir y Guardar"
      }, "Menu: ");

      switch (opt) {
        case 1:
          System.out.println("Rescatando mascota :D");
          PAJ.rescatarMascota(
              crearMascota());
          System.out.println("Mascota rescatada con exito");
          break;
        case 2: {
          System.out.println("Bienvenido al programa de adopción de " + PAJ.getNombre());
          System.out.print("\nCedula:     ");
          String cedula = Utils.getStringMaxLength(10);

          System.out.println("Estamos comprobando si ya estas en la base de datos...");
          Utils.pause();

          Persona cliente = PAJ.buscarCliente(cedula);

          if (cliente == null) {
            System.out.println(
                "Lo sentimos pero no estas registrado en nuestra base de datos, podrias llenar el siguiente formulario?");
            cliente = pedirDatosPersona(input);

            PAJ.agregarCliente(cliente);
            System.out.println("Registro exitoso!");

            cliente = PAJ.buscarCliente(cedula);
          }

          System.out.println("\nEstas son las mascotas que tenemos en adopción en este momento: ");
          PAJ.mostrarInternos();
          System.out.println("\n");

          Mascota mascotaAdoptar = null;

          while (mascotaAdoptar == null) {
            System.out.print("Dime el nombre de la mascota que quieras adoptar: ");
            String nombre = Utils.scLine();

            mascotaAdoptar = PAJ.buscarMascota(nombre, PAJ.getInternos());
          }

          PAJ.darEnAdopcion(mascotaAdoptar, cliente);
          System.out.println("Felicitaciones, has adoptado a " + mascotaAdoptar.getNombre());

          break;
        }
        case 3: {
          System.out.println("Bienvenido a " + PAJ.getNombre());
          Persona cliente = pedirCedula(PAJ);

          if (cliente != null) {
            cliente.mostrarMascotas();
            System.out.println("\n");
            Mascota m = null;

            while (m == null) {
              System.out.print("Nombre actual de la mascota a la que le deseas cambiar el nombre: ");
              String nombre = Utils.scLine();

              m = cliente.tomarMascotaPorNombre(nombre);
            }
            System.out.print("Y el nombre que le deseas poner: ");
            String nuevoNombre = Utils.scLine();

            cliente.cambiarNombreMascota(m, nuevoNombre);
          }
          break;
        }
        case 4:
          menuGuarderia(PAJ);
          break;
        case 5:
          PAJ.mostrarAdopciones();
          break;
        case 6:
          PAJ.saveToFile();
          Utils.clearConsole();
          System.out.println("ADIOOOOS!");
          Utils.pause();
          return;
      }

      Utils.pause();
    }

  }

  public static void menuGuarderia(CentroAdopcion PAJ) {
    int opt;

    while (true) {
      opt = Utils.printMenu(
          new String[] { "Dejar en guarderia", "Recojer en guarderia", "Interactuar con una mascota", "Salir" },
          "Menu Guarderia");

      switch (opt) {
        case 1: {
          Persona cliente = pedirCedula(PAJ);
          PAJ.dejarMascota(cliente);
          break;
        }
        case 2: {
          Persona cliente = pedirCedula(PAJ);
          Mascota m = null;
          String nombre = "";
          PAJ.mostrarGuarderia();

          System.out.println("\n¿Que mascota desea Recojer?");

          while (m == null) {
            System.out.println("Nombre (0 Para salir): ");
            nombre = Utils.scLine();

            m = PAJ.buscarMascota(nombre, PAJ.getGuarderia());

            if (nombre.equals("0"))
              break;
          }

          PAJ.recojerMascota(nombre, cliente);

          break;
        }
        case 3: {
          PAJ.mostrarGuarderia();
          Mascota m = null;
          String nombre = "";

          System.out.println("\n¿Con que mascota desea interactuar?");

          while (m == null) {
            System.out.println("Nombre (0 Para salir): ");
            nombre = Utils.scLine();

            m = PAJ.buscarMascota(nombre, PAJ.getGuarderia());

            if (nombre.equals("0"))
              break;
          }
          PAJ.interactuar(m);
          break;
        }

        default:
          return;
      }

      Utils.pause();
    }
  }

  public static Persona pedirCedula(CentroAdopcion PAJ) {
    Persona cliente = null;

    while (cliente == null) {
      System.out.print("Permitame su cedula (-1 para salir):     ");
      String cedula = Utils.getStringMaxLength(10);

      if (cedula.equals("-1"))
        break;

      cliente = PAJ.buscarCliente(cedula);

      if (cliente == null) {
        System.out.println("Lo sentimos, esta cedula no esta registrada :(");
      }
    }

    return cliente;
  }

  public static Persona pedirDatosPersona(Scanner input) {
    System.out.print("Nombre del usuario:     ");
    String nombre = Utils.scLine();
    System.out.print("Edad del usuario:       ");
    int edad = Utils.scInt();

    System.out.print("Residencia del usuario: ");
    String residencia = Utils.scLine();
    System.out.print("Cedula del usuario:     ");
    String cedula = Utils.getStringMaxLength(10);

    return new Persona(nombre, edad, residencia, cedula);
  }

  public static Mascota crearMascota() {

    Mascota m = null;

    char gatoOPerro = Utils.askForChar("Es un Gato o un perro? (G/p) ",
        new char[] { 'G', 'g', 'P', 'p' });

    System.out.print("Raza:       ");
    String raza = Utils.scLine();
    System.out.print("Peso:       ");
    float peso = Utils.scFloat();
    System.out.print("Nombre:     ");
    String nombre = Utils.scLine();
    char s = Utils.askForChar("Sexo (F/m): ",
        new char[] { 'F', 'f', 'M', 'm' });
    boolean sexo = (s == 'F' || s == 'f');

    System.out.print("Fecha de nacimiento (dd/mm/yyyy): ");
    Calendar fechaNacimiento = tomarEdadMascota();

    if (gatoOPerro == 'G' || gatoOPerro == 'g')
      m = new Gato(raza, fechaNacimiento, peso, nombre, sexo);
    else
      m = new Perro(raza, fechaNacimiento, peso, nombre, sexo);

    return ((gatoOPerro == 'G' || gatoOPerro == 'g') ? ((Gato) m) : ((Perro) m));
  }

  public static Calendar tomarEdadMascota() {
    try {
      Calendar fechaNacimiento = Utils.scCalendar();
      float years = Utils.getAgeInYears(fechaNacimiento);

      if (years > 30)
        throw new Exception("Esta mascota es demasiado vieja para estar viva");
      return fechaNacimiento;
    } catch (Exception e) {
      System.out.print("\n" + e.getMessage() + ": ");
      Utils.writeLog(e);
      return tomarEdadMascota();
    }

  }
}