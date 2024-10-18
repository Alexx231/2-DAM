package ejercicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ClaseScanner{
    public static void main(String[] args) {
        String rutaArchivo = "C:\\Users\\holas\\Desktop\\productos.txt";
        File archivo = new File(rutaArchivo);

        // Comprobar si el archivo existe y mostrar el tamaño en bytes
        if (!archivo.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }
        System.out.println("El archivo ocupa " + archivo.length() + " bytes.");

        int numeroTotalArticulos = 0;
        double sumaPrecios = 0.0;
        double importeTotal = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Dividir la línea en partes
                String[] partes = linea.split(";");
                if (partes.length != 4) {
                    System.out.println("Línea malformada: " + linea);
                    continue;
                }

                String categoria = partes[0];
                String nombre = partes[1];
                double precio = Double.parseDouble(partes[2]);
                int stock = Integer.parseInt(partes[3]);

                // Mostrar la información del producto
                System.out.printf("%s (%s) -- Precio: %.2f € -- Stock: %d%n", nombre, categoria, precio, stock);

                // Actualizar los cálculos
                numeroTotalArticulos++;
                sumaPrecios += precio;
                importeTotal += precio * stock;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Mostrar los datos finales
        double promedioPrecios = sumaPrecios / numeroTotalArticulos;
        System.out.println("Número total de artículos: " + numeroTotalArticulos);
        System.out.printf("Promedio de precios: %.2f €%n", promedioPrecios);
        System.out.printf("Importe total: %.2f €%n", importeTotal);
    }
}