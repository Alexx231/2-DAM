package ejercicios;

public class ActividadSeis {

    public static void main(String[] args) {
        // Verifica que se haya proporcionado exactamente un argumento
        if (args.length != 1) {
            System.err.println("Uso: java ejercicios.ActividadSeis <base>");
            System.exit(1);
        }

        try {
            // Convierte el argumento a un número entero
            int base = Integer.parseInt(args[0]);

            // Construye el triángulo de números
            StringBuilder triangle = new StringBuilder();
            for (int i = base; i >= 1; i--) {
                for (int j = 1; j <= i; j++) {
                    triangle.append(j);
                }
                triangle.append(System.lineSeparator());
            }

            // Imprime el triángulo
            System.out.print(triangle.toString());

        } catch (NumberFormatException e) {
            // Maneja el caso en que el argumento no sea un número válido
            System.err.println("Error: El argumento proporcionado no es un número válido.");
        }
    }
}