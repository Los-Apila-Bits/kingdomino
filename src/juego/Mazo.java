package juego;

import java.util.ArrayList;
import java.util.List;

public class Mazo {
		private List<Ficha> fichas = new ArrayList<Ficha>();
		
		public void mezclar() {
			return;
		}
		public Ficha sacarFicha() {
			return fichas.remove(0);
		}
		
		public Mazo() {
			for (int i = 0; i <= 48; i++) {
				fichas.add(new Ficha(i,new Terreno(), new Terreno()));
			};
		}
}
