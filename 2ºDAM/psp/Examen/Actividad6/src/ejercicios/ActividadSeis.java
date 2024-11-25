package ejercicios;

import java.io.*;

public class ActividadSeis {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: java ejercicios.ActividadSeis <base>");
            System.exit(1);
        }

        try {
            int base = Integer.parseInt(args[0]);

            StringBuilder triangle = new StringBuilder();
            for (int i = base; i >= 1; i--) {
                for (int j = 1; j <= i; j++) {
                    triangle.append(j);
                }
                triangle.append(System.lineSeparator());
            }

            System.out.print(triangle.toString());

        } catch (NumberFormatException e) {
            System.err.println("Error: El argumento proporcionado no es un número válido.");
        }
    }
}