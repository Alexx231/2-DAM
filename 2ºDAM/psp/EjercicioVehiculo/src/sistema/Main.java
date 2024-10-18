package sistema;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de Moto
        Moto moto = new Moto(0, 2, true);
        moto.acelerar();
        moto.frenar();
        moto.Cascopuesto();
        System.out.println("Velocidad actual de la moto: " + moto.obtenerVelocidad() + " km/h");

        System.out.println("-----------------------------");
        
        // Crear una instancia de Coche
        Coche coche = new Coche(0, 4, false);
        coche.acelerar();
        coche.frenar();
        coche.activarAireAcondicionado();
        coche.desactivarAireAcondicionado();
        System.out.println("Velocidad actual del coche: " + coche.obtenerVelocidad() + " km/h");
        
        System.out.println("-----------------------------");

        // Crear una instancia de Bicicleta
        Bicicleta bicicleta = new Bicicleta(0, 2, true);
        bicicleta.acelerar();
        bicicleta.frenar();
        bicicleta.usarTimbre();
        System.out.println("Velocidad actual de la bicicleta: " + bicicleta.obtenerVelocidad() + " km/h");
    }
}