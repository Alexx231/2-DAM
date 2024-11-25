package ejercicios;

public class ActividadTres {
	
	public static void main(String[] args) {
		try {
			Process proceso = Runtime.getRuntime().exec(new String[]{"notepad.exe", "C:\\Users\\holas\\Desktop\\prueba.txt"});
			System.out.println("Proceso lanzado: prueba.txt en el Bloc de Notas desde Java");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}