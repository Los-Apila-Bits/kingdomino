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

	private final int ANCHO = ViewPartida.TAM_CASILLA;
	private final int ALTO = ViewPartida.TAM_CASILLA;
	private Pane imageContainer = new Pane();
	private Rotate rotate;
	private ImageView imageView;
	private ImageView imageView2;

	private int cantRotaciones;

	private Terreno terreno1;
	private Terreno terreno2;

	public Ficha(int t1, int t2) {
		this.terreno1 = new Terreno(t1);
		this.terreno2 = new Terreno(t2);
		cantRotaciones = 0;

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

		imageContainer.getChildren().add(imageView);
		imageContainer.getChildren().add(imageView2);
		this.enableRotate();
		// this.enableDragging();
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
				source.setVisible(false);
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

	private void makeDraggable(Node node) {
		final Delta dragDelta = new Delta();

		double posIniX = node.getLayoutX();
		double posIniY = node.getLayoutY();

		node.setOnMouseEntered(me -> {
			if (!me.isPrimaryButtonDown()) {
				node.getScene().setCursor(Cursor.HAND);
			}
		});
		node.setOnMouseExited(me -> {
			if (!me.isPrimaryButtonDown()) {
				node.getScene().setCursor(Cursor.DEFAULT);

				node.setLayoutX(posIniX);
				node.setLayoutY(posIniY);
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
