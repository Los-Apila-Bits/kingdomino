package juego;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Tablero {
	private Terreno[][] tablero = new Terreno[5][5];
	private int puntos = 0;
	private ArrayList<CadenaTerrenos> agua = new ArrayList<CadenaTerrenos>();
	private ArrayList<CadenaTerrenos> desierto = new ArrayList<CadenaTerrenos>();
	private ArrayList<CadenaTerrenos> llanura = new ArrayList<CadenaTerrenos>();
	private ArrayList<CadenaTerrenos> mina = new ArrayList<CadenaTerrenos>();
	private ArrayList<CadenaTerrenos> bosque = new ArrayList<CadenaTerrenos>();
	private ArrayList<CadenaTerrenos> yermo = new ArrayList<CadenaTerrenos>();

	/*
	 * private HashMap<Terreno, CadenaTerrenos> cadenasAgua = new HashMap<Terreno,
	 * CadenaTerrenos>(); private HashMap<Terreno, CadenaTerrenos> cadenasDesierto =
	 * new HashMap<Terreno, CadenaTerrenos>(); private HashMap<Terreno,
	 * CadenaTerrenos> cadenasLlanura = new HashMap<Terreno, CadenaTerrenos>();
	 * private HashMap<Terreno, CadenaTerrenos> cadenasMina = new HashMap<Terreno,
	 * CadenaTerrenos>(); private HashMap<Terreno, CadenaTerrenos> cadenasBosque =
	 * new HashMap<Terreno, CadenaTerrenos>(); private HashMap<Terreno,
	 * CadenaTerrenos> cadenasYermo = new HashMap<Terreno, CadenaTerrenos>();
	 */
	public int contarPuntos() {
		int puntosTotales = 0;
		for (CadenaTerrenos cadenaTerrenos : agua) {
			puntosTotales += cadenaTerrenos.getPuntos();
		}
		for (CadenaTerrenos cadenaTerrenos : desierto) {
			puntosTotales += cadenaTerrenos.getPuntos();
		}
		for (CadenaTerrenos cadenaTerrenos : llanura) {
			puntosTotales += cadenaTerrenos.getPuntos();
		}
		for (CadenaTerrenos cadenaTerrenos : mina) {
			puntosTotales += cadenaTerrenos.getPuntos();
		}
		for (CadenaTerrenos cadenaTerrenos : bosque) {
			puntosTotales += cadenaTerrenos.getPuntos();
		}
		for (CadenaTerrenos cadenaTerrenos : yermo) {
			puntosTotales += cadenaTerrenos.getPuntos();
		}
		return puntosTotales;
		
		//recorro todas las listas y cuento los puntos de cada lista
	}

	public boolean puedeInsertar(int posX, int posY, Terreno terreno, int[] direccion) {
		return (!fueraDeTablero(posX, posY, direccion[0], direccion[1]) && compararTerrenoAledanio(posX, posY, terreno))
				&& (hayEspacioAledanio(posX, posY));
		// tenemos que validar que haya espacio para el lado que tenemos que poner la
		// ficha
	}

	public void insertarFicha(Ficha ficha, int posX, int posY, int[] direccion) {
		boolean hayCadena = false;
		int[] desplazamientoNulo = { 0, 0 };
		tablero[posX][posY] = ficha.getTerreno1();
		tablero[posX + direccion[0]][posY + direccion[1]] = ficha.getTerreno2();
		generarEntradasLista(ficha.getTerreno1(), posX, posY, direccion[0], direccion[1],
				selectList(ficha.getTerreno1()));
		generarEntradasLista(ficha.getTerreno2(), posX + direccion[0], posY + direccion[1], 0, 0,
				selectList(ficha.getTerreno2()));

		// pasamos la ficha a insertar, la posicion donde la vamos a ubicar y un vector
		// de int que
		// tiene en que posicion, si va hacia la izquierda los valores del vector serian
		// -1 y 0
	}

	private void generarEntradasLista(Terreno terreno1, int posX, int posY, int posOtroTerrenoX, int posOtroTerrenoY,
			ArrayList<CadenaTerrenos> lista) {
		CadenaTerrenos cadena = null; 
		Integer[] posicion = { posX, posY };
		cadena = recorrerAgregarLista(terreno1, posX, posY, 1, 0, posOtroTerrenoX, posOtroTerrenoY, lista, cadena);
		cadena = recorrerAgregarLista(terreno1, posX, posY, -1, 0, posOtroTerrenoX, posOtroTerrenoY, lista, cadena);
		cadena = recorrerAgregarLista(terreno1, posX, posY, 0, 1, posOtroTerrenoX, posOtroTerrenoY, lista, cadena);
		cadena = recorrerAgregarLista(terreno1, posX, posY, 0, -1, posOtroTerrenoX, posOtroTerrenoY, lista, cadena);
		if(cadena==null) {
			// en caso de que no tenga otros terrenos adyacentes del mismo tipo, generamos
			// una nueva cadena de terrenos y lo agregamos a la lista de cadena de terrenos

			lista.add(new CadenaTerrenos(terreno1.getCantCoronas(), posicion));
		}

	}

	public void actualizarTablero() {
		return;
	}

	public boolean esBorde(int posX, int posY) {
		return posX == 0 || posY == 0 || posX == this.tablero.length || posY == this.tablero[0].length;
	}

	private boolean compararTerrenoAledanio(int posX, int posY, Terreno terreno) {
		return terreno.compararTerreno(tablero[posX + 1][posY]) || terreno.compararTerreno(tablero[posX - 1][posY])
				|| terreno.compararTerreno(tablero[posX][posY + 1]) || terreno.compararTerreno(tablero[posX][posY - 1]);
	}

	private boolean fueraDeTablero(int posX, int posY, int direccionX, int direccionY) {
		return posX + direccionX < 0 || posY + direccionY < 0 || posX + direccionX == this.tablero.length
				|| posX + direccionY == this.tablero.length;
	}

	private boolean hayEspacioAledanio(int posX, int posY) {
		return tablero[posX][posY - 1] == null || tablero[posX - 1][posY] == null || tablero[posX][posY + 1] == null
				|| tablero[posX + 1][posY] == null;
	}

	public Tablero() {
		tablero[2][2] = new Terreno("castillo", 0);
	}

	private ArrayList<CadenaTerrenos> selectList(Terreno terreno) {
		if (terreno.getTipo() == "agua")
			return this.agua;
		if (terreno.getTipo() == "bosque")
			return this.bosque;
		if (terreno.getTipo() == "mina")
			return this.mina;
		if (terreno.getTipo() == "desierto")
			return this.desierto;
		if (terreno.getTipo() == "yermo")
			return this.agua;

		return this.llanura;

	}

	private CadenaTerrenos recorrerAgregarLista(Terreno terreno1, int posX, int posY, int desplazamientoX,
			int desplazamientoY, int posOtroTerrenoX, int posOtroTerrenoY, ArrayList<CadenaTerrenos> lista,
			CadenaTerrenos cadena) {
		Integer[] posicion = { posX, posY };
		if ((posOtroTerrenoX != desplazamientoX || posOtroTerrenoY != desplazamientoY)
				&& !fueraDeTablero(posX, posY, desplazamientoX, desplazamientoY)
				&& terreno1.compararTerreno(tablero[posX + desplazamientoX][posY + desplazamientoY])) {
			Integer[] posicionBuscada = { posX + desplazamientoX, posY + desplazamientoY };
			for (CadenaTerrenos cadenaTerrenos : lista) {
				if (cadenaTerrenos.contienePosicion(posicionBuscada)) {
					if (cadena != null)
						cadena.fusionarCadenas(cadenaTerrenos);
					else
						cadena = cadenaTerrenos.sumarFicha(terreno1.getCantCoronas(), posicion);
					break;
				}

			}
		}
		// creamos un objeto cadenaTerrenos al cual vamos a apuntar si hay mas de una
		// cadena de terrenos
		// casteamos las posiciones del terreno a un Integer[]
		// por cada if preguntamos si estamos viendo la posicion del otro terreno anexo
		// a nuestra ficha (solo en el caso de la ficha pivot), despues preguntamos si
		// nos salimos del tablero preguntando para un lado nuestro, asi no se rompe
		// todo cuando queres acceder a una posicion del tablero que esta fuera de
		// limite, y por ultimo preguntamos si la posicion adyacente a la nuestra es de
		// nuetro tipo
		
		// en caso de que la posicion este en una de las listas,agregamos la posicion y
		// guardamos en
		// "hayLista" la cadena de terrenos que la tiene, en caso de que tengamos que
		// anexar mas listas a esa cadena
		// salimos del for
		return cadena;
	}

}
