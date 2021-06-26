package components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import utils.PlayerColor;
import views.ViewPartida;

public class Jugador {

	private TableroKD tablero;
	private InfoLabel puntosLabel;
	private int id;
	private int color;
	Ficha fichaSeleccionada;
	
	public Jugador(int id, int color) {
		this.id = id;
		this.color = color;
	}
	
	public void createTablero(double tam, ViewPartida partida) {
		this.tablero = new TableroKD(tam, color, partida);
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
    
    public void setLabelPuntos (double height) {
    	 this.puntosLabel = new InfoLabel("Jugador " + id + ": " + getTablero().getTableroLogico().getPuntos() , height, Pos.CENTER_LEFT, false);
    	 puntosLabel.setPointsColor(this.color);
    }
    
    public int getColor() {
    	return this.color;
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
