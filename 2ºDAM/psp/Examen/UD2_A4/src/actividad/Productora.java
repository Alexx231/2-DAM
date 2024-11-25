package actividad;

import java.util.List;
import java.util.Random;

/**
 * Clase Productora que implementa Runnable.
 * Genera radios aleatorios y los añade a una lista compartida.
 */
public class Productora implements Runnable {
    private List<Double> radios;

    /**
     * Constructor de la clase Productora.
     * @param radios Lista compartida donde se añadirán los radios generados.
     */
    public Productora(List<Double> radios) {
        this.radios = radios;
    }

    /**
     * Método run que se ejecuta cuando el hilo comienza.
     * Genera radios aleatorios y los añade a la lista compartida.
     */
    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            double radio = 1 + (10 - 1) * random.nextDouble();
            synchronized (radios) {
                System.out.println("Productora ha generado el radio: " + radio);
                radios.add(radio);
                radios.notify();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}