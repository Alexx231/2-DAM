package examen;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GestionLibreria {
    private static final String URL = "jdbc:mysql://localhost:3306/LIBRERIA";
    private static final String USER = "root";
    private static final String PASSWORD = "Tcachuk93";
    private static final String LOG_FILE = "libreria_log.txt";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            menuPrincipal();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se pudo cargar el driver de MySQL");
            e.printStackTrace();
        }
    }

    private static void menuPrincipal() {
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
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    mostrarLibrosPorTematica();
                    break;
                case 2:
                    mostrarTodosLosLibros();
                    break;
                case 3:
                    mostrarResumenVentas();
                    break;
                case 4:
                    exportarLibrosCSV();
                    break;
                case 5:
                    mostrarLog();
                    break;
                case 6:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 6);
    }

    private static void mostrarLibrosPorTematica() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduzca la temática: ");
        String tematica = scanner.nextLine();

        String query = "SELECT l.titulo, l.autor, l.editorial, l.pvp FROM LIBRO l " +
                      "JOIN TEMA t ON l.id_tema = t.id_tema " +
                      "WHERE t.nombre LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + tematica + "%");
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nLibros encontrados:");
            while (rs.next()) {
                System.out.printf("Título: %s | Autor: %s | Editorial: %s | Precio: %.2f€\n",
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("editorial"),
                        rs.getDouble("pvp"));
            }
            registrarAccion("Consulta de libros por temática: " + tematica);
        } catch (SQLException e) {
            System.out.println("Error al consultar los libros: " + e.getMessage());
        }
    }

    private static void mostrarTodosLosLibros() {
        String query = "SELECT l.titulo, l.autor, t.nombre as tematica, l.pvp FROM LIBRO l " +
                      "JOIN TEMA t ON l.id_tema = t.id_tema";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\nListado completo de libros:");
            while (rs.next()) {
                System.out.printf("Título: %s | Autor: %s | Temática: %s | Precio: %.2f€\n",
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("tematica"),
                        rs.getDouble("pvp"));
            }
            registrarAccion("Consulta de todos los libros");
        } catch (SQLException e) {
            System.out.println("Error al consultar los libros: " + e.getMessage());
        }
    }

    private static void mostrarResumenVentas() {
        String query = "SELECT t.nombre, COUNT(d.id_detalle) as total_ventas, " +
                      "SUM(d.unidades * l.pvp) as importe_total " +
                      "FROM TEMA t " +
                      "JOIN LIBRO l ON t.id_tema = l.id_tema " +
                      "JOIN DETALLE d ON l.id_libro = d.id_libro " +
                      "GROUP BY t.nombre";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\nResumen de ventas por temática:");
            while (rs.next()) {
                System.out.printf("Temática: %s | Ventas: %d | Importe Total: %.2f€\n",
                        rs.getString("nombre"),
                        rs.getInt("total_ventas"),
                        rs.getDouble("importe_total"));
            }
            registrarAccion("Consulta de resumen de ventas");
        } catch (SQLException e) {
            System.out.println("Error al consultar las ventas: " + e.getMessage());
        }
    }

    private static void exportarLibrosCSV() {
        String query = "SELECT l.titulo, l.autor, l.editorial, t.nombre as tematica, l.pvp " +
                      "FROM LIBRO l JOIN TEMA t ON l.id_tema = t.id_tema";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             PrintWriter writer = new PrintWriter("libros.csv")) {

            writer.println("Título,Autor,Editorial,Temática,Precio");

            while (rs.next()) {
                writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",%.2f\n",
                        rs.getString("titulo").replace("\"", "\"\""),
                        rs.getString("autor").replace("\"", "\"\""),
                        rs.getString("editorial").replace("\"", "\"\""),
                        rs.getString("tematica").replace("\"", "\"\""),
                        rs.getDouble("pvp"));
            }

            System.out.println("Archivo CSV generado correctamente");
            registrarAccion("Exportación de libros a CSV");
        } catch (SQLException | FileNotFoundException e) {
            System.out.println("Error al exportar los libros: " + e.getMessage());
        }
    }

    private static void mostrarLog() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String linea;
            System.out.println("\nRegistro de acciones:");
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de log: " + e.getMessage());
        }
    }

    private static void registrarAccion(String accion) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.println(timestamp + " - " + accion);
        } catch (IOException e) {
            System.out.println("Error al registrar la acción: " + e.getMessage());
        }
    }
}