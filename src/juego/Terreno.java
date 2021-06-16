package juego;

public class Terreno {
	
	public static final int CASTILLO = 0;
	public static final int CAMPO = 1;
	public static final int BOSQUE = 2;
	public static final int PRADERA = 3;
	public static final int RIO = 4;
	public static final int PANTANO = 5;
	public static final int MINA = 6;
	
	//private final String CASTILLO = "castillo";
	//private String tipo;
	private int tipo;
	private int cantCoronas;
	
	
	public boolean compararTerreno(Terreno terreno2) {
		if(terreno2 == null)
			return false;
		return  this.tipo == terreno2.tipo || terreno2.tipo == CASTILLO;
	}

	public Terreno(int tipo, int cantCoronas) {
		
		this.tipo = tipo;
		this.cantCoronas = cantCoronas;
	}

	public int getTipo() {
		return tipo;
	}

	public int getCantCoronas() {
		return cantCoronas;
	}
//	public boolean compararTerreno(Terreno terreno2) {
//		if(terreno2 == null)
//			return false;
//		return  this.tipo.equals(terreno2.tipo) || terreno2.tipo == CASTILLO;
//	}
	
//	public Terreno(String tipo, int cantCoronas) {
//		
//		this.tipo = tipo;
//		this.cantCoronas = cantCoronas;
//	}
	
//	public String getTipo() {
//		return tipo;
//	}
}
