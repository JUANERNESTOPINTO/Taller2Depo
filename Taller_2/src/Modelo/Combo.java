package Modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import Procesamiento.LoaderArchivos;

public class Combo extends Producto {
	static Map<String, String[]> Combos = new HashMap<>();;
	Map<String, String> pedidosCombos = new HashMap<>();;
	static Map<String, String> Menu = new HashMap<>();;
	static Map<String, String> Ingredientes = new HashMap<>();;
	private static String[] elementosCombo;
	private static float descuento;
	private String nombreCombo;
	// private ProductoMenu itemsCombo;

	
}
