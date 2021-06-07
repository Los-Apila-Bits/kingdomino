package juego;

import java.util.Collections;
import java.util.List;

public class Turno {
	private Ficha[] fichas = new Ficha[4];
	
//	public int primerJugador(Jugador jugadores[]) {
//		return 1;
//	}
//	
//	public int siguienteJugador(Jugador jugadores[]) {
//		return 1;
//	}
//	
//	public void ordenarFichas(Ficha fichas[]) {
//		
//	}
	public void ordenarTurnos(List<Jugador> jugadores) {
		Collections.sort(jugadores,(j1,j2)->comparador(j1,j2));
	}

	public int comparador(Jugador j1, Jugador j2) {
		return j1.getFicha().compararNumFicha(j2.getFicha());
	}
}
