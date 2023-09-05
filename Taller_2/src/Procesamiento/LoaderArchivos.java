package Procesamiento;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class LoaderArchivos {

	public static Map<String, String> cargarMenu (String nombreArchivo) throws FileNotFoundException, IOException{
		Map<String, String> elMenu = new HashMap<>();;
		
		BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
		String linea;
		linea = br.readLine();
		while (linea != null) {
			String[] partes = linea.split(";");
            elMenu.put(partes[0], partes[1]);
			
			linea = br.readLine();
		}
		br.close();
		
		
		
		return elMenu;
	}

	public static HashMap<String, String[]> cargarCombos (String nombreArchivo) throws FileNotFoundException, IOException{
		HashMap<String, String[]> elMenu = new HashMap<>();;
		
		BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
		String linea;
		linea = br.readLine();
		while (linea != null) {
			String[] partes = linea.split(";");
			String [] complemento = {partes[1],partes[2],partes[3],partes[4]};
            elMenu.put(partes[0], complemento);
			
			linea = br.readLine();
		}
		br.close();
		
		
		
		return elMenu;
	}
}
