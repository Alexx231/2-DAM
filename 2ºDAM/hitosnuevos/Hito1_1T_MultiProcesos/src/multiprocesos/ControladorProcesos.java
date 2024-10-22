package multiprocesos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ControladorProcesos {
    public static void main(String[] args) {
        try {
            // Lanzar el primer examen
            ProcessBuilder pb1 = new ProcessBuilder("java", "-cp", "bin", "multiprocesos.Principal", "Pepe", "Juan", "Luis");
            pb1.redirectOutput(ProcessBuilder.Redirect.to(new File("examen1.txt")));
            Process p1 = pb1.start();

            // Lanzar el segundo examen
            ProcessBuilder pb2 = new ProcessBuilder("java", "-cp", "bin", "multiprocesos.Principal", "Rosa", "Miguel", "Pedro");
            pb2.redirectOutput(ProcessBuilder.Redirect.to(new File("examen2.txt")));
            Process p2 = pb2.start();

            // Esperar a que los procesos terminen
            p1.waitFor();
            p2.waitFor();

            // Verificar la existencia y contenido de los archivos
            verificarArchivo("examen1.txt");
            verificarArchivo("examen2.txt");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void verificarArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            System.out.println("El archivo " + nombreArchivo + " se ha creado correctamente.");
            try {
                String contenido = new String(Files.readAllBytes(Paths.get(nombreArchivo)));
                System.out.println("Contenido del archivo " + nombreArchivo + ":");
                System.out.println(contenido);
            } catch (IOException e) {
                System.out.println("Error al leer el contenido del archivo " + nombreArchivo);
                e.printStackTrace();
            }
        } else {
            System.out.println("El archivo " + nombreArchivo + " no se ha creado.");
        }
    }
}