package sistema;

public class Bicicleta extends VehiculoTerrestre {
    
    public boolean tieneTimbre;
    
    public Bicicleta(int velocidad, int ruedas, boolean tieneTimbre) {
        super(velocidad, ruedas);
        this.tieneTimbre = tieneTimbre;
    }

    @Override
    public void acelerar() {
        int velocidad = obtenerVelocidad();
        velocidad += 5;
        establecerVelocidad(velocidad);
        System.out.println("La bicicleta ha acelerado. Velocidad actual: " + velocidad + " km/h.");
    }

    @Override
    public void frenar() {
        int velocidad = obtenerVelocidad();
        velocidad -= 2;
        if (velocidad < 0) {
            velocidad = 0;
        }
        establecerVelocidad(velocidad);
        System.out.println("La bicicleta ha frenado. Velocidad actual: " + velocidad + " km/h.");
    }

    public void usarTimbre() {
        if (tieneTimbre) {
            System.out.println("¡Ring Ring! El timbre ha sido usado.");
        } else {
            System.out.println("Esta bicicleta no tiene timbre.");
        }
    }
}