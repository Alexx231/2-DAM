package ejercicios;

public class ActividadDos {
	
	public static void main(String[] args) {
		try {
			Process proceso = Runtime.getRuntime().exec("Spotify.exe");
			System.out.println("Proceso lanzado: Spotify");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
