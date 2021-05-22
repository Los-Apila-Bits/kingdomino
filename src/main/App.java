package main;
import java.io.FileNotFoundException;

import juego.*;
public class App {
	public static void main(String[] args) throws FileNotFoundException {
        Tablero tablero = new Tablero();
        Ficha ficha1 = new Ficha(0,new Terreno("rio",1),new Terreno("rio",0));
        
        System.out.println(ficha1.getDireccion()[0]+", "+ficha1.getDireccion()[1]);
        tablero.insertarFicha(ficha1, 1, 2);
        tablero.mostrarTablero();

        //tablero.insertarFicha(ficha1, 3, 4);
        System.out.println(tablero.contarPuntos());
		}
}

