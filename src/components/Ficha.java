package components;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

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
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import views.ViewPartida;

public class Ficha extends Pane {
	
	private int width = 140;
	private int height = 140;

	private Pane imageContainer = new Pane();
	private Rotate rotate;
	private ImageView imageView;
	private ImageView imageView2;

	private int cantRotaciones;

	private Terreno terreno1;
	private Terreno terreno2;
	
	public Ficha(int t1, int t2, int tam) {
		this.terreno1 = new Terreno(t1);
		this.terreno2 = new Terreno(t2);
		cantRotaciones = 0;
		width = height = tam;
		imageView = this.terreno1.getImageView();
		imageView2 = this.terreno2.getImageView();
		imageView.setX(0);
		imageView.setY(0);
		imageView2.setX(0);
		imageView2.setY(height);
		// setting the fit height and width of the image view
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
		imageView2.setFitHeight(height);
		imageView2.setFitWidth(width);

		rotate = new Rotate();
		rotate.setPivotX(width / 2);
		rotate.setPivotY(height);

		imageContainer.getChildren().add(imageView);
		imageContainer.getChildren().add(imageView2);
		this.enableRotate();
		getTransforms().add(rotate);
		getChildren().add(imageContainer);

		Pane source = this;

		source.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Drag was detected, start drap-and-drop gesture
				// Allow any transfer node
				// TransferMode.
				Dragboard db = source.startDragAndDrop(TransferMode.ANY);

				// Put ImageView on dragboard
				ClipboardContent cbContent = new ClipboardContent();
				cbContent.putString(terreno1.getNombre() + " " + terreno2.getNombre() + " " + cantRotaciones);
				db.setContent(cbContent);
				event.consume();
			}
		});

		source.setOnDragDone(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// the drag and drop gesture has ended
				// if the data was successfully moved, clear it
				if (event.getTransferMode() == TransferMode.MOVE) {
					source.setVisible(false);
				}
				event.consume();
			}
		});
	}

	public int getCantidadRotaciones() {
		return this.cantRotaciones;
	}
	
	public Terreno getTerrenoFicha() {
		return this.terreno1;
	}

	public Terreno getTerreno2Ficha() {
		return this.terreno2;
	}

	public Pane getImageContainer() {
		return imageContainer;
	}

	public void enableRotate() {
		this.setOnMouseClicked(event -> {
			this.rotate();
		});
	}
	
	public void rotate() {
		this.rotate.setAngle(rotate.getAngle() + 90);
		this.cantRotaciones++;
	}
}
