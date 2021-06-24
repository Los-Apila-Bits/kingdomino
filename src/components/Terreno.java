package components;


import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import views.ViewPartida;


public class Terreno extends Label {
	int x, y = -1;
	private int nombre;
	Image imagen;
	
	Terreno(int nombre){
		this.nombre = nombre;
		setTerreno(this.nombre);
	}
	
	Terreno(String colorCastillo){
		this.imagen = new Image(getClass().getResource("../resources/"+ colorCastillo + ".jpg").toExternalForm());
	}
	
	public void setTerreno(int terreno) {
		switch (terreno) {
		case -1: //Casilla con terreno vacio
			imagen = new Image(getClass().getResource("../resources/"+ terreno + ".jpg").toExternalForm());
			break;
		case 0: //Casilla con castillo
			imagen = new Image(getClass().getResource("../resources/"+ terreno + "-AMARILLO" + ".png").toExternalForm());
			break;
		case 1: //Casilla con campo
			imagen = new Image(getClass().getResource("../resources/"+ terreno + ".jpg").toExternalForm());
			break;
		case 2: //Casilla con bosque
			imagen = new Image(getClass().getResource("../resources/"+ terreno + ".jpg").toExternalForm());
			break;
		case 3: //Casilla con pradera / desierto
			imagen = new Image(getClass().getResource("../resources/"+ terreno + ".jpg").toExternalForm());
			break;
		case 4: //Casilla con río
			imagen = new Image(getClass().getResource("../resources/"+ terreno + ".jpg").toExternalForm());
			break;
		case 5: //Casilla con pantano
			imagen = new Image(getClass().getResource("../resources/"+ terreno + ".jpg").toExternalForm());
			break;
		case 6: //Casilla con mina
			imagen = new Image(getClass().getResource("../resources/"+ terreno + ".jpg").toExternalForm());
			break;
		default:
			break;
		}

	}
	
	public Image getImage() {
		return this.imagen;
	}
	
	public ImageView getImageView() {
		ImageView img = new ImageView(imagen);
		img.setFitHeight(ViewPartida.TAM_CASILLA);
		img.setFitWidth(ViewPartida.TAM_CASILLA);
		img.setPreserveRatio(true);
		return img;
	}
	
	public String getNombre() {
		return this.nombre + "";
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}

