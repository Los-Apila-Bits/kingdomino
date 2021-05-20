package juego;

public class Tablero {
		private Terreno[][] tablero = new Terreno[5][5]; 
		private int puntos = 0;
		
		public int contarPuntos() {
			return 1;
		}
		public boolean puedeInsertar(int posX, int posY, Terreno terreno, int[] direccion) {
			return (!fueraDeTablero(posX, posY, direccion) && compararTerrenoAledanio(posX, posY, terreno))
				&& (hayEspacioAledanio(posX, posY));
		}
		
		public void insertarFicha(Ficha ficha, int posX, int posY, int[] direccion) {
			tablero[posX][posY] = ficha.getTerreno1();
			tablero[posX-direccion[0]][posY-direccion[1]] = ficha.getTerreno2();
			//pasamos la ficha a insertar, la posicion donde la vamos a ubicar y un vector de int que
			//tiene en que posicion, si va hacia la izquierda los valores del vector serian -1 y 0
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

		private boolean fueraDeTablero(int posX, int posY, int[] direccion) {
			return posX + direccion[0] < 0 || posY + direccion[1] < 0 || posX + direccion[0] == this.tablero.length
					|| posX + direccion[0] == this.tablero.length;
		}

		private boolean hayEspacioAledanio(int posX, int posY) {
			return tablero[posX][posY - 1] == null || tablero[posX - 1][posY] == null || tablero[posX][posY + 1] == null
					|| tablero[posX + 1][posY] == null;
		}
}
