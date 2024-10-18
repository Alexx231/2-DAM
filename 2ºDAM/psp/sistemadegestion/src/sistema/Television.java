package sistema;

public class Television extends DispositivoBase {
    
    private int canal;
    
    public Television(int canal, boolean estaEncendido) {
        super(estaEncendido);
        this.canal = canal;
    }
    
    public void cambiarCanal(int canal, boolean estaEncendido) {
        if (estaEncendido) {
            System.out.println("El canal ha sido cambiado al número " + canal);
        } else {
            System.out.println("No se puede cambiar de canal.");
        }
    }
}