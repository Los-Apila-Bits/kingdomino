package juego;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Partida {
//		private int rondasRestantes;
//		private int numerMovimientos;
		private List<Jugador> jugadores;
		private Mazo mazo;
		private List<Ficha> fichasRonda;
		private Turno turno;
		
		public Partida(List<Jugador> jugadores) throws FileNotFoundException {
			this.jugadores = jugadores;
			mazo = new Mazo();
			turno = new Turno();
		}
		
		public void ejecutar() {
			int cantIntentos = 0;
			Scanner entrada = new Scanner(System.in);
			int eleccion;
			int posx,posy;
			while(!mazo.mazoVacio()) {
				fichasRonda = turno.sacarFichas(mazo);
				for ( Jugador jugador : jugadores) {
					System.out.println(fichasRonda);
					System.out.print("Elija Una Ficha: ");
					eleccion = entrada.nextInt()-1;
					jugador.elegirFicha(fichasRonda.get(eleccion));
					System.out.println("Ubique la ficha en el tablero");
					System.out.print("pos x: ");
					posx = entrada.nextInt();
					System.out.print("pos y: ");
					posy = entrada.nextInt();
					System.out.print("Si desea girar la ficha escriba 1 en caso contrario 0: ");
					eleccion = entrada.nextInt();
					System.out.println(jugador.getFicha());
					while(eleccion!=0) {
						jugador.getFicha().girarFicha();
						System.out.print("Si desea girar la ficha escriba 1 en caso contrario 0: ");
						eleccion = entrada.nextInt();
						System.out.println(jugador.getFicha());
					}
					System.out.println(jugador.getFicha());
					while(!jugador.ubicarFicha(posx, posy) && cantIntentos++ < 3){ // parche provisorio (ver logica tablero)
						System.out.println("Elija una ubicacion correcta\n");
						System.out.print("pos x: ");
						posx = entrada.nextInt();
						System.out.print("pos y: ");
						posy = entrada.nextInt();
						System.out.println(jugador.getFicha());
					}
					System.out.println();
					jugador.getTablero().mostrarTablero();
					cantIntentos=0;
					System.out.println("\n\n");
				}
				turno.ordenarTurnos(jugadores);
				fichasRonda.clear();
			}
			Collections.sort(jugadores,(j1,j2)->j1.getTablero().getPuntos()-j2.getTablero().getPuntos());
			System.out.println(jugadores);
		}

//		
//		private void restarTurnos() {
//			
//		}
//		private void compararPuntos() {
//			
//		}
}
