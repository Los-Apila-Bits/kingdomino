package juego;

import java.util.ArrayList;

public class CadenaTerrenos {
	
	private int coronas;
	private ArrayList<Integer[]> posiciones = new ArrayList<Integer[]>();
	
	public CadenaTerrenos(int coronas, Integer[] posicion) {
		this.coronas = coronas;
		this.posiciones.add(posicion);
	}
	
	public CadenaTerrenos sumarFicha (int coronas, Integer[] posicion) {
		this.posiciones.add(posicion);
		this.coronas += coronas;
		return this;
	}
	
	public boolean contienePosicion (Integer[] posicion) {
		return this.posiciones.contains(posicion);
	}
	
	public void fusionarCadenas (CadenaTerrenos cadenas) {
				      
			this.coronas += cadenas.getCoronas();
			this.posiciones.addAll(cadenas.getPosiciones());
	}

	public ArrayList<Integer[]> getPosiciones() {
		return posiciones;
	}
	
	public int getCoronas() {
		return coronas;
	}
	
	public int getPuntos() {
		return this.coronas * this.posiciones.size();
	}
}
