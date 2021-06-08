package juego;

public class Ficha {
	private int numeroDeFicha;
	private Terreno[] ficha = new Terreno[2]; // terreno 1 es pivot por defecto 
	private boolean estado;
	private int[] direccion = { 0, 1 }; // terreno 2 esta por defecto a la derecha
	
	public Ficha(int numeroDeFicha, Terreno terreno1, Terreno terreno2) {
		super();
		this.numeroDeFicha = numeroDeFicha;
		this.ficha[0] = terreno1;
		this.ficha[1] = terreno2;
		this.estado = true;
	}

	public int[] girarFicha() {
		if (direccion[0] == 0 && direccion[1] == -1) {
			direccion[0] = -1;
			direccion[1] = 0;
		} else {
			if (direccion[0] == -1 && direccion[1] == 0) {
				direccion[0] = 0;
				direccion[1] = 1;
			} else {
				if (direccion[0] == 0 && direccion[1] == 1) {
					direccion[0] = 1;
					direccion[1] = 0;
				} else {
					direccion[0] = 0;
					direccion[1] = -1;
				}
			}
		}
		return direccion;
	}
	
	public boolean getEstado() {
		return this.estado;
	}
	
	public void setEstado() {
		this.estado = false;
	}

	public int compararNumFicha(Ficha otra) {
		return otra.numeroDeFicha-this.numeroDeFicha;
	}
	
	public Terreno getTerreno1() {
		return this.ficha[0];
	}

	public Terreno getTerreno2() {
		return this.ficha[1];
	}
	
	public String toString() {
		return this.ficha[0].getTipo()+" "+this.ficha[0].getCantCoronas()+" "+this.ficha[1].getTipo()+" "+this.ficha[1].getCantCoronas();
	}
	
	public int[] getDireccion() {
		return this.direccion;
	}
	
	public void cambiarPivot( ) {
		Terreno aux = this.ficha[0];
		this.ficha[0] = this.ficha[1];
		this.ficha[1] = aux;	
	}
}
