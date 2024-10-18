package sistema;

public abstract class Mamifero implements Animal {
    @Override
    public void comer() {
        System.out.println("El mamífero está comiendo.");
    }

    @Override
    public void dormir() {
        System.out.println("El mamífero está durmiendo.");
    }

    @Override
    public void hacerSonido() {
        System.out.println("El mamífero está haciendo un sonido.");
    }
}