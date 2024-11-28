package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EjercicioScanner {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Ruta del archivo incorrecta ");
            System.exit(1);
        }

        File archivo = new File(args[0]);

        if (!archivo.exists()) {
            System.err.println("El archivo " + args[0] + " no existe.");
            System.exit(1);
        }

        System.out.println("El archivo ocupa " + archivo.length() + " bytes.");

        int totalArticulos = 0;
        double sumaPrecios = 0;
        double importeTotal = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");
                if (campos.length == 4) {
                    String categoria = campos[0];
                    String nombre = campos[1];
                    double precio = Double.parseDouble(campos[2]);
                    int stock = Integer.parseInt(campos[3]);

                    System.out.printf("%s (%s) -- Precio: %.2f € -- Stock: %d%n", nombre, categoria, precio, stock);

                    totalArticulos++;
                    sumaPrecios += precio;
                    importeTotal += precio * stock;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        double promedioPrecios = sumaPrecios / totalArticulos;

        System.out.println("\nNúmero total de artículos: " + totalArticulos);
        System.out.printf("Promedio de precios: %.2f €%n", promedioPrecios);
        System.out.printf("Importe total: %.2f €%n", importeTotal);
    }
}