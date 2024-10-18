package actividad;

public class Actividad3 implements Runnable {
    private String nombre;

    public Actividad3(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(nombre + " ha avanzado " + i + " metros.");
            try {
                Thread.sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                System.out.println(nombre + " ha sido interrumpido.");
            }
        }
        System.out.println(nombre + " ha terminado la carrera.");
    }

    public static void main(String[] args) {
    	Actividad3 corredor1 = new Actividad3("Corredor 1");
    	Actividad3 corredor2 = new Actividad3("Corredor 2");
    	Actividad3 corredor3 = new Actividad3("Corredor 3");

        Thread hilo1 = new Thread(corredor1);
        Thread hilo2 = new Thread(corredor2);
        Thread hilo3 = new Thread(corredor3);

        hilo1.start();
        hilo2.start();
        hilo3.start();

        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
        } catch (InterruptedException e) {
            System.out.println("La carrera ha sido interrumpida.");
        }

        System.out.println("La carrera ha terminado.");
    }
}