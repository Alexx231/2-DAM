package ejercicio;

public class prueba {
    public static void main(String[] args) {
      
        System.out.println("Hola Mundo");

  
        for (int i = 1; i <= 10; i++) {
            System.out.println("Número: " + i);
        }

       
        int suma = 0;
        for (int i = 1; i <= 10; i++) {
            suma += i;
        }
        System.out.println("La suma de los números del 1 al 10 es: " + suma);

      
        for (int i = 1; i <= 5; i++) {
            System.out.println("El factorial de " + i + " es: " + factorial(i));
        }
    }

   
    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}