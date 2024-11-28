package es.simulacro;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


/**
 * Versión sin gestión de errores y excepciones.
 * @throws SQLException
 * @throws IOException
 */
public class Libreria {
	Scanner scanner = new Scanner(System.in);
	
	void menu() throws SQLException, IOException {
		int opcion = 0;

		do {
			System.out.println("==Menu===");
			System.out.println("1.- Listar todos los libros de una temática");
			System.out.println("2.- Listar todos los libros");
			System.out.println("3.- Resumen de ventas por temática");
			System.out.println("4.- Exportar todos los libros a CSV");
			System.out.println("5.- Salir");
			System.out.println("=========");
			System.out.println();
			
			System.out.print("Opción: ");
			opcion = scanner.nextInt();
			scanner.nextLine();
			switch(opcion) {
				case 1:
					listarTodosLosLibrosPorTematica();
					break;
				case 2:
					listarTodosLosLibros();
					break;
				case 3:
					resumenDeVentasPorTematica();
					break;
				case 4:
					exportarTodosLosLibrosCSV();
					break;
				case 5:
					salir();
					break;
			}
		} while (opcion != 5);
	}
	
	void listarTodosLosLibrosPorTematica() throws SQLException {
		System.out.println("Listar todos los libros por tema");

		// leemos el tema
		System.out.print("Tema: ");
		String tema = scanner.nextLine();

		System.out.println("Libros del tema " + tema);

		// buscamos el id asociado al tema
		String sqlTema = "SELECT id_tema FROM TEMA WHERE nombre=" + "\"" + tema + "\"";
		Statement statementTema = ConexionBD.getConexion().createStatement();
		ResultSet rsTema = statementTema.executeQuery(sqlTema);

		// OJO damos por echo que el tema existe
		rsTema.next();
		int idTema = rsTema.getInt("id_tema");

		// iteramos los libros del tema
		String sqlLibro = "SELECT * FROM LIBRO WHERE id_tema=" + idTema;
		Statement statementLibro = ConexionBD.getConexion().createStatement();
		ResultSet rsLibro = statementLibro.executeQuery(sqlLibro);
		while (rsLibro.next()) {
			// leemos los campos de un libro
			String titulo = rsLibro.getString("titulo");
			String autor = rsLibro.getString("autor");
			String editorial = rsLibro.getString("editorial");
			String descripcion = rsLibro.getString("descripcion");
			double pvp = rsLibro.getDouble("pvp");
			
			System.out.println(titulo + " -- " + autor + " -- " + editorial  + " -- " + descripcion + " -- " + pvp);
		}
		System.out.println("");
	}
	
	void listarTodosLosLibros() throws SQLException {
		System.out.println("Listar todos los libros");
		
		// iteramos los libros
		String sqlLibro = "SELECT * FROM LIBRO";
		Statement statementLibro = ConexionBD.getConexion().createStatement();
		ResultSet rsLibro = statementLibro.executeQuery(sqlLibro);
		while (rsLibro.next()) {
			// leemos los campos de un libro
			String titulo = rsLibro.getString("titulo");
			String autor = rsLibro.getString("autor");
			String editorial = rsLibro.getString("editorial");
			String descripcion = rsLibro.getString("descripcion");
			double pvp = rsLibro.getDouble("pvp");

			// obtenemos el tema del libro
			int idTema = rsLibro.getInt("id_tema");
			String sqlTema = "SELECT nombre FROM TEMA WHERE id_tema=" + idTema;
			Statement statementTema = ConexionBD.getConexion().createStatement();
			ResultSet rsTema = statementTema.executeQuery(sqlTema);
			rsTema.next();
			String tema = rsTema.getString("nombre");

			System.out.println(tema + " -- " + titulo + " -- " + autor + " -- " + editorial  + " -- " + descripcion + " -- " + pvp);
		}
		System.out.println("");
	}
	
