package test;

public class MySQL1 {

	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
			System.out.println("No se ha encontrado el driver para MySQL");
			return;
			}
			System.out.println("Se ha cargado el Driver de MySQL");
	}

}
