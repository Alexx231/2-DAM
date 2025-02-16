package actividad;

import java.util.List;

public class Consumidora implements Runnable {
    private List<Double> radios;

    public Consumidora(List<Double> radios) {
        this.radios = radios;
    }

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