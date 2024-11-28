package ejercicio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author alejandro.pawlukiewicz
 */
public class Actividad {
    public static void main(String[] args) {

        File carpeta = new File("C:\\2ºDAM\\accesodatos");

        if (!carpeta.exists()) {
            System.out.println("La carpeta 'accesodatos' no existe.");
            return;
        }

        File archivo1 = new File(carpeta, "fich1.txt");
        File archivo2 = new File(carpeta, "fich2.txt");

        mostrarInformacion(carpeta);
        mostrarInformacion(archivo1);
        mostrarInformacion(archivo2);
    }

    private static void mostrarInformacion(File file) {
        System.out.println("Información de: " + file.getName());
        System.out.println("Ruta absoluta: " + file.getAbsolutePath());
        System.out.println("Es carpeta: " + file.isDirectory());
        System.out.println("Es archivo: " + file.isFile());
        System.out.println("Tamaño (bytes): " + file.length());
        if (file.isDirectory()) {
            String[] archivos = file.list();
            if (archivos != null) {
                System.out.println("Número de archivos contenidos: " + archivos.length);
                for (String archivo : archivos) {
                    System.out.println(" - " + archivo);
                }
            }
        } else if (file.isFile() && file.getName().endsWith(".txt")) {
            try {
                String contenido = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                System.out.println("Contenido del archivo:");
                System.out.println(contenido);
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        }
        System.out.println("---------------------");
    }
}