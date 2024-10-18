package ejercicios;

public class Main {
    public static void main(String[] args) {
        ActividadDos corredor1 = new ActividadDos("Corredor 1");
        ActividadDos corredor2 = new ActividadDos("Corredor 2");
        ActividadDos corredor3 = new ActividadDos("Corredor 3");

        corredor1.start();
        corredor2.start();
        corredor3.start();

        try {
            corredor1.join();
            corredor2.join();
            corredor3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("La carrera ha terminado. El ganador es: " + ActividadDos.getGanador());
    }
}