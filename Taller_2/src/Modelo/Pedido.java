package Modelo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import Procesamiento.LoaderArchivos;


public class Pedido {
	
	private String IdPedido;
	private String nombreCliente;
	private String direccionCliente;
	
	//Mapas para organizar los pedidos
	private Map<String, String[]> Combos = new HashMap<>();;
	Map<String, String> pedidosCombos = new HashMap<>();;
	static Map<String, String> Menu = new HashMap<>();;
	static Map<String, String> Ingredientes = new HashMap<>();;
	private Map<String, String> Clientela = new HashMap<>();;
	static Map<String, String> PedidoGeneral = new HashMap<>();;
	
	public void cargarData() throws FileNotFoundException, IOException {
		Combos = LoaderArchivos.cargarCombos("data/combos.txt");
		Ingredientes = LoaderArchivos.cargarMenu("data/ingredientes.txt");
		Menu = LoaderArchivos.cargarMenu("data/menu.txt");
	}
	
	private void MostrarMenu() {
		System.out.println("\nOpciones del pedido\n");
		System.out.println("1. Nuevo pedido  ");
		System.out.println("2. Entregar Pedido");
		System.out.println("3. Consultar pedidos que se han hecho");
		System.out.println("4. Consultar Menu ");
		System.out.println("14. Salir de la aplicacion");
	}
	
