package components;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import views.ViewPartida;

public class Casilla extends Label {

	private int x, y;

	public Casilla(int x, int y) {
		this.y = y;
		this.x = x;
		ImageView img = new ImageView(new Image(getClass().getResource("../resources/casillaVacia.jpg").toExternalForm()));
		img.setFitHeight(ViewPartida.TAM_CASILLA);
		img.setFitWidth(ViewPartida.TAM_CASILLA);
		img.setPreserveRatio(true);
		setGraphic(img);
	}

	public void setCasilla(Terreno terreno) {
		setGraphic(terreno.getImageView());
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}

