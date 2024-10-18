package path;

/**
 * @author alejandro.pawlukiewicz.23@campusfp.es
 */

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class PathInfoNIO {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("No se ha pasado ningún argumento.");
            System.exit(1);
        }

        if (args.length > 1) {
            System.err.println("Se han pasado demasiados argumentos.");
            System.exit(1);
        }

        Path path = Paths.get(args[0]);

        if (!Files.exists(path)) {
            System.err.println("El archivo o carpeta no existe: " + args[0]);
            System.exit(1);
        }

        try {
            if (Files.isRegularFile(path)) {
                printFileInfo(path);
            } else if (Files.isDirectory(path)) {
                printFolderInfo(path);
            } else {
                System.err.println(args[0] + " no es ni archivo ni carpeta.");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Error al procesar el archivo o carpeta: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void printFileInfo(Path path) throws IOException {
        System.out.println("Path absoluto: " + path.toAbsolutePath());
        System.out.println("Tamaño en bytes: " + Files.size(path));
        System.out.println("Permisos: ");
        System.out.println("  Lectura: " + Files.isReadable(path));
        System.out.println("  Escritura: " + Files.isWritable(path));
        System.out.println("  Ejecución: " + Files.isExecutable(path));
    }

    private static void printFolderInfo(Path path) throws IOException {
        System.out.println("Path absoluto: " + path.toAbsolutePath());
        System.out.println("Número de archivos: " + Files.list(path).count());

        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                printFileInfo(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (!dir.equals(path)) {
                    System.out.println("Subcarpeta: " + dir.toAbsolutePath());
                    System.out.println("Número de archivos: " + Files.list(dir).count());
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}