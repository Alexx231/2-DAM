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
        String codigoExamen = this.buffer.consumirExamen();
        if (codigoExamen != null) {
            System.out.println(hilo.getName() + " está realizando el examen: " + codigoExamen);
            Random random = new Random();
            char[] respuestas = {'A', 'B', 'C', 'D', '-'};
            for (int i = 1; i <= 10; i++) {
                char respuesta = respuestas[random.nextInt(respuestas.length)];
                System.out.println("Pregunta " + i + ": " + respuesta);
            }
        } else {
            System.out.println("Agotado tiempo de espera y no hay más exámenes");
        }
    }
}