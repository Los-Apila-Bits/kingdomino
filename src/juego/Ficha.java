package juego;

public class Ficha {
	private int numeroDeFicha;
	private Terreno[] ficha = new Terreno[2]; // terreno 1 es pivot por defecto 
	private boolean estado;
	private int[] dir = { 0, -1 }; // terreno 2 esta por defecto a la derecha
	
	public Ficha(int numeroDeFicha, Terreno terreno1, Terreno terreno2) {
		super();
		this.numeroDeFicha = numeroDeFicha;
		this.ficha[0] = terreno1;
		this.ficha[1] = terreno2;
		this.estado = true;
	}

	public int[] girarFicha() {
		if (dir[0] == 0 && dir[1] == -1) {
			dir[0] = -1;
			dir[1] = 0;
		} else {
			if (dir[0] == -1 && dir[1] == 0) {
				dir[0] = 0;
				dir[1] = 1;
			} else {
				if (dir[0] == 0 && dir[1] == 1) {
					dir[0] = 1;
					dir[1] = 0;
				} else {
					dir[0] = 0;
					dir[1] = -1;
				}
			}
		}
		return dir;
	}
	public boolean getEstado() {
		return this.estado;
	}
	
	public void setEstado() {
		this.estado = false;
	}

//	public void compararNumFicha() { //metodo duduso
//		return;
//	}

	public Terreno getTerreno1() {
		return this.ficha[0];
	}

	public Terreno getTerreno2() {
		return this.ficha[1];
	}
}
