package main;
import java.io.FileNotFoundException;

import juego.*;
public class App {
	public static void main(String[] args) throws FileNotFoundException {
        Tablero tablero = new Tablero();
        Ficha ficha1 = new Ficha(0,new Terreno("rio",1),new Terreno("rio",0));
        
        System.out.println(ficha1.getDireccion()[0]+" "+ficha1.getDireccion()[1]);
        tablero.insertarFicha(ficha1, 1, 2);
        tablero.insertarFicha(ficha1, 1, 4);
        tablero.insertarFicha(ficha1, 0, 2);
        tablero.insertarFicha(ficha1, 0, 4);
        tablero.insertarFicha(ficha1, 3, 2);
        tablero.insertarFicha(ficha1, 3, 4);
        tablero.insertarFicha(ficha1, 4, 2);
        tablero.insertarFicha(ficha1, 4, 4);
        tablero.insertarFicha(ficha1, 2, 1);
        tablero.insertarFicha(ficha1, 2, 4);
        ficha1.girarFicha();
        tablero.insertarFicha(ficha1, 1, 0);
        tablero.insertarFicha(ficha1, 4, 0);
        tablero.mostrarTablero();
      //  System.out.println(tablero.fueraDeTablero(4, 2, ficha1.getDireccion()[0],ficha1.getDireccion()[1]));
       // System.out.println(tablero.fueraDeTablero(4, 4, ficha1.getDireccion()[0],ficha1.getDireccion()[1]));

        System.out.println(tablero.contarPuntos());
        //System.out.println(tablero.puedeInsertar(1, 4, ficha1));
		}
}

