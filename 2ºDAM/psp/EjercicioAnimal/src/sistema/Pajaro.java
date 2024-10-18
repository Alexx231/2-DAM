package sistema;

public class Pajaro implements Animal {
    @Override
    public void comer() {
        System.out.println("El pájaro está comiendo.");
    }

    @Override
    public void dormir() {
        System.out.println("El pájaro está durmiendo.");
    }

    @Override
    public void hacerSonido() {
        System.out.println("El pájaro está cantando.");
    }

    public void volar() {
        System.out.println("El pájaro está volando.");
    }
}