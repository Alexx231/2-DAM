// src/main/java/onichan/uwu/App.java
package onichan.uwu;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext(AppConfig.class)) {

            LibreriaService libreriaService = context.getBean(LibreriaService.class);
            menuPrincipal(libreriaService, context);
        }
    }

    private static void menuPrincipal(LibreriaService libreriaService, AnnotationConfigApplicationContext context) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE LIBRERÍA ===");
            System.out.println("1. Mostrar libros por temática");
            System.out.println("2. Mostrar todos los libros");
            System.out.println("3. Resumen de ventas por temática");
            System.out.println("4. Exportar libros a CSV");
            System.out.println("5. Ver log de acciones");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (opcion) {
                    case 1:
                        System.out.print("Introduzca la temática: ");
                        String tematica = scanner.nextLine();
                        libreriaService.obtenerLibrosPorTematica(tematica).forEach(libro -> {
                            System.out.println(libro.getTitulo());
                        });
                        break;
                    case 2:
                        libreriaService.obtenerTodosLosLibros().forEach(libro -> {
                            System.out.println(libro.getTitulo());
                        });
                        break;
                    case 3:
                        libreriaService.obtenerResumenVentas().forEach(venta -> {
                            System.out.println(venta.getTematica() + ": " + venta.getTotalVentas() + " ventas, " + venta.getImporteTotal() + "€");
                        });
                        break;
                    case 4:
                        System.out.print("Introduzca la ruta del archivo CSV (o pulse Enter para usar 'libros.csv'): ");
                        String rutaArchivo = scanner.nextLine();
                        if (rutaArchivo.trim().isEmpty()) {
                            rutaArchivo = "libros.csv";
                        }
                        libreriaService.exportarLibrosCSV(rutaArchivo);
                        System.out.println("Archivo CSV generado correctamente en: " + rutaArchivo);
                        break;
                    case 5:
                    	LoggingAspect loggingAspect = context.getBean(LoggingAspect.class);
                        loggingAspect.mostrarLog("TODOS");
                        break;
                    case 6:
                        System.out.println("¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 6);
        
        scanner.close();
    }
}