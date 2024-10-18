package sistema;

public class Main {
    public static void main(String[] args) {
        Animal perro = new Perro();
        perro.comer();
        perro.dormir();
        perro.hacerSonido();
        ((Perro) perro).moverCola();
        
        System.out.println("-----------------------------");

        Animal pajaro = new Pajaro();
        pajaro.comer();
        pajaro.dormir();
        pajaro.hacerSonido();
        ((Pajaro) pajaro).volar();

        System.out.println("-----------------------------");
        
        Animal pez = new Pez();
        pez.comer();
        pez.dormir();
        pez.hacerSonido();
        ((Pez) pez).nadar();
    }
}