	void resumenDeVentasPorTematica() throws SQLException {
		System.out.println("Resumen de ventas por tema");

		// iteramos por temas
		String sqlTema = "SELECT * FROM TEMA";
		Statement statementTema = ConexionBD.getConexion().createStatement();
		ResultSet rsTema = statementTema.executeQuery(sqlTema);
		while (rsTema.next()) {
			int idTema = rsTema.getInt("id_tema");
			String tema = rsTema.getString("nombre");
			
			// acumulador para las ventas
			double ventas = 0;

			// iteramos por los libros del tema
			String sqlLibro = "SELECT * FROM LIBRO WHERE id_tema=" + idTema;
			Statement statementLibro = ConexionBD.getConexion().createStatement();
			ResultSet rsLibro = statementLibro.executeQuery(sqlLibro);
			while (rsLibro.next()) {
				int idLibro = rsLibro.getInt("id_libro");
				double pvp = rsLibro.getDouble("pvp");

				// iteramos por las lineas de detalle con ese libro
				// Alternativa a iterar:  String sqlDetalle = "SELECT COUNT(*) FROM DETALLE WHERE id_libro=" + idLibro;
				String sqlDetalle = "SELECT * FROM DETALLE WHERE id_libro=" + idLibro;
				Statement statementDetalle = ConexionBD.getConexion().createStatement();
				ResultSet rsDetalle = statementDetalle.executeQuery(sqlDetalle);
				while (rsDetalle.next()) {
					//	acumulamos las ventas del libro
					ventas += pvp;
				}
			}
			
			//	mostramos las ventas por tema
			System.out.println("Ventas del tema " + tema + " :" + ventas);
		}
		
		System.out.println("");
	}
	
	void exportarTodosLosLibrosCSV() throws FileNotFoundException, SQLException {
		System.out.println("Exportar todos los libros en formato CSV");
		
		// leemos el nombre del archivo CSV
		System.out.print("Nombre del archivo CSV: ");
		String archivoCSV = scanner.nextLine();

		PrintWriter pwCSV = new PrintWriter(archivoCSV);
		
		//	escribimos la cabecera del archivo CSV
		pwCSV.println("titulo|autor|editorial|descripcion|pvp");
 		
		// iteramos los libros
		String sqlLibro = "SELECT * FROM LIBRO";
		Statement statementLibro = ConexionBD.getConexion().createStatement();
		ResultSet rsLibro = statementLibro.executeQuery(sqlLibro);
		while (rsLibro.next()) {
			// leemos los campos de un libro
			String titulo = rsLibro.getString("titulo");
			String autor = rsLibro.getString("autor");
			String editorial = rsLibro.getString("editorial");
			String descripcion = rsLibro.getString("descripcion");
			double pvp = rsLibro.getDouble("pvp");

			//	escribimos la linea en el CSV
			pwCSV.println(titulo + "|" + autor + "|" + editorial + "|" + descripcion + "|" + pvp);
		}
		pwCSV.close();
		System.out.println("");
	}

	void visualizarLogDeActividad() throws IOException {
		System.out.println("Log de actividad");
		
		String archivoLogActividad = "log-actividad.log";
		Files.lines(Paths.get(archivoLogActividad))
			.forEach(System.out::println);
		
		System.out.println("");
	}
	
	void salir() {
		System.out.println("¡Saliendo!");
	}
	
	public static void main(String[] args) {
		ConexionBD.getConexion();
		System.out.println("");
		
		Libreria libreria = new Libreria();
		
		// si se lanza un excepción, ponemos su mensaje descriptivo y volvemos de nuevo al menu
		while (true) {
			try {
				libreria.menu();
				break;
			} catch (Exception e) {
				System.err.println("Vuelve a intentarlo. Ha ocurrido este error: " + e.getMessage());
				e.printStackTrace(System.err);
				System.err.println("PULSA INTRO SI NO SALE EL MENÚ");
				libreria.scanner.nextLine();
			}
		}
	}
}

