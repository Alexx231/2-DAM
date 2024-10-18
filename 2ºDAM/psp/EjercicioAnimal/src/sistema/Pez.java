package sistema;

public class Pez implements Animal {
    @Override
    public void comer() {
        System.out.println("El pez está comiendo.");
    }

    @Override
    public void dormir() {
        System.out.println("El pez está durmiendo.");
    }

    @Override
    public void hacerSonido() {
        System.out.println("El pez está haciendo burbujas.");
    }

    public void nadar() {
        System.out.println("El pez está nadando.");
    }
}