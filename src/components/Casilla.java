package components;

import javafx.scene.control.Label;

public class Casilla extends Label {

	Terreno terreno;
	private int x, y;
	int enabled = 0;

	public Casilla(int x, int y) {
		this.y = y;
		this.x = x;
		this.terreno = new Terreno(-1);
		// EVENTOS
		//setOnMouseClicked(e->onMouseClicked());
		setCasilla(this.terreno);

	}

//	private void onMouseClicked() {
//		if(this.enabled < 2)
//			setCasilla(new Terreno(1));	
//	}

	public void setCasilla(Terreno terreno) {
		this.terreno = terreno;
		setGraphic(terreno.getImageView());
		this.enabled++;
	}

	public Terreno getTerreno() {
		return terreno;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}