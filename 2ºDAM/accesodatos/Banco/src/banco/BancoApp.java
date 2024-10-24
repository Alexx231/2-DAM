package banco;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BancoApp {
    private static final String FILE_NAME = "cuenta.dat";

    public static void main(String[] args) {
        Cuenta cuenta = cargarCuenta();
        if (cuenta == null) {
            System.out.println("Error al cargar la cuenta. Saliendo del programa.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int opcion;

        // Bucle principal del menú
        do {
            mostrarMenu(); // Muestra el menú principal
            opcion = leerOpcion(scanner); // Lee la opción seleccionada por el usuario

            // Ejecuta la acción correspondiente a la opción seleccionada
            switch (opcion) {
                case 1:
                    ingresarDinero(scanner, cuenta); // Ingresa dinero en la cuenta
                    break;
                case 2:
                    retirarDinero(scanner, cuenta); // Retira dinero de la cuenta
                    break;
                case 3:
                    consultarSaldo(cuenta); // Consulta el saldo actual de la cuenta
                    break;
                case 4:
                    consultarMovimientos(cuenta); // Consulta los movimientos de la cuenta
                    break;
                case 5:
                    eliminarCuenta(); // Elimina el archivo de la cuenta
                    cuenta = cargarCuenta(); // Carga una nueva cuenta
                    break;
                case 6:
                    guardarCuenta(cuenta); // Guarda la cuenta en un archivo
                    System.out.println("¡Hasta la próxima!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } while (opcion != 6); // Repite el bucle hasta que el usuario elija salir
    }

    // Muestra el menú principal
    private static void mostrarMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Ingresar dinero");
        System.out.println("2. Retirar dinero");
        System.out.println("3. Consultar saldo");
        System.out.println("4. Consultar movimientos");
        System.out.println("5. Eliminar cuenta y crear una nueva");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // Lee la opción seleccionada por el usuario y maneja entradas no válidas
    private static int leerOpcion(Scanner scanner) {
        int opcion = -1;
        try {
            opcion = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Por favor, ingrese un número.");
            scanner.next(); // Limpiar el buffer
        }
        return opcion;
    }

    // Ingresa dinero en la cuenta
    private static void ingresarDinero(Scanner scanner, Cuenta cuenta) {
        System.out.print("Cantidad a ingresar: ");
        try {
            double ingreso = scanner.nextDouble();
            if (ingreso > 0) {
                cuenta.ingresar(ingreso);
                System.out.println("Ingreso realizado con éxito.");
            } else {
                System.out.println("La cantidad debe ser mayor que cero.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Por favor, ingrese un número.");
            scanner.next(); // Limpiar el buffer
        }
    }

    // Retira dinero de la cuenta
    private static void retirarDinero(Scanner scanner, Cuenta cuenta) {
        System.out.print("Cantidad a retirar: ");
        try {
            double retirada = scanner.nextDouble();
            if (retirada > 0 && retirada <= cuenta.getSaldo()) {
                cuenta.retirar(retirada);
                System.out.println("Retirada realizada con éxito.");
            } else {
                System.out.println("La cantidad debe ser mayor que cero y no exceder el saldo disponible.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Por favor, ingrese un número.");
            scanner.next(); // Limpiar el buffer
        }
    }

    // Consulta el saldo actual de la cuenta
    private static void consultarSaldo(Cuenta cuenta) {
        System.out.println("Saldo actual: " + cuenta.getSaldo() + "€");
    }

    // Consulta los movimientos de la cuenta
    private static void consultarMovimientos(Cuenta cuenta) {
        System.out.println("Movimientos:");
        for (Movimiento movimiento : cuenta.getMovimientos()) {
            System.out.println(movimiento);
        }
    }

    // Carga la cuenta desde un archivo o crea una nueva si no existe
    private static Cuenta cargarCuenta() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (Cuenta) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            String nombre;
            while (true) {
                System.out.print("Nombre del cliente: ");
                nombre = scanner.nextLine();
                if (nombre.matches("[a-zA-Z\\s]+")) { // Para verificar que el nombre del usuario no lleva ningun numero.
                    break;
                } else {
                    System.out.println("El nombre debe contener solo letras y espacios. Inténtelo de nuevo.");
                }
            }
            System.out.print("DNI del cliente: ");
            String dni = scanner.nextLine();
            Cliente cliente = new Cliente(nombre, dni);
            return new Cuenta(cliente);
        }
        return null;
    }

    // Guarda la cuenta en un archivo
    private static void guardarCuenta(Cuenta cuenta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(cuenta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Elimina el archivo de la cuenta
    private static void eliminarCuenta() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Cuenta eliminada con éxito.");
            } else {
                System.out.println("Error al eliminar la cuenta.");
            }
        } else {
            System.out.println("No existe ninguna cuenta para eliminar.");
        }
    }
}