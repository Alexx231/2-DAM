package file;

import java.io.File;

public class prueba {

public static void main(String[] args) {
	
File fich = new File("C:/pelis.txt");

if (fich.exists()) {
	
System.out.println("Existe el fichero");

System.out.println("Nº de bytes que ocupa: " + fich.length());

System.out.println("Nombre de archivo: " + fich.getName());

System.out.println("Ruta: " + fich.getPath());

System.out.println("¿Es un fichero oculto? " + fich.isHidden());

System.out.println("¿Está permitida la escritura? " + fich.canWrite());

System.out.println("¿Está permitida la lectura? " + fich.canRead());

} else {
	
System.out.println("El fichero no existe");
}
}
}