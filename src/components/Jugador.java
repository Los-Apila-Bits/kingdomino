package components;

import javafx.scene.control.Label;

public class Jugador {

	private TableroKD tablero;
	private Label puntosLabel;
	private int id;
	private String color;
	Ficha fichaSeleccionada;
	
	public Jugador(int id, String color) {
		this.id = id;
		this.color = color;
		this.puntosLabel = new Label("Jugador " + this.id + ": 0");
	}
	
	public void createTablero(double tam) {
		this.tablero = new TableroKD(tam, color);
	}
	
	public TableroKD getTablero() {
		return this.tablero;
	}
	
    public int getPuntos() {
		return this.tablero.getTableroLogico().getPuntos();
    }
    
    public int getId() {
    	return this.id;
    }
	
	public Label getLabelPuntos() {
		return this.puntosLabel;
	}
	
	public Ficha getFichaSeleccionada() {
		return this.fichaSeleccionada;
	}
	
	public void setFichaSeleccionada(Ficha f) {
		this.fichaSeleccionada = f;
	}

//	public void jugar() {
//		this.tablero.setDisable(false);
//		while(!this.tablero.isUpdated()) {
//			
//		}
//		this.tablero.setUpdate(true);
//	}

}
