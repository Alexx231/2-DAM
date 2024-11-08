package es.fiesta;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext;

public class App {
    public static void main(String[] args) {
        System.out.println("Actividad de inyección de dependencias con Spring");
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
		System.out.println("_______________________");

		Fiesta fi = (Fiesta) context.getBean("ACME-1");;
		Fiestero f = fi.getFiestero();
		Coche c = f.getCoche();
		
		String texto = String.format(
			"En la fiesta sita en %s, "
			+ "el fiestero es %s "
			+ "está llegando en un coche con matrícula %s de la marca %s y modelo %s."
			+ " Para contactar con el fiestero llame al teléfono %s.",
			fi.getDireccion(),
			f.getNombre(),
			c.getMatricula(),
			c.getMarca(),
			c.getModelo(),
			f.getTlf());
		
		System.out.println(texto);
		
		String textoEsperado = 
			"En la fiesta sita en Humanes, "
			+ "el fiestero es Alejandro "
			+ "está llegando en un coche con matrícula 7282-CML de la marca VW y modelo Passat. "
			+ "Para contactar con el fiestero llame al teléfono 555-555-555.";

		
		if (0 != texto.compareTo(textoEsperado)) System.err.println("MAL!!!! Repasa el archivo context.xml");
		
		((AnnotationConfigApplicationContext)context).close();
	}
}