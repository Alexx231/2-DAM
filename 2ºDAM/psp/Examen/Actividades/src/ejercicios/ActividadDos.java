package ejercicios;

public class ActividadDos {
    
    public static void main(String[] args) {
        // Intenta ejecutar el proceso "Spotify.exe"
        try {
            Process proceso = Runtime.getRuntime().exec("Spotify.exe");
            // Imprime un mensaje indicando que el proceso ha sido lanzado
            System.out.println("Proceso lanzado: Spotify");
        } catch (Exception e) {
            // Maneja cualquier excepción que ocurra durante la ejecución del proceso
            e.printStackTrace();
        }
    }
}