	public void ejecutarPedidos() throws FileNotFoundException, IOException {
		boolean continuar = true;
		cargarData();
		Combos = LoaderArchivos.cargarCombos("data/combos.txt");
		while (continuar) {
			try {
				MostrarMenu();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 1) {
					IdPedido = input("Ingrese el Id bajo el cual se va a guardar el pedido");
					direccionCliente = input("Direccion Cliente: ");
					nombreCliente = input("Nombre Cliente: ");
					String pedidoactual = "";
					String comboactual = "";
					String ingredienteactual="";
					boolean pedirmas = true;

					while (pedirmas) {
						try {System.out.println("1. Aniadir producto al pedido\n2. Salir y guardar pedido");
						int seleccion = Integer.parseInt(input("Por favor seleccione una opción"));
						
						if (seleccion==1) {
							
							String nomproducto = input("Elemento (Menu, ingrediente o Combo) ->");
							if (Menu.containsKey(nomproducto)) {

								pedidoactual = pedidoactual + "-" + nomproducto;

								PedidoGeneral.put(IdPedido, pedidoactual);
							}
							else if (Combos.containsKey(nomproducto)) {
								
								comboactual = comboactual + "-" + nomproducto;

								pedidosCombos.put(IdPedido, comboactual);
							}
							else if (Ingredientes.containsKey(nomproducto)) {
								
								ingredienteactual = ingredienteactual + "-" + nomproducto;

								pedidosCombos.put(IdPedido, ingredienteactual);
							}
						}
						
						else if (seleccion==2) {
							String[] partescombo = comboactual.split("-");
							String[] partespedido = pedidoactual.split("-");
							String[] partesing = ingredienteactual.split("-");
							System.out.println("Resumen del pedido:\n\n");
							float totalcombo =0;
							for (int i = 1; i < partescombo.length; i++) {
									String combo = (partescombo[i]);
									float precio = getPrecioCombo(Combos, Menu, combo);
									totalcombo += precio;
									String retorno_precio = String.valueOf(precio);
									System.out.println(combo + " -> " + retorno_precio);
							}
							float totalpedido =0;
							for (int i = 1; i < partespedido.length; i++) {
								{
									String producto = (partespedido[i]);
									float precio = Integer.parseInt(Menu.get(producto));
									totalpedido += precio;
									String retorno_precio = String.valueOf(precio);
									System.out.println(producto + " -> " + retorno_precio);
								}
							
							}
							float totaling =0;
							for (int i = 1; i < partesing.length; i++) {
								{
									String producto = (partesing[i]);
									float precio = Integer.parseInt(Ingredientes.get(producto));
									totaling += precio;
									String retorno_precio = String.valueOf(precio);
									System.out.println(producto + " -> " + retorno_precio);
								}
							
						}
							totalpedido+=totalcombo+totaling;
							System.out.println("\n\nTotal a pagar -> " + totalpedido);
							Clientela.put(IdPedido, nombreCliente+'-'+direccionCliente+"-"+totalpedido);

							pedirmas = false;
							
						}}
			
								
							
					catch (NumberFormatException e) {
						System.out.println("Debe seleccionar uno de los números de las opciones.");
					}
					
				}
					}
			
				else if (opcion_seleccionada == 2)
				{IdPedido = input("Ingrese el Id bajo el cual se guardo el pedido");
				if (Clientela.containsKey(IdPedido)){
					Clientela.put(IdPedido,"Entregado");
					System.out.println("Se ha guardado el pedido como entregado");
				}
				else {System.out.println("No se ha encontrado el pedido");}
				}
				else if (opcion_seleccionada == 3) 
				{System.out.println(Clientela);}
					
				else if (opcion_seleccionada == 4) {
					
					consultarTodo();
					
				} else if (opcion_seleccionada == 14) {
					System.out.println("Saliendo de aplicacion ...");
					continuar = false;
				}
			}

			catch (NumberFormatException e) {
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}

		}
	}
	
	private void consultarTodo() {
		
		{Menu.forEach(
		        (item, precio)
		        -> System.out.println("+--------------------+\n"+item + " \nPrecio: " + precio 
		        		));}
		{Combos.forEach(
		        (combo, precio)
		        -> System.out.println("+--------------------+\n"+combo + " \nDescuento: " + precio[0]+ 
		        		" \nHaburguesa: " + precio[1]
		        		+ " \nAdicion: " + precio[2]+ " \nBebida: " + precio[3]+
		        		"\n"));}
		
	}
	
	public String GetfacturaProducto(String pedidoactual,String idPedido) {
		float total = 0;
		String[] partes = pedidoactual.split("-");
		System.out.println("\n\n+--------------Factura--------------+\nOrden numero: " + idPedido);
		for (int i = 1; i < partes.length; i++) {
			{
				String producto = (partes[i]);
				float precio = Integer.parseInt(Menu.get(producto));
				total += precio;
				String retorno_precio = String.valueOf(precio);
				System.out.println(producto + " -> " + retorno_precio);
			}
		}
		String retorno_total = String.valueOf(total);
		System.out.println(
				"\n\nTotal a cancelar: " + retorno_total + "\n+-----------------------------------+");
		return retorno_total;

	}
	public String input(String mensaje) {
		try {
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		} catch (IOException e) {
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;

}
	
	public void cargarCombos() throws FileNotFoundException, IOException {
		Combos = LoaderArchivos.cargarCombos("data/combos.txt");
		Ingredientes = LoaderArchivos.cargarMenu("data/ingredientes.txt");
		Menu = LoaderArchivos.cargarMenu("data/menu.txt");
	}
	
	

	public Map<String, String> crearPedidoCombo(){
		String idPedido = input("Ingrese el Id del pedido");
		String pedidoactual = "";
		boolean continuar = true;
		float total = 0;
		String nombreCombo;

		while (continuar) {
			try {
				System.out.println("1. Aniadir combo al pedido\n2. Salir y guardar pedido");
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 1) {
					System.out.println(Combos.keySet());

					nombreCombo = input("Ingrese nombre del combo ->");

					if (Combos.containsKey(nombreCombo)) {

						pedidoactual = pedidoactual + "-" + nombreCombo;

						pedidosCombos.put(idPedido, pedidoactual);
					}
				}

				else if (opcion_seleccionada == 2) {
					System.out.println("Se ha guardado el combo");
					continuar = false;
					String[] partes = pedidoactual.split("-");
					System.out.println("\n\n+--------------Factura--------------+\nOrden numero: " + idPedido);
					for (int i = 1; i < partes.length; i++) {
						{
							String combo = (partes[i]);
							float precio = getPrecioCombo(Combos, Menu, combo);
							total += precio;
							String retorno_precio = String.valueOf(precio);
							System.out.println(combo + " -> " + retorno_precio);
						}
					}
					String retorno_total = String.valueOf(total);
					System.out.println(
							"\n\nTotal a cancelar: " + retorno_total + "\n+-----------------------------------+");

				}
			}

			catch (NumberFormatException e) {
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}

		}
		return pedidosCombos;

	}
	
	public String GetfacturaCombo(String pedidoactual,String idPedido) {
		String[] partes = pedidoactual.split("-");
		System.out.println("\n\n+--------------Factura--------------+\nOrden numero: " + idPedido);
		float total = 0;
		for (int i = 1; i < partes.length; i++) {
			{
				String combo = (partes[i]);
				float precio = getPrecioCombo(Combos, Menu, combo);
				total += precio;
				String retorno_precio = String.valueOf(precio);
				System.out.println(combo + " -> " + retorno_precio);
			}
		}
		String retorno_total = String.valueOf(total);
		System.out.println(
				"\n\nTotal a cancelar: " + retorno_total + "\n+-----------------------------------+");
		return retorno_total;}


	public void MostrarPedidos() {
		{pedidosCombos.forEach(
		        (combo, estado)
		        -> System.out.println("+--------------------+\n"+combo + " \nEstado: " + estado));}

	}

	public Map<String, String> editarCombo() {
		String idPedido = input("Ingrese el Id del pedido a revisar");
		if (pedidosCombos.containsKey(idPedido)) {
			System.out.println("El pedido " + idPedido + " ha sido encontrado, que desea hacer con el pedido?\n"
					+ "1.Salir\n" + "2.Entregar el pedido\n" + "3.Cancelar el pedido");

			boolean continuar = true;
			while (continuar) {
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 1) {
					System.out.println("\n\nSaliendo de la la opcion Editar Combo...\n\n");
					continuar = false;
				} else if (opcion_seleccionada == 2) {
					float total = 0;
					String pedidoactual = pedidosCombos.get(idPedido);
					String[] partes = pedidoactual.split("-");
					System.out.println("\n\n+--------------Factura--------------+\nOrden numero: " + idPedido);
					for (int i = 1; i < partes.length; i++) {
						{
							String combo = (partes[i]);
							float precio = getPrecioCombo(Combos, Menu, combo);
							total += precio;
							String retorno_precio = String.valueOf(precio);
							System.out.println(combo + " -> " + retorno_precio);
						}
					}
					String retorno_total = String.valueOf(total);
					System.out.println("\n\nTotal a cancelar: " + retorno_total);
					pedidosCombos.replace(idPedido, "Facturado: " + retorno_total);
					System.out
							.println("El pedido " + idPedido + " ha sido entregado y ya no se encuentra en el sistema");
					continuar = false;
				} else if (opcion_seleccionada == 3) {
					System.out
							.println("El pedido " + idPedido + " ha sido cancelado y ya no se encuentra en el sistema");
					pedidosCombos.replace(idPedido, "Cancelado");
					continuar = false;
				}

				try {

				} catch (NumberFormatException e) {
					System.out.println("Debe seleccionar uno de los números de las opciones.");
				}
			}
		} else {
			System.out.println("No se ha encontrado el pedido");
		}
		return pedidosCombos;

	}

	public static float getPrecioCombo(Map<String, String[]> Combos, Map<String, String> Menu, String combo) {
		float precio = 0;
		String fuerte;
		String acompaniamiento;
		String bebida;
		String[] elementosCombo;
		float descuento;
		elementosCombo = Combos.get(combo);
		fuerte = elementosCombo[1];
		acompaniamiento = elementosCombo[2];
		bebida = elementosCombo[3];
		descuento = Integer.parseInt(elementosCombo[0].split("%")[0]);
		descuento = 1 - descuento / 100;
		precio += Integer.parseInt(Menu.get(fuerte)) + Integer.parseInt(Menu.get(acompaniamiento))
				+ Integer.parseInt(Menu.get(bebida));
		precio = precio * descuento;
//System.out.println("El combo "+combo+" tiene la hamburguesa "+fuerte+
//" acompaniada de "+acompaniamiento
//+" y la bebidad "+bebida+".\nPrecio total: "+precio);
		return precio;

	}
	
	public void mostrarMenuCombo()
	{
		System.out.println("\nOpciones de la aplicación\n");
		System.out.println("1. Pedir combo  ");
		System.out.println("2. Entregar o cancelar pedido");
		System.out.println("3. Consultar pedidos que se han hecho");
		System.out.println("4. Consultar combos");
		System.out.println("14. Salir de la aplicacion");
	}

	public void ejecutarCombos() throws FileNotFoundException, IOException {
		boolean continuar = true;
		cargarCombos();

		while (continuar) {
			try {
				mostrarMenuCombo();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 1) {
					crearPedidoCombo();
				} else if (opcion_seleccionada == 2)
					{editarCombo();}
				else if (opcion_seleccionada == 3) 
				{
				MostrarPedidos();	
				} else if (opcion_seleccionada == 4) {
					{Combos.forEach(
					        (combo, precio)
					        -> System.out.println("+--------------------+\n"+combo + " \nDescuento: " + precio[0]+ 
					        		" \nHaburguesa: " + precio[1]
					        		+ " \nAdicion: " + precio[2]+ " \nBebida: " + precio[3]+
					        		"\n"));}
				} else if (opcion_seleccionada == 14) {
					System.out.println("Saliendo de combos ...");
					continuar = false;
				}
			}

			catch (NumberFormatException e) {
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}

		}
	}
	
	
	public static String toString(int[] a) { {
		       if (a == null)
		           return "null";
		        int iMax = a.length - 1;
		       if (iMax == -1)
		            return "[]";
		
		       StringBuilder b = new StringBuilder();
		        b.append('[');
		        for (int i = 0; ; i++) {
		            b.append(a[i]);
		          if (i == iMax)
		               return b.append(']').toString();
		           b.append(", ");
        }}
		    }
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Pedido consola = new Pedido();
		consola.ejecutarPedidos();
}
	}
	
	


