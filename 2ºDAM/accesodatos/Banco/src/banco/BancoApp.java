package banco;

import java.io.*;
import java.util.Scanner;

public class BancoApp {
    private static final String FILE_NAME = "cuenta.dat";

    public static void main(String[] args) {
        Cuenta cuenta = cargarCuenta();

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("1. Ingresar dinero");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Consultar saldo");
            System.out.println("4. Consultar movimientos");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Cantidad a ingresar: ");
                    double ingreso = scanner.nextDouble();
                    cuenta.ingresar(ingreso);
                    break;
                case 2:
                    System.out.print("Cantidad a retirar: ");
                    double retirada = scanner.nextDouble();
                    cuenta.retirar(retirada);
                    break;
                case 3:
                    System.out.println("Saldo actual: " + cuenta.getSaldo());
                    break;
                case 4:
                    System.out.println("Movimientos:");
                    for (Movimiento movimiento : cuenta.getMovimientos()) {
                        System.out.println(movimiento);
                    }
                    break;
                case 5:
                    guardarCuenta(cuenta);
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 5);
    }

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
            System.out.print("Nombre del cliente: ");
            String nombre = scanner.nextLine();
            System.out.print("DNI del cliente: ");
            String dni = scanner.nextLine();
            Cliente cliente = new Cliente(nombre, dni);
            return new Cuenta(cliente);
        }
        return null;
    }

    private static void guardarCuenta(Cuenta cuenta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(cuenta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}