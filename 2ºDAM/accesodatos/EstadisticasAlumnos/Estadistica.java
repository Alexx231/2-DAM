import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Estadistica {
    private List<Alumno> alumnos;
    private List<Calificacion> calificaciones;

    /**
     * Constructor que carga los datos de los archivos CSV.
     * 
     * @param archivoAlumnos Ruta del archivo CSV de alumnos.
     * @param archivoCalificaciones Ruta del archivo CSV de calificaciones.
     * @throws IOException Si ocurre un error al leer los archivos.
     */
    Estadistica(String archivoAlumnos, String archivoCalificaciones) throws IOException {
        this.alumnos = cargarAlumnos(archivoAlumnos);
        this.calificaciones = cargarCalificaciones(archivoCalificaciones);
        asignarCalificaciones();
    }

    /**
     * Carga los datos de los alumnos desde un archivo CSV.
     * 
     * @param archivo Ruta del archivo CSV.
     * @return Lista de objetos Alumno.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private List<Alumno> cargarAlumnos(String archivo) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(archivo))) {
            return stream.skip(1) // Saltar la cabecera
                    .map(line -> line.split("\\|"))
                    .map(datos -> new Alumno(datos[0], "", datos[1]))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Carga los datos de las calificaciones desde un archivo CSV.
     * 
     * @param archivo Ruta del archivo CSV.
     * @return Lista de objetos Calificacion.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private List<Calificacion> cargarCalificaciones(String archivo) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(archivo))) {
            return stream.skip(1) // Saltar la cabecera
                    .map(line -> line.split("\\|"))
                    .map(datos -> new Calificacion(datos[0], Asignatura.valueOf(datos[1].toUpperCase()), Double.parseDouble(datos[2].replace(",", "."))))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Asigna las calificaciones a los alumnos correspondientes.
     */
    private void asignarCalificaciones() {
        Map<String, Alumno> mapaAlumnos = alumnos.stream()
                .collect(Collectors.toMap(alumno -> alumno.dni, alumno -> alumno));

        for (Calificacion calificacion : calificaciones) {
            Alumno alumno = mapaAlumnos.get(calificacion.dni);
            if (alumno != null) {
                alumno.calificaciones.add(calificacion);
            }
        }
    }

    /**
     * Escribe las notas de cada alumno en el stream proporcionado.
     * 
     * @param out Stream de salida.
     */
    public void escribirNotasAlumnos(PrintStream out) {
        for (Alumno alumno : alumnos) {
            out.println(alumno.nombre + " " + alumno.apellido + " " + alumno.dni);
            for (Calificacion calificacion : alumno.calificaciones) {
                out.println("\t" + calificacion.asignatura);
                out.println("\t\t" + calificacion.nota);
            }
        }
    }

    /**
     * Escribe las notas medias de cada alumno en cada asignatura en el stream proporcionado.
     * 
     * @param out Stream de salida.
     */
    public void escribirNotasMediasAlumnos(PrintStream out) {
        for (Alumno alumno : alumnos) {
            out.println(alumno.nombre + " " + alumno.apellido + " " + alumno.dni);
            Map<Asignatura, List<Double>> notasPorAsignatura = new HashMap<>();
            for (Calificacion calificacion : alumno.calificaciones) {
                notasPorAsignatura
                        .computeIfAbsent(calificacion.asignatura, k -> new ArrayList<>())
                        .add(calificacion.nota);
            }
            for (Map.Entry<Asignatura, List<Double>> entry : notasPorAsignatura.entrySet()) {
                double media = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                out.println("\t" + entry.getKey() + ": " + media);
            }
        }
    }

    /**
     * Escribe la nota media de cada alumno en el stream proporcionado.
     * 
     * @param out Stream de salida.
     */
    public void escribirNotaMediaAlumnos(PrintStream out) {
        for (Alumno alumno : alumnos) {
            double media = alumno.calificaciones.stream().mapToDouble(c -> c.nota).average().orElse(0.0);
            out.println(alumno.nombre + " " + alumno.apellido + " " + alumno.dni + ": " + media);
        }
    }

    /**
     * Escribe la nota media por asignatura en el stream proporcionado.
     * 
     * @param out Stream de salida.
     */
    public void escribirNotaMediaPorAsignatura(PrintStream out) {
        Map<Asignatura, List<Double>> notasPorAsignatura = new HashMap<>();
        for (Calificacion calificacion : calificaciones) {
            notasPorAsignatura
                    .computeIfAbsent(calificacion.asignatura, k -> new ArrayList<>())
                    .add(calificacion.nota);
        }
        for (Map.Entry<Asignatura, List<Double>> entry : notasPorAsignatura.entrySet()) {
            double media = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            out.println(entry.getKey() + ": " + media);
        }
    }

    /**
     * Escribe la nota media global en el stream proporcionado.
     * 
     * @param out Stream de salida.
     */
    public void escribirNotaMediaGlobal(PrintStream out) {
        double media = calificaciones.stream().mapToDouble(c -> c.nota).average().orElse(0.0);
        out.println("Nota media global: " + media);
    }
}