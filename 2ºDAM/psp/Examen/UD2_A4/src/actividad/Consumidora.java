package actividad;

import java.util.List;

/**
 * Clase Consumidora que implementa Runnable.
 * Consume radios de una lista compartida y crea objetos Circunferencia.
 */
public class Consumidora implements Runnable {
    private List<Double> radios;

    /**
     * Constructor de la clase Consumidora.
     * @param radios Lista compartida de donde se consumirán los radios.
     */
    public Consumidora(List<Double> radios) {
        this.radios = radios;
    }

    /**
     * Método run que se ejecuta cuando el hilo comienza.
     * Consume radios de la lista compartida y crea objetos Circunferencia.
     */
    @Override
    public void run() {
        while (true) {
            synchronized (radios) {
                while (radios.isEmpty()) {
                    try {
                        radios.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                double radio = radios.remove(0);
                Circunferencia circunferencia = new Circunferencia(radio);
                System.out.println("Consumidora ha creado una circunferencia con radio: " + radio + " y área: " + circunferencia.getArea());
            }
        }
    }
}