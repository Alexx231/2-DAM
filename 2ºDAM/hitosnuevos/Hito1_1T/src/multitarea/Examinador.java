package multitarea;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Examinador implements Runnable {
    private String nombre;
    private BufferExamenes buffer;
    private List<String> resultados;

    public Examinador(String nombre, BufferExamenes buffer) {
        this.nombre = nombre;
        this.buffer = buffer;
        this.resultados = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            String examen = buffer.consumirExamen();
            realizarExamen(examen);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void realizarExamen(String examen) {
        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            String respuesta = generarRespuestaAleatoria(random);
            String resultado = String.format("%s;%s; Pregunta %d; %s", examen, nombre, i, respuesta);
            resultados.add(resultado);
        }
    }

    private String generarRespuestaAleatoria(Random random) {
        String[] opciones = {"A", "B", "C", "D", "-"};
        return opciones[random.nextInt(opciones.length)];
    }

    public List<String> getResultados() {
        return new ArrayList<>(resultados);
    }
}