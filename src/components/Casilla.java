package components;

import java.awt.event.MouseEvent;

import javafx.scene.control.Label;

public class Casilla extends Label {

	Terreno terreno;
	private int x, y;

	public Casilla(int x, int y) {
		this.y = y;
		this.x = x;
		this.terreno = new Terreno(-1);
		// EVENTOS
		setOnMouseClicked(e->onMouseClicked());
		setCasilla(this.terreno);

	}

	private void onMouseClicked() {
		System.out.println("Hola");
	}

	public void setCasilla(Terreno terreno) {
		this.terreno = terreno;
		setGraphic(terreno.getImageView());
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