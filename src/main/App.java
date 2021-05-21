package main;
import java.io.FileNotFoundException;

import juego.*;
public class App {
	public static void main(String[] args) throws FileNotFoundException {
		Mazo m = new Mazo();
		//System.out.println(m.fichas.size());
		for (int i = 0; i < 48; i++) {
			System.out.println(m.sacarFicha());
		}
	}
}
