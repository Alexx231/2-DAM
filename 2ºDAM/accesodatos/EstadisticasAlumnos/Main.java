import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Validación de argumentos
        if (args.length != 2) {
            System.err.println("Uso: java Main <archivoAlumnos> <archivoCalificaciones>");
            System.exit(1);
        }

        String archivoAlumnos = args[0];
        String archivoCalificaciones = args[1];

        try {
            // Instancia de la clase Estadistica
            Estadistica estadistica = new Estadistica(archivoAlumnos, archivoCalificaciones);

            // Generación de todas las estadísticas en el stream System.out
            estadistica.escribirNotasAlumnos(System.out);
            estadistica.escribirNotasMediasAlumnos(System.out);
            estadistica.escribirNotaMediaAlumnos(System.out);
            estadistica.escribirNotaMediaPorAsignatura(System.out);
            estadistica.escribirNotaMediaGlobal(System.out);
        } catch (IOException e) {
            // Manejo de errores
            System.err.println("Error al leer los archivos: " + e.getMessage());
            System.exit(1);
        }
    }
}