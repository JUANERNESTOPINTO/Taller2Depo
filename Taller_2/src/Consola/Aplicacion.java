package Consola;
import Modelo.Pedido;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import Modelo.Restaurante;
import Procesamiento.LoaderArchivos;
import Modelo.Combo;

public class Aplicacion {
	
	Map<String, String> Menu = new HashMap<>();;
	Map<String, String> Ingredientes = new HashMap<>();;
	Map <String, String[]> Combos = new HashMap<>();;
	
	


 	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	public static void main (String[] args) throws FileNotFoundException, IOException
	{
		Pedido consola = new Pedido();
		consola.ejecutarPedidos();
	}

}
