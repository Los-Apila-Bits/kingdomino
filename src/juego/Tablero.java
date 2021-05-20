package juego;

public class Tablero {
		private Terreno[][] tablero = new Terreno[5][5]; 
		private int puntos = 0;
		
		public int contarPuntos() {
			return 1;
		}
		public boolean puedeInsertar(int posX, int posY, Terreno terreno) {
			return tablero[posX][posY].compararTerreno(terreno);
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
}
