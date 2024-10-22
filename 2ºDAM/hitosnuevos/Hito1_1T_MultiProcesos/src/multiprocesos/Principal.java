package multiprocesos;

import multitarea.BufferExamenes;
import multitarea.Examinador;
import multitarea.ProductorExamenes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Principal {
    public static void main(String[] args) throws InterruptedException, IOException {
        if (args.length == 0) {
            System.out.println("Por favor, proporciona los nombres de los alumnos como argumentos.");
            return;
        }

        // Redirigir la salida estándar a un archivo
        String outputFileName = args[0].equals("Pepe") ? "examen1.txt" : "examen2.txt";
        PrintStream fileOut = new PrintStream(new FileOutputStream(outputFileName, true));
        System.setOut(fileOut);

        BufferExamenes generador = new BufferExamenes();
        for (String alumno : args) {
            new ProductorExamenes(generador);
            new Examinador(alumno, generador);
        }
    }
}