package sistema;

public class Telefono extends DispositivoBase {
    
    private int numero;
    
    public Telefono(int numero, boolean estaEncendido) {
        super(estaEncendido);
        this.numero = numero;
    }
    
    public void realizarLlamada(int numero, boolean estaEncendido) {
        if (estaEncendido) {
            System.out.println("Realizando llamada al numero: " + numero);
        } else {
            System.out.println("No se puede realizar la llamada.");
        }
    }
}