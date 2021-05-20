package juego;

public class Terreno {
	private String tipo;
	private int cantCoronas;
	
	public Terreno(String tipo, int cantCoronas) {
		this.tipo = tipo;
		this.cantCoronas = cantCoronas;
	}
	
	public int getCoronas() {
		return this.cantCoronas;
	}
	
	public boolean compararTerreno(Terreno terreno2) {
		return terreno2 != null && this.tipo == terreno2.tipo;
	}
}
