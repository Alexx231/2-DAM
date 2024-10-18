package sistema;

abstract class DispositivoBase implements Dispositivo {
    
    protected boolean estaEncendido;
    
    public DispositivoBase(boolean estaEncendido) {
        this.estaEncendido = estaEncendido;
    }
    
    @Override
    public void encender() {
        estaEncendido = true;
    }
    
    @Override
    public void apagar() {
        estaEncendido = false;
    }
    
    @Override
    public void mostrarEstado() {
        if (estaEncendido) {
            System.out.println("El dispositivo está encendido.");
        } else {
            System.out.println("El dispositivo está apagado.");
        }
    }

}