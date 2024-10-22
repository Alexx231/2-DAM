package multitarea;

import java.util.Random;

public class Examinador implements Runnable {
    private Thread hilo;
    private BufferExamenes buffer;

    public Thread getHilo() {
        return hilo;
    }

    public Examinador(String alumno, BufferExamenes generador) {
        this.buffer = generador;
        this.hilo = new Thread(this, alumno);
        this.hilo.start();
    }

    @Override
    public void run() {
        String examen = buffer.consumirExamen();
        for (int i = 1; i <= 10; i++) {
            System.out.println(examen + ";" + hilo.getName() + "; Pregunta " + i + "; " + generarRespuesta());
        }
    }

    private String generarRespuesta() {
        String[] respuestas = {"A", "B", "C", "D", "-"};
        return respuestas[(int) (Math.random() * respuestas.length)];
    }
}