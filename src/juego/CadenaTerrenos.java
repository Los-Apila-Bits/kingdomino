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
		for (Integer[] integer : this.posiciones) {
			if(posicion[0]== integer[0]&&posicion[1]==integer[1])
				return true;
		}
		return false;
	}
	
	public void fusionarCadenas (CadenaTerrenos cadenas) {
				      
			this.coronas += cadenas.getCoronas();
			this.posiciones.addAll(cadenas.getPosiciones());
			cadenas.getPosiciones().clear();
			
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
