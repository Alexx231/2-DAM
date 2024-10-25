package multitarea;

public class Principal {
    public static void main(String[] args) throws InterruptedException {
        BufferExamenes generador = new BufferExamenes();
        
        // Crear y arrancar los productores
        ProductorExamenes productor1 = new ProductorExamenes(generador);
        ProductorExamenes productor2 = new ProductorExamenes(generador);
        ProductorExamenes productor3 = new ProductorExamenes(generador);
        
        // Esperar a que los productores terminen
        productor1.getHilo().join();
        productor2.getHilo().join();
        productor3.getHilo().join();
        
        // Crear y arrancar los examinadores
        Examinador examinador1 = new Examinador("Rosa", generador);
        Examinador examinador2 = new Examinador("Miguel", generador);
        Examinador examinador3 = new Examinador("Carlos", generador);
        
        // Ejecutar los examinadores
        examinador1.run();
        examinador2.run();
        examinador3.run();
        
        // Imprimir los exámenes producidos
        for (String examen : generador.getExamenesProducidos()) {
            System.out.println("Producido examen " + examen);
        }
        
        // Imprimir los resultados de los examinadores
        for (String resultado : examinador1.getResultados()) {
            System.out.println(resultado);
        }
        for (String resultado : examinador2.getResultados()) {
            System.out.println(resultado);
        }
        for (String resultado : examinador3.getResultados()) {
            System.out.println(resultado);
        }
    }
}