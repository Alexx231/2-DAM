package multiprocesos;

import java.io.File;

public class ControladorProcesos {
    public void lanzarProcesos() {

        String[] args1 = {"Pepe", "Juan", "Luis", "examen1.txt"};

        String[] args2 = {"Rosa", "Miguel", "Pedro", "examen2.txt"};

        Principal.ejecutarConArgumentos(args1);
        Principal.ejecutarConArgumentos(args2);


        verificarArchivo("examen1.txt");
        verificarArchivo("examen2.txt");
    }

    private void verificarArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            System.out.println("El archivo " + nombreArchivo + " ha sido rellenado.");
        } else {
            System.out.println("El archivo " + nombreArchivo + " no se ha creado.");
        }
    }
}