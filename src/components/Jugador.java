package components;

public class Jugador {

	TableroKD tablero;
	Ficha fichaSeleccionada;
	int id;
	
	public Jugador(int id) {
		
		this.id = id;
		this.tablero = new TableroKD();
		
	}

}
