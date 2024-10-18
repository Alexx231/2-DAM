package ejercicios;

import java.io.*;

public class ActividadCuatro {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String line = br.readLine();
            int number = Integer.parseInt(line);

            for (int i = 1; i <= 10; i++) {
                System.out.printf("%d x %d = %d%n", number, i, number * i);
            }

        } catch (NumberFormatException e) {
            System.err.println("Error: El número proporcionado no es válido.");
        } catch (IOException e) {
            System.err.println("Error: Ocurrió un problema al leer la entrada.");
        }
    }
}
