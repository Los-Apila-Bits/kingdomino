package juego;

import java.util.ArrayList;

public class Tablero {
	private Terreno[][] tablero = new Terreno[5][5];
	private int puntos = 0;
	private ArrayList<CadenaTerrenos> rio = new ArrayList<CadenaTerrenos>();
	private ArrayList<CadenaTerrenos> campo = new ArrayList<CadenaTerrenos>();
	private ArrayList<CadenaTerrenos> pradera = new ArrayList<CadenaTerrenos>();
	private ArrayList<CadenaTerrenos> mina = new ArrayList<CadenaTerrenos>();
	private ArrayList<CadenaTerrenos> bosque = new ArrayList<CadenaTerrenos>();
	private ArrayList<CadenaTerrenos> pantano = new ArrayList<CadenaTerrenos>();

	public Tablero() {
		tablero[2][2] = new Terreno(Terreno.CASTILLO, 0);
	}

	public int contarPuntos() {
		this.puntos = 0;
		for (CadenaTerrenos cadenaTerrenos : rio) {
			this.puntos += cadenaTerrenos.getPuntos();
		}
		for (CadenaTerrenos cadenaTerrenos : campo) {
			this.puntos += cadenaTerrenos.getPuntos();
		}
		for (CadenaTerrenos cadenaTerrenos : pradera) {
			this.puntos += cadenaTerrenos.getPuntos();
		}
		for (CadenaTerrenos cadenaTerrenos : mina) {
			this.puntos += cadenaTerrenos.getPuntos();
		}
		for (CadenaTerrenos cadenaTerrenos : bosque) {
			this.puntos += cadenaTerrenos.getPuntos();
		}
		for (CadenaTerrenos cadenaTerrenos : pantano) {
			this.puntos += cadenaTerrenos.getPuntos();
		}
		return this.puntos;

		// recorro todas las listas y cuento los puntos de cada lista
	}

	public boolean puedeInsertar(int posX, int posY, Ficha ficha) {
		if (fueraDeTablero(posX, posY, ficha.getDireccion()[0], ficha.getDireccion()[1])) {
			System.out.println("Ficha fuera del tablero");
			return false;
		}
		
		if((hayEspacio(posX, posY)&& hayEspacio(posX + ficha.getDireccion()[0], posY + ficha.getDireccion()[1]))) {
			System.out.println("Hay espacio en el tablero");
			if ((compararTerrenoAledanio(posX, posY, ficha.getTerreno1()))
				|| compararTerrenoAledanio(posX + ficha.getDireccion()[0], posY + ficha.getDireccion()[1],ficha.getTerreno2())) {
				System.out.println("Los terrenos aledanios coinciden");
				return true;
			}
		}
		return false;
		// tenemos que validar que haya espacio donde tenemos que poner la
		// ficha 
	}

	public boolean insertarFicha(Ficha ficha, int posX, int posY) {
		
		System.out.println("posX: " + posX + " posY: " + posY + " puedaInsertar: " + puedeInsertar(posX,posY,ficha));
		System.out.println("dir0: " + ficha.getDireccion()[0] + " dir1: " + ficha.getDireccion()[1]);
		if (!puedeInsertar(posX, posY, ficha)) {
			ficha.cambiarPivot();
			if (!puedeInsertar(posX, posY, ficha))
				return false;
		}
		tablero[posX][posY] = ficha.getTerreno1();
		tablero[posX + ficha.getDireccion()[0]][posY + ficha.getDireccion()[1]] = ficha.getTerreno2();
		generarEntradasLista(ficha.getTerreno1(), posX, posY, ficha.getDireccion()[0], ficha.getDireccion()[1],
				selectList(ficha.getTerreno1()));
		generarEntradasLista(ficha.getTerreno2(), posX + ficha.getDireccion()[0], posY + ficha.getDireccion()[1], 0, 0,
				selectList(ficha.getTerreno2()));
		contarPuntos();
		return true;
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
		if (cadena == null) {
			// en caso de que no tenga otros terrenos adyacentes del mismo tipo, generamos
			// una nueva cadena de terrenos y lo agregamos a la lista de cadena de terrenos

			lista.add(new CadenaTerrenos(terreno1.getCantCoronas(), posicion));
		}

	}

	public boolean compararTerrenoAledanio(int posX, int posY, Terreno terreno) {
//		System.out.println("posx: "+posX+" posy: "+posY);
//		System.out.println("Tam tablero: "+tablero.length);
		if (posX < tablero.length - 1 && terreno.compararTerreno(tablero[posX + 1][posY]))
			return true;	
		if (posX > 0 && terreno.compararTerreno(tablero[posX - 1][posY])) 
				return true;
		if (posY < tablero.length - 1 && terreno.compararTerreno(tablero[posX][posY + 1]))
				return true;
		if (posY > 0 && terreno.compararTerreno(tablero[posX][posY - 1]))
				return true;
		return false;
	}

	private boolean fueraDeTablero(int posX, int posY, int direccionX, int direccionY) {
		return posX + direccionX < 0 || posY + direccionY < 0 || posX + direccionX > (this.tablero.length - 1)
				|| posY + direccionY > (this.tablero[0].length - 1);
	}

	private boolean hayEspacio(int posX, int posY) {
		return tablero[posX][posY] == null;
	}

	private ArrayList<CadenaTerrenos> selectList(Terreno terreno) {
		if (terreno.getTipo() == Terreno.RIO)
			return this.rio;
		if (terreno.getTipo() == Terreno.BOSQUE)
			return this.bosque;
		if (terreno.getTipo() == Terreno.MINA)
			return this.mina;
		if (terreno.getTipo() == Terreno.CAMPO)
			return this.campo;
		if (terreno.getTipo() == Terreno.PANTANO)
			return this.pantano;
		return this.pradera;
	}

	private CadenaTerrenos recorrerAgregarLista(Terreno terreno1, int posX, int posY, int desplazamientoX,
			int desplazamientoY, int posOtroTerrenoX, int posOtroTerrenoY, ArrayList<CadenaTerrenos> lista,
			CadenaTerrenos cadena) {
		Integer[] posicion = { posX, posY };
		if ((posOtroTerrenoX == desplazamientoX && posOtroTerrenoY == desplazamientoY)) {

			return cadena;
		}
		if (fueraDeTablero(posX, posY, desplazamientoX, desplazamientoY)) {
			return cadena;
		}

		
		if (terreno1.compararTerreno(tablero[posX + desplazamientoX][posY + desplazamientoY])) {
			Integer[] posicionBuscada = { posX + desplazamientoX, posY + desplazamientoY };
			for (CadenaTerrenos cadenaTerrenos : lista) {
				if (cadenaTerrenos.contienePosicion(posicionBuscada)) {
					if (cadena != null) {
						cadena.fusionarCadenas(cadenaTerrenos);
					} else {
						cadena = cadenaTerrenos.sumarFicha(terreno1.getCantCoronas(), posicion);
					}
					break;
				}

			}
		}
		return cadena;
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
		
	}

	public void mostrarTablero() {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				if (tablero[i][j] != null)
					System.out.print(String.format("%-10s", tablero[i][j].getTipo()));
				else
					System.out.print(String.format("%-10s", "vacio"));
			}
			System.out.println();
		}
	}

	public Terreno[][] getTablero() {
		return tablero;
	}
	
	public int getPuntos() {
		return this.puntos;
	}

}
