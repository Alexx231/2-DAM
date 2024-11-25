package ejercicios;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActividadCinco {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso de la Actividad Cinco");
            System.exit(1);
        }

        try {
            int numero = Integer.parseInt(args[0]);
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formateo = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            System.out.println("Tabla de multiplicar del " + numero);
            System.out.println("Generada a las: " + ahora.format(formateo));

            for (int i = 1; i <= 10; i++) {
                System.out.printf("%d x %d = %d%n", numero, i, numero * i);
            }

        } catch (NumberFormatException e) {
            System.err.println("Error: El argumento proporcionado no es un número válido.");
        }
    }
}