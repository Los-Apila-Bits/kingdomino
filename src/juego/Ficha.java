package juego;

public class Ficha {
		private int numeroDeFicha;
		private Terreno[] ficha = new Terreno[2]; 
		private boolean estado;
		
		public void girarFicha() {
			return;
		}
		
		public void compararNumFicha() {
			return;
		}
		public Ficha(int numeroDeFicha, Terreno terreno1, Terreno terreno2) {
			super();
			this.numeroDeFicha = numeroDeFicha;
			this.ficha[0] = terreno1;
			this.ficha[1] = terreno2;
			this.estado = true;
		}
		public Terreno getTerreno1() {
			return this.ficha[0];
		}
		public Terreno getTerreno2() {
			return this.ficha[0];
		}
}
