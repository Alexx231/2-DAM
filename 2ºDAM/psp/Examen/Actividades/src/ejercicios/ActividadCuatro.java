package ejercicios;

import java.io.*;

public class ActividadCuatro {

    public static void main(String[] args) {
        // Intenta leer una línea de la entrada estándar (System.in)
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            // Lee una línea de la entrada
            String line = br.readLine();
            // Convierte la línea leída a un número entero
            int number = Integer.parseInt(line);

            // Imprime la tabla de multiplicar del número proporcionado
            for (int i = 1; i <= 10; i++) {
                System.out.printf("%d x %d = %d%n", number, i, number * i);
            }

        } catch (NumberFormatException e) {
            // Maneja el error si la entrada no es un número válido
            System.err.println("Error: El número proporcionado no es válido.");
        } catch (IOException e) {
            // Maneja el error si ocurre un problema al leer la entrada
            System.err.println("Error: Ocurrió un problema al leer la entrada.");
        }
    }
}