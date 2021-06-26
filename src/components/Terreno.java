package components;


import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import views.ViewPartida;


public class Terreno extends Label {

	private int cantCoronas = 0;
	private int nombre;
	private double tam;
	Image imagen;
	
	Terreno(int nombre, int cantCoronas,double tam){
		this.tam = tam;
		this.nombre = nombre;
		this.cantCoronas = cantCoronas;
		setTerreno(this.nombre, cantCoronas);
	}
	
	Terreno(String colorCastillo, double tam){
		this.tam = tam;
		this.imagen = new Image("/resources/"+ colorCastillo + ".png");
		ImageView view = new ImageView(this.imagen);
		view.setFitHeight(tam);
		view.setFitWidth(tam);
		setGraphic(view);
	}
	
	public void setTerreno(int terreno, int cantCoronas) {
		this.imagen = new Image(getClass().getResource("../resources/"+ terreno + "-" + cantCoronas + ".jpg").toExternalForm());
	}
	
	public Image getImage() {
		return this.imagen;
	}
	
	public ImageView getImageView() {
		ImageView img = new ImageView(imagen);
		img.setFitHeight(tam);
		img.setFitWidth(tam);
		img.setPreserveRatio(true);
		return img;
	}
	
	public String getNombre() {
		return this.nombre + "";
	}
	
	public int getCantCoronas() {
		return this.cantCoronas;
	}
}

