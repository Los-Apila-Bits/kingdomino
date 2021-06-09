package juego;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class Sala {
	private int idSala;
	private static int cantSalas=0;
	private String contrasenia;
	private List<Jugador> jugadores = new LinkedList<Jugador>();
	private int cantJugadores=1;
	
	public Sala(String contrasenia) {
		idSala = cantSalas++;
		this.contrasenia = contrasenia;
	}
	public void iniciarPartida() throws FileNotFoundException {
		Partida partida = new Partida(jugadores);
		partida.ejecutar();
	}
	public void agregarJugador(Jugador jugador) {
		if(cantJugadores < 4)
			jugadores.add(jugador);
		else
			System.out.println("Sala llena");
	}
	
	public void unirseSala(Jugador jugador, String constrasenia) {
		if(this.contrasenia.equals(constrasenia) && cantJugadores < 4)
			jugadores.add(jugador);
		else
			System.out.println("Contraseña incorrecta / Sala llena");
	}
	
	public void eliminarJugador(int jugador) {
		jugadores.remove(jugador);
	}
	
	public int getIdSala() {
		return this.idSala;
	}

}
