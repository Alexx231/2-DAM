package tresenraya;

import java.util.Scanner;

public class Rayas {

	int[] rayass;
	
	int ancho = 3;
	
	int alto = 3;
	
	int pieza = 0;
	
	int celdas = ancho*alto;
	
	int player1 = 1, player2 = -1;
	
	Scanner scan;
	
	
	void Start()
	{
	    rayass = new int[celdas];

	    for (int i = 0; i < celdas; i++)
	    {
	        rayass[i] = 0;
	    }
	    
	    scan = new Scanner(System.in);
	}
	
	void Pintar(boolean simbolos)
	{
		if(simbolos)
		{
			for(int i = 0; i < celdas; i++)
			{
				
				if(i % ancho == 0 && i != 0)
				{
					System.out.println();
				}
				if(rayass[1] == player1)
				{
					System.out.print("X");
				}
				else if(rayass[1] == player2)
				{
					System.out.print("O");
				}
				else
				{
					System.out.print("");
				}
			}
		}
		else
		{
			for(int i = 0; i < celdas; i++)
			{
				
				if(i % ancho == 0 && i != 0)
				{
					System.out.println();
				}
				System.out.print(rayass[i]);
			}
		}
		
	}
	
	void TurnoPlayer() {

        System.out.println("Selecciona un número para el alto (0, 1, 2): ");
        int alto = scan.nextInt();
        

        System.out.println("Selecciona un número para el ancho (0, 1, 2): ");
        int ancho = scan.nextInt();
        

        int indice = alto * 3 + ancho;


        if (rayass[indice] == 0) {

            rayass[indice] = pieza;
        } else {

            System.out.println("Posición ocupada, intenta de nuevo.");
            TurnoPlayer();
        }

        pieza = (pieza == player1) ? player2 : player1;
    }
	
public static void main(String[] args)
{
	Rayas m = new Rayas();
	m.Start();
	
	while(true) {
		m.Pintar(true);
		m.TurnoPlayer();
		m.Pintar(true);
		m.TurnoPlayer();
	}
	
	
	
}
	
}
