package ejercicios;

public class Main {
    public static void main(String[] args) {
        // Crear tres instancias de ActividadDos, cada una representando un corredor
        ActividadDos corredor1 = new ActividadDos("Corredor 1");
        ActividadDos corredor2 = new ActividadDos("Corredor 2");
        ActividadDos corredor3 = new ActividadDos("Corredor 3");

        // Iniciar los hilos de los corredores
        corredor1.start();
        corredor2.start();
        corredor3.start();

        try {
            // Esperar a que todos los corredores terminen la carrera
            corredor1.join();
            corredor2.join();
            corredor3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Imprimir el nombre del ganador de la carrera
        System.out.println("La carrera ha terminado. El ganador es: " + ActividadDos.getGanador());
    }
}