package juego;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Mazo {
	private List<Ficha> fichas = new ArrayList<Ficha>();

	public Mazo() throws FileNotFoundException {
		Scanner entrada = new Scanner(new File("mazo.txt"));
		while (entrada.hasNext()) {
			int i = 1;
			fichas.add(new Ficha(i, new Terreno(entrada.next(), entrada.nextInt()),
					new Terreno(entrada.next(), entrada.nextInt())));
			i++;
		}
		entrada.close();
		mezclar();
	}

	private void mezclar() {
		Collections.shuffle(fichas);
	}

	public Ficha sacarFicha() {
		return fichas.remove(0);
	}
}
