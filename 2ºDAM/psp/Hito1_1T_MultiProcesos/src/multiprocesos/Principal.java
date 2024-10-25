package multiprocesos;

import multitarea.BufferExamenes;
import multitarea.Examinador;
import multitarea.ProductorExamenes;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Principal {
    public static void main(String[] args) {
        if (args.length > 0) {
            ejecutarConArgumentos(args);
        } else {
            ControladorProcesos controlador = new ControladorProcesos();
            controlador.lanzarProcesos();
        }
    }

    public static void ejecutarConArgumentos(String[] args) {
        try {
            if (args.length < 2) {
                return; 
            }

            String outputFileName = args[args.length - 1];
            BufferExamenes generador = new BufferExamenes();
            List<ProductorExamenes> productores = new ArrayList<>();
            List<Examinador> examinadores = new ArrayList<>();


            for (int i = 0; i < args.length - 1; i++) {
                productores.add(new ProductorExamenes(generador));
            }


            for (ProductorExamenes productor : productores) {
                productor.getHilo().join();
            }


            for (int i = 0; i < args.length - 1; i++) {
                examinadores.add(new Examinador(args[i], generador));
            }


            for (Examinador examinador : examinadores) {
                examinador.run();
            }


            try (PrintWriter fileOut = new PrintWriter(new FileWriter(outputFileName))) {
                for (String examen : generador.getExamenesProducidos()) {
                    fileOut.println("Producido examen " + examen);
                }
                for (Examinador examinador : examinadores) {
                    for (String resultado : examinador.getResultados()) {
                        fileOut.println(resultado);
                    }
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}