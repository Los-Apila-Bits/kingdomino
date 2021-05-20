package juego;

public class Terreno {
	private String tipo;
	private int cantCoronas;
	
	public boolean compararTerreno(Terreno terreno2) {
		return terreno2 != null && this.tipo == terreno2.tipo;
	}
}
