package components;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import juego.Tablero;
import views.ViewPartida;


public class TableroKD extends GridPane {
	
	private static final int TAM_TABLERO = 5;

	private Tablero tableroLogico;
	private Casilla[][] casillas = new Casilla[TAM_TABLERO][TAM_TABLERO];
	private int turnoActual = 1;

	public TableroKD(double tam, String colorCastillo) {
		double tamCasilla = tam / TAM_TABLERO;
		setGridLinesVisible(false);
		setMaxHeight(tam);
		setMinHeight(tam);
		setMaxWidth(tam);
		setMinWidth(tam);
		this.tableroLogico = new Tablero();
		ColumnConstraints columnas = new ColumnConstraints(tamCasilla);
		RowConstraints filas = new RowConstraints(tamCasilla);
		for (int i = 0; i<=TAM_TABLERO - 1; i++) {	
			getColumnConstraints().add(columnas);
			getRowConstraints().add(filas);
		}
		setAlignment(Pos.CENTER);
		setGridLinesVisible(true);
				
		for (int i = 0; i < TAM_TABLERO; i++) {
			for (int j = 0; j < TAM_TABLERO; j++) {
				int x = i;
				int y = j;
				setAlignment(Pos.CENTER);
				Casilla casilla = new Casilla(x, y, tamCasilla);
				add(casilla, x, y);
				casillas[i][j] = casilla;
			}
		}
		
		casillas[2][2].setCasilla(new Terreno(colorCastillo, tamCasilla));

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
					//System.out.println("Drag entered");
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
					
					juego.Ficha fichaLogica = new juego.Ficha(1, new juego.Terreno(Integer.parseInt(newFicha[0]),Integer.parseInt(newFicha[3])), new juego.Terreno(Integer.parseInt(newFicha[1]),Integer.parseInt(newFicha[4])));
					
					
					//----------Inserta en el tablero visual-------------------
					Terreno terreno1 = new Terreno(Integer.parseInt(newFicha[0]),Integer.parseInt(newFicha[3]));
					Terreno terreno2 = new Terreno(Integer.parseInt(newFicha[1]),Integer.parseInt(newFicha[4]));
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
					
					if(success) {
						ViewPartida.actualizarPuntos();
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
	
	public Tablero getTableroLogico() {
		return this.tableroLogico;
	}

	public int obtenerTurnoActual() {
		return this.turnoActual;
	}

	public void setTurnoActual(int turnoActual) {
		this.turnoActual = turnoActual;
	}

}
