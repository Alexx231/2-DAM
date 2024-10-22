package multiprocesos;

import java.io.IOException;

public class ControladorProcesos {
    public static void main(String[] args) {
        try {
            // Lanzar el primer examen
            ProcessBuilder pb1 = new ProcessBuilder("java", "-cp", "C:\\2ºDAM\\psp\\Hito1_1T\\bin", "multiprocesos.Principal", "Pepe", "Juan", "Luis");
            pb1.redirectOutput(ProcessBuilder.Redirect.to(new java.io.File("examen1.txt")));
            pb1.start();

            // Lanzar el segundo examen
            ProcessBuilder pb2 = new ProcessBuilder("java", "-cp", "C:\\2ºDAM\\psp\\Hito1_1T\\bin", "multiprocesos.Principal", "Rosa", "Miguel", "Pedro");
            pb2.redirectOutput(ProcessBuilder.Redirect.to(new java.io.File("examen2.txt")));
            pb2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}