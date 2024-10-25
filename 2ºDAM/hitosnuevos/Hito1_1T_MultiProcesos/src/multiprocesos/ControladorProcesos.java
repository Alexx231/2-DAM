package multiprocesos;

import java.io.File;

public class ControladorProcesos {
    public void lanzarProcesos() {

        String[] args1 = {"Pepe", "Juan", "Maria", "examen1.txt"};

        String[] args2 = {"Luis", "Ana", "Carlos", "examen2.txt"};

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