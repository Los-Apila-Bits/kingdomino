package juego;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Mazo {
	public List<int[]> fichas = new ArrayList<int[]>();

	public Mazo() throws FileNotFoundException {
		Scanner entrada = new Scanner(new File("mazo.txt"));
		int i = 1;
		int[] vec = new int[5];
		while (entrada.hasNext()) {
			vec[0] = i;
			for (int j = 1; j < 5; j++) {
				vec[j]= entrada.nextInt();
			}
			i++;
			fichas.add(vec);
		}
		entrada.close();
		mezclar();
	}
	
	public List<int[]> getFichas() {
		return fichas;
	}


	private void mezclar() {
		Collections.shuffle(fichas);
	}

	public int[] sacarFicha() {
		return fichas.remove(0);
	}
	
	public boolean mazoVacio() {
		return fichas.isEmpty();
	}
	
	public int comparador(int[] ficha1, int[] ficha2) {
		return ficha1[0]-ficha2[0];
	}
	
}
