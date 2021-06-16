package juego;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Partida {
//		private int rondasRestantes;
//		private int numeroMovimientos;
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
				Collections.sort(fichasRonda, (j1,j2)->j2.compararNumFicha(j2)); //Ordeno para que queden los numero de fichas más bajos adelante
				for ( Jugador jugador : jugadores) {
					System.out.println(jugador.getNombre()+"\n");
					jugador.getTablero().mostrarTablero();
					System.out.println(fichasRonda);
					System.out.print("Elija Una Ficha: ");
					eleccion = entrada.nextInt()-1;
					jugador.elegirFicha(fichasRonda.get(eleccion));
					fichasRonda.get(eleccion).setEstado();
					fichasRonda.remove(eleccion);
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
					while(!jugador.ubicarFicha(posx, posy) && cantIntentos++ < 3){ // parche provisorio (ver logica tablero)
						System.out.println("Elija una ubicacion correcta\n");
						System.out.print("pos x: ");
						posx = entrada.nextInt();
						System.out.print("pos y: ");
						posy = entrada.nextInt();
						System.out.println(jugador.getFicha());
					}
					System.out.println();
					System.out.println(jugador.getTablero().getPuntos());
					jugador.getTablero().mostrarTablero();
					cantIntentos=0;
					System.out.println("\n\n");
				}
				turno.ordenarTurnos(jugadores);
				fichasRonda.clear();
			}
			Collections.sort(jugadores,(j1,j2)->compararPuntos(j1, j2));
			System.out.println("Posiciones finales: " + jugadores);
		}

		private int compararPuntos(Jugador j1, Jugador j2) {
			return j2.getTablero().getPuntos()-j1.getTablero().getPuntos();
		}
}
