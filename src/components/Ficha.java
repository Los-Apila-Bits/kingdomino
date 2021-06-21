package components;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

public class Ficha {
	private final int ANCHO =100;
	private final int ALTO =100;
	private Image image1;
	private Image image2;
	private Pane imageContainer = new Pane();
	private Rotate rotate;
    //Setting the image view 
    private ImageView imageView; 
    private ImageView imageView2;
	public Ficha(String terreno1, String terreno2) {
		try {
			image1 = new Image(new FileInputStream(terreno1 + ".jpeg"));
			image2 = new Image(new FileInputStream(terreno2 + ".jpeg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imageView = new ImageView(image1); 
		imageView2 = new ImageView(image2); 
		imageView.setX(0); 
        imageView.setY(0); 
        imageView2.setX(0); 
        imageView2.setY(ALTO); 
        //setting the fit height and width of the image view 
        imageView.setFitHeight(ALTO); 
        imageView.setFitWidth(ANCHO); 
        imageView2.setFitHeight(ALTO); 
        imageView2.setFitWidth(ANCHO);
		imageContainer.getChildren().add(imageView);
	    imageContainer.getChildren().add(imageView2);
	    rotate = new Rotate();
	    rotate.setPivotX(ANCHO/2);
	    rotate.setPivotY(ALTO);
	    imageContainer.getTransforms().add(rotate);
	    this.enableDragging();
	    this.enableRotate();
	}
	
	
	public Pane getImageContainer() {
		return imageContainer;
	}

	public void enableRotate() {
        this.imageContainer.setOnMouseClicked( event -> {
            this.rotate();
        });
    }
	public void enableDragging() {
        final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
        this.imageContainer.setOnMousePressed( event -> {mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));
        this.rotate.setAngle(rotate.getAngle()-90);});
        this.imageContainer.setOnMouseDragged( event -> {
            double deltaX = event.getSceneX() - mouseAnchor.get().getX();
            double deltaY = event.getSceneY() - mouseAnchor.get().getY();
            this.imageContainer.relocate(this.imageContainer.getLayoutX()+deltaX, this.imageContainer.getLayoutY()+deltaY);
            mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));;
        });
    }
    
	
	
	public void rotate() {
		this.rotate.setAngle(rotate.getAngle()+90);
	}
}
