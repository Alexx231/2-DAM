package ejercicios;

public class ActividadTres {
    
    public static void main(String[] args) {
        // Intenta ejecutar el Bloc de Notas con un archivo específico
        try {
            Process proceso = Runtime.getRuntime().exec(new String[]{"notepad.exe", "C:\\Users\\holas\\Desktop\\prueba.txt"});
            // Imprime un mensaje indicando que el proceso ha sido lanzado
            System.out.println("Proceso lanzado: prueba.txt en el Bloc de Notas desde Java");
        } catch (Exception e) {
            // Maneja cualquier excepción que ocurra durante la ejecución del proceso
            e.printStackTrace();
        }
    }
}