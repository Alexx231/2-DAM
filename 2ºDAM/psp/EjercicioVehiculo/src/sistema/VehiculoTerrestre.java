package sistema;

public class VehiculoTerrestre implements Vehiculo {

    private int velocidad;
    private int ruedas;

    public VehiculoTerrestre(int velocidad, int ruedas) {
        this.velocidad = velocidad;
        this.ruedas = ruedas;
    }

    @Override
    public void acelerar() {
       
    }

    @Override
    public void frenar() {
        
    }

    @Override
    public int obtenerVelocidad() {
        return velocidad;
    }

    public void establecerVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getRuedas() {
        return ruedas;
    }
}