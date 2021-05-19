package juego;

public class Tablero {
		private Terreno[][] tablero = new Terreno[5][5]; 
		private int puntos = 0;
		
		public int contarPuntos() {
			return 1;
		}
		
		public boolean puedeInsertar() {
			return true;
		}
		
		public void insertarFicha(Ficha ficha) {
			return;
		}
		
		public void actualizarTablero() {
			return;
		}
}
