package juego;

import java.util.ArrayList;
import java.util.List;

public class Mazo {
		private List<Ficha> fichas = new ArrayList<Ficha>();
		
		public void ezclar() {
			return;
		}
		
		public void sacarFichas() {
			return;
		}
		public Mazo() {
			for (int i = 0; i <= 48; i++) {
				fichas.add(new Ficha(i,new Terreno(), new Terreno()));
			};
		}
}
