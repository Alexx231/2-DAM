package sistema;

public class Main {
    public static void main(String[] args) {
        // Crear y usar un Telefono
        int numeroTelefono = 123456789;
        Telefono telefono = new Telefono(numeroTelefono, false);

        telefono.encender();
        telefono.mostrarEstado();
        int numeroLlamada = 987654321;
        telefono.realizarLlamada(numeroLlamada, true);
        telefono.apagar();
        telefono.mostrarEstado();

        System.out.println("-----------------------------");
        
        // Crear y usar un Ordenador
        String programa = "Microsoft Word";
        Ordenador ordenador = new Ordenador(false, programa);

        ordenador.encender();
        ordenador.mostrarEstado();
        ordenador.ejecutarPrograma(programa,true);
        ordenador.apagar();
        ordenador.mostrarEstado();

        System.out.println("-----------------------------");
        
        // Crear y usar una Television
        int canalInicial = 1;
        Television televisor = new Television(canalInicial, true);

        televisor.encender();
        televisor.mostrarEstado();
        int nuevoCanal = 5;
        televisor.cambiarCanal(nuevoCanal,true);
        televisor.apagar();
        televisor.mostrarEstado();
    }
}