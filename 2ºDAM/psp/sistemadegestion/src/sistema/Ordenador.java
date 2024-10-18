package sistema;

public class Ordenador extends DispositivoBase {
    
    private String programa;
    
    public Ordenador(boolean estaEncendido, String programa) {
        super(estaEncendido);
        this.programa = programa;
    }
    
	public void ejecutarPrograma(String programa, boolean estaEncendido) {
        if (estaEncendido) {
            System.out.println("Ejecutando el programa: " + programa);
        } else {
            System.out.println("El programa no se puede ejecutar");
        }
    }
}