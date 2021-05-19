package juego;

public class Terreno {
	private String tipo;
	private int cantCoronas;
	
	public boolean compararTerreno(Terreno terreno2) {
		return this.tipo == terreno2.tipo;
	}
}
