package views;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import juego.Mazo;
import components.Ficha;
import components.Jugador;
import settings.Settings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static utils.Sounds.*;

public class ViewPartida {
	private double width = 1024;
	private double height = 768;
	public static final int TAM_CASILLA = 140;
	public static final int TAM_PREV = 70;
	private static final int TURNOS_TOTALES = 12;
	private int turnoActual;

	private static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

	private Settings settings;

	private Mazo mazo;
	private GridPane fichasTurno = new GridPane();
	BorderPane root = new BorderPane();
	private int jugActual = 0;

	public ViewPartida(Settings settings) {
		this.settings = settings;
		this.width = settings.getWidth();
		this.height = Screen.getPrimary().getVisualBounds().getHeight(); // Ajusta el alto a la pantalla principal, sin
		this.turnoActual = 1; // dejar contenido bajo la barra de inicio
		int cantJugadores = 2;
		jugadores.add(new Jugador(1, "AMARILLO"));
		jugadores.add(new Jugador(2, "AZUL"));
	}

	public void start(Stage primaryStage) throws IOException {
		primaryStage.setTitle("KingDomino");
		primaryStage.setMaximized(true);
		// BorderPane root = new BorderPane(); // Contenedor principal de la vista
		root.setPrefSize(width, height);
		MenuBar mb = new MenuBar(); // Barra de opciones
		Menu jugar = new Menu("Jugar");
		MenuItem nuevaSala = new MenuItem("Crear nueva sala");
		MenuItem salir = new MenuItem("Salir al escritorio");
		salir.setOnAction(e -> primaryStage.close());
		jugar.getItems().addAll(nuevaSala, salir);
		mb.getMenus().addAll(jugar);

		settings.applySettings(primaryStage, INGAME_THEME);

		GridPane cuadroTablero = new GridPane(); // Tablero de kingdomino
		cuadroTablero.setStyle("-fx-background-color:#FFFF7A;");
		// cuadroTablero.add(tablero = new TableroKD(), 0, 0, 5, 5);
		cuadroTablero.add(jugadores.get(0).getTablero(), 0, 0, 5, 5);
		cuadroTablero.setAlignment(Pos.CENTER);
		cuadroTablero.setMinHeight(100);
		cuadroTablero.setMinWidth(700);
		cuadroTablero.setGridLinesVisible(false);
		cuadroTablero.setDisable(true);

		// Panel de la derecha. Informaciï¿½n de los jugadores, ficha a colocar y del
		// turno
		BorderPane info = new BorderPane();

		info.setMaxWidth(400);

		// Contenedor vertical con informaciï¿½n de jugadores
		VBox infoPartida = new VBox();
		infoPartida.setPadding(new Insets(10, 50, 10, 10));

		Text infoJugadoresText = new Text("Informacion de los jugadores");
		infoJugadoresText.setFont(Font.font("Consolas", 25));
		infoPartida.getChildren().add(infoJugadoresText);

		for (int i = 0; i < jugadores.size(); i++) {
			infoPartida.getChildren().add(jugadores.get(i).getLabelPuntos());
		}

		VBox contenedorFichas = new VBox();

		// Panel para rotar ficha

		StackPane previsualizacionFicha = new StackPane();
		previsualizacionFicha.setAlignment(Pos.CENTER);
		previsualizacionFicha.setStyle("-fx-background-color:#F9C112;");
		previsualizacionFicha.setMaxHeight(TAM_CASILLA * 3);
		previsualizacionFicha.setMinHeight(TAM_CASILLA * 3);
		previsualizacionFicha.setMinWidth(TAM_CASILLA * 3);
		previsualizacionFicha.setPadding(new Insets(TAM_CASILLA / 2, 0, 10, previsualizacionFicha.getMinWidth() / 3));

		fichasTurno.setMinHeight(300);
		fichasTurno.setAlignment(Pos.CENTER);
		fichasTurno.setVgap(15);
		fichasTurno.setHgap(25);
		fichasTurno.setPadding(new Insets(20, 0, 30, 0));
		fichasTurno.setGridLinesVisible(false);
		try {
			mazo = new Mazo();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		List<int[]> fichas1 = new ArrayList<int[]>();
		for (int i = 0; i < 4; i++) {
			fichas1.add(mazo.sacarFicha());
		}
		addFichas(fichas1);

		Button sigJugador = new Button("Siguiente jugador");
		Button seleccionarFicha = new Button("Seleccionar Ficha");
		GridPane contenedorBotones = new GridPane();
		contenedorBotones.setPadding(new Insets(0, 70, 80, 70));

		sigJugador.setDisable(true);
		sigJugador.setVisible(false);
		contenedorBotones.add(sigJugador, 0, 0);
		contenedorBotones.add(seleccionarFicha, 1, 0);

		seleccionarFicha.setOnMouseClicked(event -> {
			if (!jugadores.get(jugActual).getTablero().isFichaColocada()) {
				Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Acción no permitida");
                alert.setHeaderText(null);
                alert.setContentText("No colocó la ficha!");
                alert.showAndWait();
				return;
			}
			cuadroTablero.setDisable(true);
			seleccionarFicha.setDisable(true);
			fichasTurno.setDisable(false);
		});

		sigJugador.setOnMouseClicked(event -> {
			if (turnoActual > TURNOS_TOTALES) {
				// mostrar dialog del ganador
			}
			jugadores.get(jugActual).getTablero().setFichaColocada(false);
			previsualizacionFicha.getChildren().clear();
			sigJugador.setDisable(true);
			jugActual++;
			if (jugActual == jugadores.size()) { // Debo armar una nueva pila de fichas
				List<int[]> fichas = new ArrayList<int[]>();
				for (int i = 0; i < 4; i++) {
					fichas.add(mazo.sacarFicha());
				}
				fichasTurno.getChildren().clear();
				addFichas(fichas);
				jugActual = 0;
				turnoActual++;
			}
			cuadroTablero.getChildren().clear();
			cuadroTablero.setDisable(false);
			cuadroTablero.add(jugadores.get(jugActual).getTablero(), 0, 0, 5, 5);
			if (jugadores.get(jugActual).getFichaSeleccionada() != null) {
				previsualizacionFicha.getChildren().add(jugadores.get(jugActual).getFichaSeleccionada());
			}

		});

		previsualizacionFicha.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != previsualizacionFicha && event.getDragboard().hasString()) {
					previsualizacionFicha.setOpacity(0.7);
				}
				event.consume();
			}
		});

		previsualizacionFicha.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				previsualizacionFicha.setOpacity(1);

				event.consume();
			}
		});

		previsualizacionFicha.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getGestureSource() != previsualizacionFicha && event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.MOVE);
				}
				event.consume();

			}
		});

		previsualizacionFicha.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				Node node = event.getPickResult().getIntersectedNode();
				if (event.getTarget() == event.getSource() && db.hasString()) { //????
					Integer cIndex = GridPane.getColumnIndex(node);
					Integer rIndex = GridPane.getRowIndex(node);
					int x = cIndex == null ? 0 : cIndex;
					int y = rIndex == null ? 0 : rIndex;

					String[] newFichaSeleccionada = new String[5];
					newFichaSeleccionada = db.getString().split(" ");

					Ficha newF = new Ficha(Integer.parseInt(newFichaSeleccionada[0]),
							Integer.parseInt(newFichaSeleccionada[1]), Integer.parseInt(newFichaSeleccionada[3]),
							Integer.parseInt(newFichaSeleccionada[4]), TAM_CASILLA);

					jugadores.get(jugActual).setFichaSeleccionada(newF);

					previsualizacionFicha.getChildren().add(newF);
					Event.fireEvent(sigJugador, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0,
							MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null));

					sigJugador.setDisable(false);
					fichasTurno.setDisable(true);
					seleccionarFicha.setDisable(false);
					success = true;
				}
				event.setDropCompleted(success);
				event.consume();
			}

		});

		contenedorFichas.getChildren().add(previsualizacionFicha);
		contenedorFichas.getChildren().add(fichasTurno);
		info.setTop(infoPartida);
		info.setCenter(contenedorFichas);
		info.setBottom(contenedorBotones);

		root.setTop(mb);
		root.setCenter(cuadroTablero);
		root.setRight(info);

		Scene scene = new Scene(root, width, height);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void addFichas(List<int[]> fichas) {
		fichas.sort((ficha1, ficha2) -> ficha1[0] - ficha2[0]);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				int[] vec = fichas.get(i + i + j);
				this.fichasTurno.add(new Ficha(vec[1], vec[2], vec[3], vec[4], TAM_PREV), j, i);
			}
		}
	}

	public static void actualizarPuntos() {
		for (int i = 0; i < jugadores.size(); i++) {
			jugadores.get(i).getLabelPuntos().setText(
					"Jugador " + (i + 1) + ": " + jugadores.get(i).getTablero().getTableroLogico().getPuntos());
		}
	}

}
