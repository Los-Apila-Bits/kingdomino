package juego;

public class Jugador {
		private int idJugador;
		private static int contJugadores = 1;
		private String nombre;
		private String color;
		private boolean estado;
		private Tablero tablero;
		private Ficha ficha;
		
		public Jugador(String nombre, String color) {
			this.nombre = nombre;
			this.color = color;
			this.idJugador = contJugadores++;
			this.tablero = new Tablero();
			this.ficha = new Ficha(0, null, null);
			
		}
		
		
		public void elegirFicha (Ficha ficha) {
			this.ficha = ficha;
		}
		
		public void ubicarFicha(int x, int y) {
			tablero.insertarFicha(ficha, x, y);
		}
		
		public boolean getEstado() {
			return this.estado;
		}
		
		public int getId() {
			return this.idJugador;
		}
		
		public String getNombre() {
			return this.nombre;
		}
		
		public String getColor() {
			return this.color;
		}
		
		
//		public void crearSala(String nombreSala) {
//			
//		}
//		
//		public void entrarSala(int idSala) {
//			
//		}
//		
//		public void salirSala() {
//			
//		}
}
