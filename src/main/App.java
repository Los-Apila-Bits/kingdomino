package main;
import java.io.FileNotFoundException;

import juego.*;
public class App {
	public static void main(String[] args) throws FileNotFoundException {
        Tablero tablero = new Tablero();
        Ficha ficha1 = new Ficha(0,new Terreno("rio",1),new Terreno("rio",0));
        
        ficha1.girarFicha();
        tablero.puedeInsertar(2, 3, ficha1);
        System.out.println(tablero.getTablero()[2][2].getTipo());
        tablero.insertarFicha(ficha1, 2, 3);
        System.out.println(tablero.getTablero()[2][3].getTipo());
        System.out.println(tablero.getTablero()[1][3].getTipo());
        //int[] direccion1 = {1,0};
        //tablero.insertarFicha(ficha1, 3, 4);
        System.out.println(tablero.contarPuntos());
		}
}

