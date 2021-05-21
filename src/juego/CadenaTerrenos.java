package juego;

import java.util.ArrayList;

public class CadenaTerrenos {
	
	private int coronas;
	private ArrayList<Integer[]> posiciones = new ArrayList<Integer[]>();
	
	public CadenaTerrenos(int coronas, Integer[] posicion) {
		this.coronas = coronas;
		this.posiciones.add(posicion);
	}
	
	public void sumarFicha (int coronas, Integer[] posicion) {
		this.posiciones.add(posicion);
		this.coronas += coronas;
	}
	
	public boolean contienePosicion (Integer[] posicion) {
		return this.posiciones.contains(posicion);
	}
	
	public void fusionarCadenas (ArrayList<CadenaTerrenos> cadenas) {
		for (CadenaTerrenos cadena : cadenas) { 		      
			this.coronas += cadena.getCoronas();
			this.posiciones.addAll(cadena.getPosiciones());
	    }
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