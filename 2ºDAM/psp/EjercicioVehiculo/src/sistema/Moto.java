package sistema;

public class Moto extends VehiculoTerrestre {
    
    public boolean tieneCasco;
    
    public Moto(int velocidad, int ruedas, boolean tieneCasco) {
        super(velocidad, ruedas);
        this.tieneCasco = tieneCasco;
    }

    @Override
    public void acelerar() {
        int velocidad = obtenerVelocidad();
        velocidad += 20;
        establecerVelocidad(velocidad);
        System.out.println("La moto ha acelerado. Velocidad actual: " + velocidad + " km/h.");
    }

    @Override
    public void frenar() {
        int velocidad = obtenerVelocidad();
        velocidad -= 10;
        if (velocidad < 0) {
            velocidad = 0;
        }
        establecerVelocidad(velocidad);
        System.out.println("La moto ha frenado. Velocidad actual: " + velocidad + " km/h.");
    }

    public void Cascopuesto() {
        if (!tieneCasco) {
            System.out.println("El casco no está puesto.");
        } else {
            System.out.println("El casco está puesto.");
        }
    }
}