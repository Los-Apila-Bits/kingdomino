package components;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static utils.Materials.*;

public class Casilla extends Label {

	private int x, y;

	public Casilla(int x, int y, double tam) {
		this.y = y;
		this.x = x;
		ImageView img = new ImageView(new Image(getClass().getResource(CASILLA).toExternalForm()));
		img.setFitHeight(tam);
		img.setFitWidth(tam);
		img.setPreserveRatio(true);
		setGraphic(img);
		setHeight(tam);
		setWidth(tam);
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

