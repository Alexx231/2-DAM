package actividad;

// La clase Actividad3 implementa la interfaz Runnable para que sus instancias puedan ser ejecutadas por un hilo.
public class Actividad3 implements Runnable {
    // Atributo que almacena el nombre del corredor.
    private String nombre;

    // Constructor que inicializa el nombre del corredor.
    public Actividad3(String nombre) {
        this.nombre = nombre;
    }

    // Método run que se ejecuta cuando el hilo es iniciado.
    @Override
    public void run() {
        // Simula el avance del corredor en una carrera.
        for (int i = 1; i <= 10; i++) {
            System.out.println(nombre + " ha avanzado " + i + " metros.");
            try {
                // Pausa el hilo por un tiempo aleatorio entre 0 y 1000 milisegundos.
                Thread.sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                // Maneja la interrupción del hilo.
                System.out.println(nombre + " ha sido interrumpido.");
            }
        }
        // Indica que el corredor ha terminado la carrera.
        System.out.println(nombre + " ha terminado la carrera.");
    }

    // Método principal que inicia la ejecución del programa.
    public static void main(String[] args) {
        // Crea tres instancias de Actividad3, cada una con un nombre diferente.
        Actividad3 corredor1 = new Actividad3("Corredor 1");
        Actividad3 corredor2 = new Actividad3("Corredor 2");
        Actividad3 corredor3 = new Actividad3("Corredor 3");

        // Crea tres hilos, cada uno ejecutando una instancia de Actividad3.
        Thread hilo1 = new Thread(corredor1);
        Thread hilo2 = new Thread(corredor2);
        Thread hilo3 = new Thread(corredor3);

        // Inicia los tres hilos.
        hilo1.start();
        hilo2.start();
        hilo3.start();

        try {
            // Espera a que los tres hilos terminen.
            hilo1.join();
            hilo2.join();
            hilo3.join();
        } catch (InterruptedException e) {
            // Maneja la interrupción de la espera de los hilos.
            System.out.println("La carrera ha sido interrumpida.");
        }

        // Indica que la carrera ha terminado.
        System.out.println("La carrera ha terminado.");
    }
}