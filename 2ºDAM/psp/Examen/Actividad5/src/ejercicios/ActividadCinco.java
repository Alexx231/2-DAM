package ejercicios;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActividadCinco {

    public static void main(String[] args) {
        // Verifica que se haya pasado un argumento
        if (args.length != 1) {
            System.err.println("Uso de la Actividad Cinco");
            System.exit(1);
        }

        try {
            // Convierte el argumento a un número entero
            int numero = Integer.parseInt(args[0]);
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formateo = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Imprime la tabla de multiplicar del número proporcionado
            System.out.println("Tabla de multiplicar del " + numero);
            System.out.println("Generada a las: " + ahora.format(formateo));

            // Genera e imprime la tabla de multiplicar del 1 al 10
            for (int i = 1; i <= 10; i++) {
                System.out.printf("%d x %d = %d%n", numero, i, numero * i);
            }

        } catch (NumberFormatException e) {
            // Maneja el caso en que el argumento no sea un número válido
            System.err.println("Error: El argumento proporcionado no es un número válido.");
        }
    }
}