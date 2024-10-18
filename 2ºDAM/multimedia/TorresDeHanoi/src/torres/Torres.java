package torres;

import java.util.Scanner;

public class Torres {
	
	int[] torre1;
	int[] torre2;
	int[] torre3;
	
	int n_fichas = 3;
	
	int ficha_en_mano = 0;
	
	boolean victoria = false;
	
	Scanner scan;
	
	public Torres(int patata)
	{
		n_fichas = patata;
	}
	
	void Start()
	{
	    torre1 = new int[n_fichas];
	    torre2 = new int[n_fichas];
	    torre3 = new int[n_fichas];
	    scan = new Scanner(System.in);

	    for (int i = 0; i < n_fichas; i++)
	    {
	        torre1[i] = i + 1;
	        torre2[i] = 0;
	        torre3[i] = 0;
	    }
	}
	
	void Pintar()
	{
		
		for(int i = 0; i < n_fichas; i++)
		{
			System.out.print(torre1[i]);
		}
		
		System.out.println("\n");
		
		for(int i = 0; i < n_fichas; i++)
		{
			System.out.print(torre2[i]);
		}
		
		System.out.println("\n");
		
		for(int i = 0; i < n_fichas; i++)
		{
			System.out.print(torre3[i]);
		}
		
		System.out.println("\n\n");
		System.out.println("Ficha en mano: " + ficha_en_mano);
		
	}
	
	void TurnoSacarFicha()
	{
	
		System.out.println("Selecciona una torre para sacar ficha: 1/2/3");
		int input_usuario = 0;
		input_usuario = scan.nextInt();
		
		if(input_usuario == 1)
		{
			for(int posiciondelatorre = 0; posiciondelatorre < n_fichas; posiciondelatorre++)
			{
				if(torre1[posiciondelatorre] != 0 && ficha_en_mano == 0)
				{
					ficha_en_mano = torre1[posiciondelatorre];
					torre1[posiciondelatorre] = 0;
					
				}
				
			}
		}
		else if(input_usuario == 2)
		{
			
		}
		else if(input_usuario == 3)
		{
			
		}
		
		
	}
	
	boolean TurnoPonerFicha()
	{
		System.out.println("Selecciona una torre para poner ficha: 1/2/3");
		int input_usuario = 0;
		input_usuario = scan.nextInt();
		
		switch(input_usuario) 
		{
		case 1:
			for(int sitio = n_fichas - 1; sitio >= 0; sitio--) 
			{
				if(torre1[sitio] == 0)
				{
					if(sitio != n_fichas - 1) {
						
						if(ficha_en_mano > torre1[sitio + 1])
					{
						torre1[sitio] = ficha_en_mano;
						ficha_en_mano = 0;
						return true;
					}
					else 
					{
						torre1[sitio] = ficha_en_mano;
						ficha_en_mano = 0;
						return true;
					}
					
					}
				
				}
			}
			break;
			
		case 2:
			for(int sitio = n_fichas - 1; sitio >= 0; sitio--) 
			{
				if(torre2[sitio] == 0)
				{
					if(sitio != n_fichas - 1) {
						
						if(ficha_en_mano > torre2[sitio + 1])
					{
						torre2[sitio] = ficha_en_mano;
						ficha_en_mano = 0;
						return true;
					}
					else 
					{
						torre2[sitio] = ficha_en_mano;
						ficha_en_mano = 0;
						return true;
					}
					
					}
				
				}
			}
			break;
			
		case 3:
			for(int sitio = n_fichas - 1; sitio >= 0; sitio--) 
			{
				if(torre3[sitio] == 0)
				{
					if(sitio != n_fichas - 1) {
						
						if(ficha_en_mano > torre3[sitio + 1])
					{
						torre3[sitio] = ficha_en_mano;
						ficha_en_mano = 0;
						return true;
					}
					else 
					{
						torre3[sitio] = ficha_en_mano;
						ficha_en_mano = 0;
						return true;
					}
					
					}
				
				}
			}
			break;
		}
		return false;
	}
	
	void ComprobarVictoria()
	{
		if(torre2[0] != 0 || torre3[0] != 0)
		{
			victoria = true;
		}
	}
	
	public static void main(String[] args) {
		
		Torres t = new Torres(3);
		
		t.Start();
		
		
		while(t.victoria == false)
		{
			
		t.Pintar();
		t.TurnoSacarFicha();
		t.Pintar();
		while (t.TurnoPonerFicha() == false)
		{
			t.Pintar();
		}
		t.ComprobarVictoria();
	}
		System.out.println("Victoria Magistral");
	}
	
}

