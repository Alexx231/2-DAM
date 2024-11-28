package onichan.uwu;

import onichan.uwu.Libro;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibreriaService {
 private static final String URL = "jdbc:mysql://localhost:3306/LIBRERIA";
 private static final String USER = "root";
 private static final String PASSWORD = "Tcachuk93";

 public List<Libro> obtenerLibrosPorTematica(String tematica) {
     List<Libro> libros = new ArrayList<>();
     String query = "SELECT l.id_libro, l.titulo, l.autor, l.editorial, l.pvp, t.nombre as tematica " +
                   "FROM LIBRO l JOIN TEMA t ON l.id_tema = t.id_tema WHERE t.nombre LIKE ?";

     try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
          PreparedStatement pstmt = conn.prepareStatement(query)) {
         
         pstmt.setString(1, "%" + tematica + "%");
         ResultSet rs = pstmt.executeQuery();

         while (rs.next()) {
             Libro libro = new Libro();
             libro.setId(rs.getInt("id_libro"));
             libro.setTitulo(rs.getString("titulo"));
             libro.setAutor(rs.getString("autor"));
             libro.setEditorial(rs.getString("editorial"));
             libro.setPvp(rs.getDouble("pvp"));
             libro.setTematica(rs.getString("tematica"));
             libros.add(libro);
         }
     } catch (SQLException e) {
         throw new RuntimeException("Error al consultar libros por temática", e);
     }
     return libros;
 }

 public List<Libro> obtenerTodosLosLibros() {
     List<Libro> libros = new ArrayList<>();
     String query = "SELECT l.id_libro, l.titulo, l.autor, l.editorial, l.pvp, t.nombre as tematica " +
                   "FROM LIBRO l JOIN TEMA t ON l.id_tema = t.id_tema";

     try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery(query)) {

         while (rs.next()) {
             Libro libro = new Libro();
             libro.setId(rs.getInt("id_libro"));
             libro.setTitulo(rs.getString("titulo"));
             libro.setAutor(rs.getString("autor"));
             libro.setEditorial(rs.getString("editorial"));
             libro.setPvp(rs.getDouble("pvp"));
             libro.setTematica(rs.getString("tematica"));
             libros.add(libro);
         }
     } catch (SQLException e) {
         throw new RuntimeException("Error al consultar todos los libros", e);
     }
     return libros;
 }

 public void exportarLibrosCSV(String archivo) {
     List<Libro> libros = obtenerTodosLosLibros();
     try (PrintWriter writer = new PrintWriter(archivo)) {
         writer.println("ID,Título,Autor,Editorial,Temática,Precio");
         for (Libro libro : libros) {
             writer.printf("%d,\"%s\",\"%s\",\"%s\",\"%s\",%.2f\n",
                 libro.getId(),
                 libro.getTitulo().replace("\"", "\"\""),
                 libro.getAutor().replace("\"", "\"\""),
                 libro.getEditorial().replace("\"", "\"\""),
                 libro.getTematica().replace("\"", "\"\""),
                 libro.getPvp());
         }
     } catch (IOException e) {
         throw new RuntimeException("Error al exportar a CSV", e);
     }
 }

 public List<VentasPorTematica> obtenerResumenVentas() {
     List<VentasPorTematica> ventas = new ArrayList<>();
     String query = "SELECT t.nombre, COUNT(d.id_detalle) as total_ventas, " +
                   "SUM(d.unidades * l.pvp) as importe_total " +
                   "FROM TEMA t " +
                   "JOIN LIBRO l ON t.id_tema = l.id_tema " +
                   "JOIN DETALLE d ON l.id_libro = d.id_libro " +
                   "GROUP BY t.nombre";

     try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery(query)) {

         while (rs.next()) {
             VentasPorTematica venta = new VentasPorTematica();
             venta.setTematica(rs.getString("nombre"));
             venta.setTotalVentas(rs.getInt("total_ventas"));
             venta.setImporteTotal(rs.getDouble("importe_total"));
             ventas.add(venta);
         }
     } catch (SQLException e) {
         throw new RuntimeException("Error al consultar resumen de ventas", e);
     }
     return ventas;
 }
}