package actividad;

public class Circunferencia {
    private double radio;

    public Circunferencia(double radio) {
        this.radio = radio;
    }

    public double getArea() {
        return Math.PI * radio * radio;
    }
}