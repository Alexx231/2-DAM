/**

 * @author alejandro.pawlukiewicz.23@campusfp.es

  */







package ejercicios;



import java.io.File;

import java.io.IOException;

import java.io.OutputStream;



interface Info{

void printInfo(OutputStream os);

}

class FileInfo implements Info{

File archivo;

@Override

public void printInfo(OutputStream os) {

String msg = null;




try {

msg = "Ruta Absoluta: " + this.archivo.getAbsolutePath() + System.lineSeparator();
os.write(msg.getBytes());
msg = "Nombre " + this.archivo.getName() + System.lineSeparator();
os.write(msg.getBytes());
msg = "Directorio " + this.archivo.getParent() + System.lineSeparator();
os.write(msg.getBytes());
msg = "Tamaño de bytes " + this.archivo.length()+ System.lineSeparator();
os.write(msg.getBytes());
msg = " es Legible " + this.archivo.canRead() + System.lineSeparator();
os.write(msg.getBytes());
msg = "Se puede ejecutar " + this.archivo.canExecute()+ System.lineSeparator();
os.write(msg.getBytes());
msg = "Se puede escribir " + this.archivo.canExecute() + System.lineSeparator();
os.write(msg.getBytes());








} catch (IOException e) {

// TODO Auto-generated catch block

System.err.println(e.getMessage());

}

// TODO Auto-generated method stub

}



FileInfo(File archivo){

this.archivo = archivo;



}





}




class FolderInfo implements Info {

    File archivo;

    @Override
    public void printInfo(OutputStream os) {
        String msg = null;

        try {
            msg = "Ruta absoluta: " + this.archivo.getAbsolutePath() + System.lineSeparator();
            os.write(msg.getBytes());
            msg = "Nombre: " + this.archivo.getName() + System.lineSeparator();
            os.write(msg.getBytes());
            msg = "Directorio : " + this.archivo.getParent() + System.lineSeparator();
            os.write(msg.getBytes());
            long folderSize = getFolderSize(this.archivo);
            msg = "Tamaño en bytes: " + folderSize + System.lineSeparator();
            os.write(msg.getBytes());
            msg = "Es legible: " + this.archivo.canRead() + System.lineSeparator();
            os.write(msg.getBytes());
            msg = "Se puede ejecutar: " + this.archivo.canExecute() + System.lineSeparator();
            os.write(msg.getBytes());
            msg = "Se puede escribir: " + this.archivo.canWrite() + System.lineSeparator();
            os.write(msg.getBytes());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    FolderInfo(File archivo) {
        this.archivo = archivo;
    }

    // Método para calcular el tamaño total de una carpeta
    private long getFolderSize(File folder) {
        long length = 0;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += getFolderSize(file);
                }
            }
        }
        return length;
    }
}




public class PathInfo {



public static void main(String[] args) {

/*

System.out.println("Me han pasado " + args.length + " argumento(s)");

for (int i = 0; i < args.length; i++) {

System.out.println("Argumento " + i + ";" + args[i]);



}

*/



File archivo = new File(args[0]);



Info info = null;

if (archivo.isFile()) {

info = new FileInfo(archivo);



} else if (archivo.isDirectory()){

info = new FolderInfo(archivo);



}else {

System.err.println(args[0] + " no es ni archivo ni carpeta");

System.exit(1);

}

info.printInfo(System.out);

}



}

