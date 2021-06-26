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

	private Tablero tableroLogico;
	private Casilla[][] casillas = new Casilla[5][5];
	private int turnoActual = 1;
	private boolean isFichaColocada = true;

	public TableroKD(String colorCastillo) {

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
		casillas[2][2].setCasilla(new Terreno(colorCastillo));

		GridPane target = this;

		target.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != target && event.getDragboard().hasString()) {
					target.setOpacity(0.7);
				}
				event.consume();
			}
		});
		target.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				target.setOpacity(1);
				event.consume();
			}
		});

		target.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				Node node = event.getPickResult().getIntersectedNode();
				if (node != target && db.hasString()) {

					Integer cIndex = GridPane.getColumnIndex(node);
					Integer rIndex = GridPane.getRowIndex(node);
					int x = cIndex == null ? 0 : cIndex;
					int y = rIndex == null ? 0 : rIndex;

					// Si se rompe pasar a 3
					String[] newFicha = new String[5];
					newFicha = db.getString().split(" ");

					int cantRotaciones = Integer.parseInt(newFicha[2]);
					int pos = cantRotaciones % 4;

					juego.Ficha fichaLogica = new juego.Ficha(1,
							new juego.Terreno(Integer.parseInt(newFicha[0]), Integer.parseInt(newFicha[3])),
							new juego.Terreno(Integer.parseInt(newFicha[1]), Integer.parseInt(newFicha[4])));

					// ----------Inserta en el tablero visual-------------------
					Terreno terreno1 = new Terreno(Integer.parseInt(newFicha[0]), Integer.parseInt(newFicha[3]));
					Terreno terreno2 = new Terreno(Integer.parseInt(newFicha[1]), Integer.parseInt(newFicha[4]));
					switch (pos) {
					case 0:
						fichaLogica.setDireccion(1, 0);
						if (tableroLogico.insertarFicha(fichaLogica, y, x)) {
							casillas[x][y].setCasilla(terreno1);
							casillas[x][y + 1].setCasilla(terreno2);
							success = true;
						}
						break;
					case 1:
						fichaLogica.setDireccion(0, -1);
						if (tableroLogico.insertarFicha(fichaLogica, y, x)) {
							casillas[x][y].setCasilla(terreno1);
							casillas[x - 1][y].setCasilla(terreno2);
							success = true;
						}
						break;
					case 2:
						fichaLogica.setDireccion(-1, 0);
						if (tableroLogico.insertarFicha(fichaLogica, y, x)) {
							casillas[x][y].setCasilla(terreno1);
							casillas[x][y - 1].setCasilla(terreno2);
							success = true;
						}
						break;
					case 3:
						fichaLogica.setDireccion(0, 1);
						if (tableroLogico.insertarFicha(fichaLogica, y, x)) {
							casillas[x][y].setCasilla(terreno1);
							casillas[x + 1][y].setCasilla(terreno2);
							success = true;
						}
						break;
					default:
						break;
					}
					if (success) {
						ViewPartida.actualizarPuntos();
						setFichaColocada(true);
					}
				}
				event.setDropCompleted(success);
				event.consume();
			}

		});

		// Drag over event handler is used for the receiving node to allow movement
		target.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != target && event.getDragboard().hasString()) {
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
//
//	public boolean isUpdated() {
//		return updated;
//	}
//	public void setUpdate(boolean valor) {
//			this.updated = valor;
//	}

	public boolean isFichaColocada() {
		return isFichaColocada;
	}

	public void setFichaColocada(boolean isFichaColocada) {
		this.isFichaColocada = isFichaColocada;
	}
}
