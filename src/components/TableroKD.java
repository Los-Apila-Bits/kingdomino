package components;

import java.awt.event.ActionListener;
//import java.beans.EventHandler;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import juego.Tablero;
import components.Terreno;
import views.ViewPartida;
import components.Ficha;

public class TableroKD extends GridPane {

	private Tablero tableroLogico;
	private Casilla[][] casillas = new Casilla[5][5];
	private int turnoActual = 1;

	public TableroKD() {
		
		this.tableroLogico = new Tablero();

		setMinHeight(700);
		setMinWidth(700);
		ColumnConstraints columnas = new ColumnConstraints();
		columnas.setPercentWidth(20);
		RowConstraints filas = new RowConstraints();
		filas.setPercentHeight(20);
		getColumnConstraints().add(columnas);
		getRowConstraints().add(filas);
		getColumnConstraints().add(columnas);
		getRowConstraints().add(filas);
		getColumnConstraints().add(columnas);
		getRowConstraints().add(filas);
		getColumnConstraints().add(columnas);
		getRowConstraints().add(filas);
		getColumnConstraints().add(columnas);
		getRowConstraints().add(filas);
		setAlignment(Pos.CENTER);
		setGridLinesVisible(true);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				int x = i;
				int y = j;
				setAlignment(Pos.CENTER);
				Casilla casilla = new Casilla(x, y);
				add(casilla, x, y);
				casillas[i][j] = casilla;
			}
		}
		casillas[2][2].setCasilla(new Terreno(0));

		GridPane target = this;


		// Drag entered changes the appearance of the receiving node to indicate to the
		// player that they can place there
		target.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// The drag-and-drop gesture entered the target
				// show the user that it is an actual gesture target
				if (event.getGestureSource() != target && event.getDragboard().hasString()) {
					// source.setVisible(false);
					target.setOpacity(0.7);
					System.out.println("Drag entered");
				}
				event.consume();
			}
		});

		// Drag exited reverts the appearance of the receiving node when the mouse is
		// outside of the node
		target.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// mouse moved away, remove graphical cues
				// source.setVisible(true);
				target.setOpacity(1);

				event.consume();
			}
		});

		// Drag dropped draws the image to the receiving node
		target.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// Data dropped
				// If there is an image on the dragboard, read it and use it
				Dragboard db = event.getDragboard();
				boolean success = false;
				Node node = event.getPickResult().getIntersectedNode();
				if (node != target && db.hasString()) {

					Integer cIndex = GridPane.getColumnIndex(node);
					Integer rIndex = GridPane.getRowIndex(node);
					int x = cIndex == null ? 0 : cIndex;
					int y = rIndex == null ? 0 : rIndex;

					String[] newFicha = new String[3];
					newFicha = db.getString().split(" ");
					
					int cantRotaciones = Integer.parseInt(newFicha[2]);
					int pos = cantRotaciones % 4;
					
					juego.Ficha fichaLogica = new juego.Ficha(1, new juego.Terreno(Integer.parseInt(newFicha[0]),0), new juego.Terreno(Integer.parseInt(newFicha[1]),1));
					
					
					//----------Inserta en el tablero visual-------------------
					Terreno terreno1 = new Terreno(Integer.parseInt(newFicha[0]));
					Terreno terreno2 = new Terreno(Integer.parseInt(newFicha[1]));
					// TODO: set image size; use correct column/row span
					switch (pos) {
					case 0:
						fichaLogica.setDireccion(1,0);
						if(tableroLogico.insertarFicha(fichaLogica, y, x)) {
							casillas[x][y].setCasilla(terreno1);
							casillas[x][y + 1].setCasilla(terreno2);
							success = true;
						}
						break;
					case 1:
						fichaLogica.setDireccion(0, -1);
						if(tableroLogico.insertarFicha(fichaLogica, y, x)) {	
							casillas[x][y].setCasilla(terreno1);
							casillas[x - 1][y].setCasilla(terreno2);
							success = true;
						}
						break;
					case 2:
						fichaLogica.setDireccion(-1, 0);
						if(tableroLogico.insertarFicha(fichaLogica, y, x)) {	
						casillas[x][y].setCasilla(terreno1);
						casillas[x][y - 1].setCasilla(terreno2);
						success = true;
						}
						break;
					case 3:
						fichaLogica.setDireccion(0, 1);
						if(tableroLogico.insertarFicha(fichaLogica, y, x)) {	
						casillas[x][y].setCasilla(terreno1);
						casillas[x + 1][y].setCasilla(terreno2);
						success = true;
						}
						break;
					default:
						break;
					}
					
					tableroLogico.mostrarTablero();

					//success = true;
				}

				// let the source know whether the image was successfully transferred and used
				event.setDropCompleted(success);
				event.consume();
			}
		});

		// Drag over event handler is used for the receiving node to allow movement
		target.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// data is dragged over to target
				// accept it only if it is not dragged from the same node
				// and if it has image data

				if (event.getGestureSource() != target && event.getDragboard().hasString()) {
					// allow for moving
					event.acceptTransferModes(TransferMode.MOVE);
				}
				event.consume();

			}
		});


	}
			
//	private int getX(int index) {
//		return index % 5;
//	}
//	
//	private int getY(int index) {
//		return (index - getX(index)) / 5;
//	}

//	public Casilla getCasilla(int x, int y) {
//		return x < 0 || x > 4 || y < 0 || y > 4 ? null : casillas[y * 5 + x];
//	}
//	
//	public void setTerreno(Terreno terreno) {
//		getCasilla(terreno.getX(), terreno.getY()).setCasilla(terreno);
//	}

	public int obtenerTurnoActual() {
		return this.turnoActual;
	}

	public void setTurnoActual(int turnoActual) {
		this.turnoActual = turnoActual;
	}

}
