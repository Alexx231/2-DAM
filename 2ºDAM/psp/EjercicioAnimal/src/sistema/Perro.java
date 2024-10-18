package sistema;

public class Perro extends Mamifero {
    @Override
    public void hacerSonido() {
        System.out.println("El perro está ladrando.");
    }

    public void moverCola() {
        System.out.println("El perro está moviendo la cola.");
    }
}