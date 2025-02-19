package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Cliente {
    private static IServicioCanciones servicio;
    private static Scanner scanner;

    public static void main(String[] args) {
        try {
            // Obtener referencia del registro
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            servicio = (IServicioCanciones) registry.lookup("ServicioCanciones");
            scanner = new Scanner(System.in);

            boolean salir = false;
            while (!salir) {
                mostrarMenu();
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        listarCanciones();
                        break;
                    case 2:
                        buscarPorTitulo();
                        break;
                    case 3:
                        obtenerDetalles();
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
                System.out.println("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }

        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.toString());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== GESTIÓN DE CANCIONES ===");
        System.out.println("1. Listar todas las canciones");
        System.out.println("2. Buscar canción por título");
        System.out.println("3. Obtener detalles por ID");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void listarCanciones() {
        try {
            List<Cancion> canciones = servicio.listarCanciones();
            System.out.println("\n=== LISTADO DE CANCIONES ===");
            canciones.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Error al listar canciones: " + e.getMessage());
        }
    }

    private static void buscarPorTitulo() {
        try {
            System.out.print("\nIngrese el título a buscar: ");
            String titulo = scanner.nextLine();
            Cancion cancion = servicio.buscarPorTitulo(titulo);
            System.out.println("\n=== RESULTADO DE LA BÚSQUEDA ===");
            if (cancion != null) {
                System.out.println(cancion);
            } else {
                System.out.println("No se encontró la canción");
            }
        } catch (Exception e) {
            System.err.println("Error al buscar canción: " + e.getMessage());
        }
    }

    private static void obtenerDetalles() {
        try {
            System.out.print("\nIngrese el ID de la canción: ");
            int id = scanner.nextInt();
            Cancion cancion = servicio.obtenerDetalles(id);
            System.out.println("\n=== DETALLES DE LA CANCIÓN ===");
            if (cancion != null) {
                System.out.println(cancion);
            } else {
                System.out.println("No se encontró la canción con ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener detalles: " + e.getMessage());
        }
    }
}