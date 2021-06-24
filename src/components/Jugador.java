package components;

import javafx.scene.control.Label;

public class Jugador {

	TableroKD tablero;
	Label puntosLabel;
	Ficha fichaSeleccionada;
	int id;
	
	public Jugador(int id) {
		this.id = id;
		this.tablero = new TableroKD();
		this.puntosLabel = new Label("Jugador " + this.id + ": 0");
	}
	
	public TableroKD getTablero() {
		return this.tablero;
	}
	
//	public int getPuntos() {
//		return this.tablero.getTableroLogico().getPuntos();
//	}
	
	public Label getLabelPuntos() {
		return this.puntosLabel;
	}

}
