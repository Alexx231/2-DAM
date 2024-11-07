package es.gasolinera;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		System.out.println("Actividad de inyección de dependencias con Spring");

		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		
		System.out.println("_______________________");

		Gasolinera g = (Gasolinera) context.getBean("ACME-1");
		Empleado e = g.getEmpleado();
		Coche c = e.getCoche();
		
		String texto = String.format(
			"En la gasolinera sita en %s, "
			+ "el empleado %s "
			+ "está repostando un coche con matrícula %s de la marca %s y modelo %s."
			+ " Para contactar con el empleado llame al teléfono %s.",
			g.getDireccion(),
			e.getNombre(),
			c.getMatricula(),
			c.getMarca(),
			c.getModelo(),
			e.getTlf());
		
		System.out.println(texto);
		
		String textoEsperado = 
			"En la gasolinera sita en Coyote Beach, "
			+ "el empleado Bugs Bonny "
			+ "está repostando un coche con matrícula HP-9669 de la marca Nissan y modelo GTR-3. "
			+ "Para contactar con el empleado llame al teléfono 555-555-555.";

		
		if (0 != texto.compareTo(textoEsperado)) System.err.println("MAL!!!! Repasa el archivo context.xml");
		
		((ClassPathXmlApplicationContext)context).close();
	}
}