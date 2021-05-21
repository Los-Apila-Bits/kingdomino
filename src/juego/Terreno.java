package juego;

public class Terreno {
	private final String CASTILLO = "castillo";
	private String tipo;
	private int cantCoronas;
	
	public boolean compararTerreno(Terreno terreno2) {
		if(terreno2 == null)
			return false;
		return  this.tipo == terreno2.tipo || terreno2.tipo == CASTILLO;
	}

	public Terreno(String tipo, int cantCoronas) {
		
		this.tipo = tipo;
		this.cantCoronas = cantCoronas;
	}

	public String getTipo() {
		return tipo;
	}

	public int getCantCoronas() {
		return cantCoronas;
	}
}
