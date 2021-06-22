package components;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.sun.javafx.geom.Rectangle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import views.ViewPartida;

public class Ficha extends Pane {

//	private final int ANCHO = 100;
//	private final int ALTO =100;

	private final int ANCHO = ViewPartida.TAM_CASILLA;
	private final int ALTO = ViewPartida.TAM_CASILLA;
	private Image image1;
	private Image image2;
	private Pane imageContainer = new Pane();
	private Rotate rotate;
	// Setting the image view
	private ImageView imageView;
	private ImageView imageView2;

	Terreno terreno1;
	Terreno terreno2;
//	public Ficha(String terreno1, String terreno2) {
//		try {
//			image1 = new Image(new FileInputStream(terreno1 + ".jpeg"));
//			image2 = new Image(new FileInputStream(terreno2 + ".jpeg"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		imageView = new ImageView(image1); 
//		imageView2 = new ImageView(image2); 
//		imageView.setX(0); 
//        imageView.setY(0); 
//        imageView2.setX(0); 
//        imageView2.setY(ALTO); 
//        //setting the fit height and width of the image view 
//        imageView.setFitHeight(ALTO); 
//        imageView.setFitWidth(ANCHO); 
//        imageView2.setFitHeight(ALTO); 
//        imageView2.setFitWidth(ANCHO);
//		imageContainer.getChildren().add(imageView);
//	    imageContainer.getChildren().add(imageView2);
//	    rotate = new Rotate();
//	    rotate.setPivotX(ANCHO/2);
//	    rotate.setPivotY(ALTO);
//	    imageContainer.getTransforms().add(rotate);
//	    this.enableDragging();
//	    this.enableRotate();
//	}

	public Ficha(int terreno1, int terreno2) {
		this.terreno1 = new Terreno(terreno1);
		this.terreno2 = new Terreno(terreno2);

		imageView = this.terreno1.getImageView();
		imageView2 = this.terreno2.getImageView();
		imageView.setX(0);
		imageView.setY(0);
		imageView2.setX(0);
		imageView2.setY(ALTO);
		// setting the fit height and width of the image view
		imageView.setFitHeight(ALTO);
		imageView.setFitWidth(ANCHO);
		imageView2.setFitHeight(ALTO);
		imageView2.setFitWidth(ANCHO);

		rotate = new Rotate();
		rotate.setPivotX(ANCHO / 2);
		rotate.setPivotY(ALTO);

//    imageView.getTransforms().add(rotate);
//    imageView2.getTransforms().add(rotate);

		imageContainer.getChildren().add(imageView);
		imageContainer.getChildren().add(imageView2);
		this.enableDragging();
		this.enableRotate();
		imageContainer.getTransforms().add(rotate);
		getChildren().add(imageContainer);
		// getTransforms().add(rotate);
		
		/////////////////
		
		
		//----------- Mueve bien la ficha-------
		//makeDraggable(imageContainer);
		//------------------------------------
		
		
		//-----------Drag and Drop Ficha---------------
		ImageView source = imageView;
		
	    source.setOnDragDetected(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent event) {
	            //Drag was detected, start drap-and-drop gesture
	            //Allow any transfer node
	            Dragboard db = source.startDragAndDrop(TransferMode.ANY);

	            //Put ImageView on dragboard
	            ClipboardContent cbContent = new ClipboardContent();
	            //cbContent.putImage(source.getImage());
	            cbContent.putImage(source.getImage());
	            //cbContent.put(DataFormat.)
	            db.setContent(cbContent);
	            source.setVisible(false);
	            event.consume();
	        }
	    });
	    
	    source.setOnDragDone(new EventHandler<DragEvent>() {
	        public void handle(DragEvent event) {
	            //the drag and drop gesture has ended
	            //if the data was successfully moved, clear it
	            if(event.getTransferMode() == TransferMode.MOVE){
	                source.setVisible(false);
	            }
	            event.consume();
	        }
	    });
		

	    //---------------------------------------------------
	}
	
	public Terreno getTerrenoFicha() {
		return this.terreno1;
	}

	public Pane getImageContainer() {
		return imageContainer;
	}

	public void enableRotate() {
		this.imageContainer.setOnMouseClicked(event -> {
			this.rotate();
		});
//        this.imageView.setOnMouseClicked( event -> {
//            this.rotate();   
//        });
		// imageView2.setRotate(90);
		// Acá iría la lógica de girar las imagenes
	}

	public void enableDragging() {
		final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
//        this.imageContainer.setOnMousePressed( event -> {mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));
//        this.rotate.setAngle(rotate.getAngle()-90);});

		this.imageContainer.setOnMousePressed(event -> {
			event.setDragDetect(true);
		});
		this.imageContainer.setOnDragOver(event -> {
			event.acceptTransferModes(TransferMode.MOVE);
			event.consume();
		});

//        this.imageContainer.setOnMousePressed( event -> {mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));});
//        this.imageContainer.setOnMouseDragged( event -> {
//            double deltaX = event.getSceneX() - mouseAnchor.get().getX();
//            double deltaY = event.getSceneY() - mouseAnchor.get().getY();
//            this.imageContainer.relocate(this.imageContainer.getLayoutX()-deltaX, this.imageContainer.getLayoutY()-deltaY);
//            mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));;
//        });
	}

	public void rotate() {
		this.rotate.setAngle(rotate.getAngle() + 90);
	}
	
    private void makeDraggable(Node node) {
        final Delta dragDelta = new Delta();

        node.setOnMouseEntered(me -> {
            if (!me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.HAND);
            }
        });
        node.setOnMouseExited(me -> {
            if (!me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        node.setOnMousePressed(me -> {
            if (me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
            dragDelta.x = me.getX();
            dragDelta.y = me.getY();
            node.getScene().setCursor(Cursor.MOVE);
        });
        node.setOnMouseReleased(me -> {
            if (!me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        node.setOnMouseDragged(me -> {
            node.setLayoutX(node.getLayoutX() + me.getX() - dragDelta.x);
            node.setLayoutY(node.getLayoutY() + me.getY() - dragDelta.y);
        });
    }

    private class Delta {
        public double x;
        public double y;
    }
}
