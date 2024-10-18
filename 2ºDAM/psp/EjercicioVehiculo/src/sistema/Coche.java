package sistema;

public class Coche extends VehiculoTerrestre {
    
    public boolean tieneAireAcondicionado;
    
    public Coche(int velocidad, int ruedas, boolean tieneAireAcondicionado) {
        super(velocidad, ruedas);
        this.tieneAireAcondicionado = tieneAireAcondicionado;
    }

    @Override
    public void acelerar() {
        int velocidad = obtenerVelocidad();
        velocidad += 10;
        establecerVelocidad(velocidad);
        System.out.println("El coche ha acelerado. Velocidad actual: " + velocidad + " km/h.");
    }

    @Override
    public void frenar() {
        int velocidad = obtenerVelocidad();
        velocidad -= 5;
        if (velocidad < 0) {
            velocidad = 0;
        }
        establecerVelocidad(velocidad);
        System.out.println("El coche ha frenado. Velocidad actual: " + velocidad + " km/h.");
    }

    public void activarAireAcondicionado() {
        if (!tieneAireAcondicionado) {
            tieneAireAcondicionado = true;
            System.out.println("El aire acondicionado ha sido activado.");
        } else {
            System.out.println("El aire acondicionado ya está activado.");
        }
    }

    public void desactivarAireAcondicionado() {
        if (tieneAireAcondicionado) {
            tieneAireAcondicionado = false;
            System.out.println("El aire acondicionado ha sido desactivado.");
        } else {
            System.out.println("El aire acondicionado ya está desactivado.");
        }
    }
}