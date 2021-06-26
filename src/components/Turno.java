package components;

import java.util.ArrayList;

import juego.Mazo;
import views.ViewPartida;

public class Turno {
	
	private ArrayList<Ficha> fichasTurno;
	private ArrayList<Ficha> fichasSeleccionadas;
	
	public Turno(Mazo m){
		fichasTurno = new ArrayList<Ficha>();
		for(int i=0;i<4;i++) {
			int[] datosFicha = m.sacarFicha();
			fichasTurno.add(new Ficha(datosFicha[1],datosFicha[2],datosFicha[3],datosFicha[4],ViewPartida.TAM_PREV));
		}
		fichasSeleccionadas = new ArrayList<Ficha>();
	}
	
	public ArrayList<Ficha> getFichas() {
		return this.fichasTurno;
	}
}
