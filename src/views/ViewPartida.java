package views;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.geom.PickRay;
import com.sun.javafx.scene.input.PickResultChooser;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Screen;
import javafx.stage.Stage;
import juego.Mazo;
import components.Ficha;
import components.Jugador;
import components.KDSubScene;
import components.Terreno;
import settings.Settings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	
	private final String FONT_PATH = "/resources/playfair_font.ttf";
	private KDSubScene winnerSubscene;

	private static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

	private Settings settings;

	private Mazo mazo;
	private GridPane fichasTurno = new GridPane();
	BorderPane root = new BorderPane();
	private int jugActual = 0;

	public ViewPartida(Settings settings) {
		this.settings = settings;
		this.width = settings.getWidth();
//		this.height = settings.getHeight();
		this.height = Screen.getPrimary().getVisualBounds().getHeight(); // Ajusta el alto a la pantalla principal, sin
		this.turnoActual = 1; // dejar contenido bajo la barra de inicio
		int cantJugadores = 2;
		jugadores.add(new Jugador(1, "AMARILLO"));
		jugadores.add(new Jugador(2, "AZUL"));
//		for (int i = 0; i < cantJugadores; i++) {
//			jugadores.add(new Jugador(i + 1, "AMARILLO"));
//		}
	}
	
	public void showWinner() {
		
		Collections.sort(jugadores, (a,b)->a.getTablero().getTableroLogico().getPuntos() - b.getTablero().getTableroLogico().getPuntos());
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		
		alert.setContentText("El podio es: \n" + "Jugador" +
		jugadores.get(1).getId() + " con (" + jugadores.get(1).getTablero().getTableroLogico().getPuntos() + ")"  + " \n" + "Jugador" 
				+ jugadores.get(0).getId() + " con(" + jugadores.get(0).getTablero().getTableroLogico().getPuntos() + ")"
				+ "\nFelicitaciones, el Jugador" + jugadores.get(1).getId() + " es el victorioso!!!");

		alert.showAndWait();
				
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

		// Panel de la derecha. Informaci�n de los jugadores, ficha a colocar y del
		// turno
		BorderPane info = new BorderPane();

		info.setMaxWidth(400);

		// Contenedor vertical con informaci�n de jugadores
		VBox infoPartida = new VBox();
		infoPartida.setPadding(new Insets(10, 50, 10, 10));

		Text infoJugadoresText = new Text("Informacion de los jugadores");
		infoJugadoresText.setFont(Font.font("Consolas", 25));
		infoPartida.getChildren().add(infoJugadoresText);
		
		for(int i = 0; i<jugadores.size(); i++) {
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
		fichasTurno.setPadding(new Insets(0, 0, 30, 0));

		try {
			mazo = new Mazo();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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

		sigJugador.setDisable(true);

		contenedorBotones.add(sigJugador, 0, 0);
		contenedorBotones.add(seleccionarFicha, 1, 0);

		seleccionarFicha.setOnMouseClicked(event -> {
			if(!jugadores.get(jugActual).getTablero().isFichaColocada()) {
				return;
			}
			cuadroTablero.setDisable(true);
			//seleccionarFicha.setDisable(true);
			fichasTurno.setDisable(false);
		});

		sigJugador.setOnMouseClicked(event -> {
			if (turnoActual > 12) {
				showWinner();
				// mostrar dialog del ganador
			}
			jugadores.get(jugActual).getTablero().setFichaColocada(false);
			previsualizacionFicha.getChildren().clear();
			sigJugador.setDisable(true);
			jugActual++;
			if (jugActual == jugadores.size()) { // Debo armar una nueva pila de fichas
				List<int[]> fichas = new ArrayList<int[]>();
				
				if(mazo.getSize() >= 4)
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
			//info.setCenter(previsualizacionFicha);
		});
		
		previsualizacionFicha.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// The drag-and-drop gesture entered the target
				// show the user that it is an actual gesture target
				if (event.getGestureSource() != previsualizacionFicha && event.getDragboard().hasString()) {
					// source.setVisible(false);
					previsualizacionFicha.setOpacity(0.7);
					//System.out.println("Drag entered");
				}
				event.consume();
			}
		});
		
		previsualizacionFicha.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// mouse moved away, remove graphical cues
				// source.setVisible(true);
				previsualizacionFicha.setOpacity(1);

				event.consume();
			}
		});
		
		// Drag over event handler is used for the receiving node to allow movement
		previsualizacionFicha.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// data is dragged over to target
				// accept it only if it is not dragged from the same node
				// and if it has image data

				if (event.getGestureSource() != previsualizacionFicha && event.getDragboard().hasString()) {
					// allow for moving
					event.acceptTransferModes(TransferMode.MOVE);
				}
				event.consume();

			}
		});
		
		previsualizacionFicha.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// Data dropped
				// If there is an image on the dragboard, read it and use it
				Dragboard db = event.getDragboard();
				boolean success = false;
				Node node = event.getPickResult().getIntersectedNode();
				if (db.hasString()) {

					Integer cIndex = GridPane.getColumnIndex(node);
					Integer rIndex = GridPane.getRowIndex(node);
					int x = cIndex == null ? 0 : cIndex;
					int y = rIndex == null ? 0 : rIndex;
					
					System.out.println(db.getString());
					
					String[] newFichaSeleccionada = new String[5];
					newFichaSeleccionada = db.getString().split(" ");

					Ficha newF = new Ficha(Integer.parseInt(newFichaSeleccionada[0]),Integer.parseInt(newFichaSeleccionada[1]), Integer.parseInt(newFichaSeleccionada[3]), Integer.parseInt(newFichaSeleccionada[4]), TAM_CASILLA);
					
					jugadores.get(jugActual).setFichaSeleccionada(newF);
					
//					PickRay pickRay = new PickRay((int) sigJugador.getLayoutX(), (int) sigJugador.getLayoutY(), 1.0, 1.0, 1.0);
//					PickResultChooser pickResultChooser = new PickResultChooser();
//					root.impl_pickNode(pickRay,  pickResultChooser);
//					Node intersectedNode = pickResultChooser.getIntersectedNode();
//					
//					event.fireEvent(sigJugador, MouseEvent.MOUSE_CLICKED);
					previsualizacionFicha.getChildren().add(newF);
					sigJugador.setDisable(false);
					fichasTurno.setDisable(true);
					success = true;
				}
				// let the source know whether the image was successfully transferred and used
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
		// jugar();

	}

//	private void jugar() {
//		try {
//			mazo = new Mazo();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		List<int[]> fichas = new ArrayList<int[]>();
//		while (!mazo.mazoVacio()) {
//			for (int i = 0; i < 4; i++) {
//				fichas.add(mazo.sacarFicha());
//			}
//			addFichas(fichas);
//			System.out.println(jugadores.size());
//			for (Jugador jugador : this.jugadores) {
//				jugador.jugar();
//			}
//			fichas.clear();
//		}
//	}

	private void addFichas(List<int[]> fichas) {
		fichas.sort((ficha1, ficha2) -> ficha1[0] - ficha2[0]);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				int[] vec = fichas.get(i + i + j);
				this.fichasTurno.add(new Ficha(vec[1], vec[2], vec[3], vec[4], TAM_PREV), j, i);
			}
		}
		fichasTurno.setGridLinesVisible(true);
	}

	public static void actualizarPuntos() {
		for(int i = 0; i<jugadores.size(); i++) {
			jugadores.get(i).getLabelPuntos()
			.setText("Jugador " + (i + 1) + ": " + jugadores.get(i).getTablero().getTableroLogico().getPuntos());
		}
	}

}
