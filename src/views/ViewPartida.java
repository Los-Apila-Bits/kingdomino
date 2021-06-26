package views;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.event.ActionEvent;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import juego.Mazo;
import components.Ficha;
import components.InfoLabel;
import components.Jugador;
import components.KDButton;
import components.TableroKD;
import settings.Settings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import components.KDSubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.PlayerColor;

import static utils.Sounds.*;
import static utils.Materials.*;

public class ViewPartida {
	public static final int TAM_PREV = 70;
	private int turnoActual;

	private static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

	private Settings settings;
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private Stage menuStage;
	private HBox dockPane;
	private GridPane fichasTurno = new GridPane();
	private Mazo mazo;
	BorderPane root = new BorderPane();
	private int jugActual = 0;

	public ViewPartida(Settings settings) {
		this.settings = settings;
		this.turnoActual = 1;
		jugadores.add(new Jugador(1, "AMARILLO"));
		jugadores.add(new Jugador(2, "AZUL"));
		// int cantJugadores = jugadores.size();
		initializeStage();
	}
	
	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, settings.getWidth(), settings.getHeight());
		gameStage = new Stage();
		gameStage.setScene(gameScene);
		gameStage.setOnCloseRequest(x -> {
			x.consume();
			// if(ConfirmExit.askConfirmation()) {
			// Platform.exit();
			gameStage.close();
			menuStage.show();
			// }
		});
		settings.applySettings(gameStage, INGAME_THEME);
	}
	
	public void createNewGame(Stage menuStage) {
		dockPane = new HBox(-1);
		dockPane.setAlignment(Pos.CENTER_LEFT);
		this.menuStage = menuStage;
		this.menuStage.hide();
		createTablero();
		createInfoPanel();
		gamePane.getChildren().addAll(dockPane);
		gameStage.show();
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
	
	public void createTablero() {
		VBox content = new VBox(5);
		content.setAlignment(Pos.CENTER);
        ScrollPane scroller = new ScrollPane(content);
        Image backgroundImage = new Image(GAME_PANEL_1);
        double imageWidth = backgroundImage.getWidth();
		double imageHeight = backgroundImage.getHeight();
		double backgroundHeight = settings.getHeight();
		double backgroundWeight = backgroundHeight * imageWidth / imageHeight;
		BackgroundImage background = new BackgroundImage(new Image(GAME_PANEL_1, backgroundWeight, backgroundHeight, false, false), BackgroundRepeat.ROUND, BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, null);
		content.setBackground(new Background(background));
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		content.setPrefWidth(backgroundWeight);
		content.setPrefHeight(backgroundHeight);
		backgroundImage = new Image(BOARD_MAP);
		imageWidth = backgroundImage.getWidth();
		imageHeight = backgroundImage.getHeight();
		double tableroWidth = backgroundWeight * 0.85;
		double tableroHeight = tableroWidth * imageHeight / imageWidth;
		BackgroundImage tableroBackground = new BackgroundImage(new Image(BOARD_MAP, tableroWidth, tableroHeight, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
		for(Jugador jugador : jugadores) {
			InfoLabel playerLabel = new InfoLabel("Jugador " + jugador.getId(), backgroundHeight * .05, Pos.CENTER_LEFT, true);
			playerLabel.setPlayerColor(PlayerColor.RED);
			playerLabel.setTranslateY(backgroundHeight * 0.1);
			AnchorPane anchorPaneTablero = new AnchorPane();
			anchorPaneTablero.setMaxHeight(backgroundHeight);
			anchorPaneTablero.setMaxWidth(tableroWidth);
			anchorPaneTablero.setMinWidth(tableroWidth);
			anchorPaneTablero.setMinHeight(backgroundHeight);
			anchorPaneTablero.setBackground(new Background(tableroBackground));
			double jugadorTablero = tableroHeight * 0.75;
			jugador.createTablero(jugadorTablero);
			TableroKD tablero = jugador.getTablero();
			tablero.setLayoutX(tableroWidth/2 - jugadorTablero / 2);
			AnchorPane.setTopAnchor(tablero, 100d);
			AnchorPane.setBottomAnchor(tablero, 100d);
			tablero.setDisable(true);
			anchorPaneTablero.getChildren().add(tablero);
			content.getChildren().addAll(playerLabel, anchorPaneTablero);
		}
		//cuadroTablero.add(tablero = new TableroKD(), 0, 0, 5, 5);

		dockPane.getChildren().add(scroller);
	}
	
	public void createInfoPanel() {
		BorderPane anchorPaneTablero = new BorderPane();
		Image backgroundImage = new Image(GAME_PANEL_2);
        double imageWidth = backgroundImage.getWidth();
		double imageHeight = backgroundImage.getHeight();
		double backgroundHeight = settings.getHeight();
		double backgroundWeight = backgroundHeight * imageWidth / imageHeight;
		BackgroundImage background = new BackgroundImage(new Image(GAME_PANEL_2, backgroundWeight, backgroundHeight, false, false), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		anchorPaneTablero.setBackground(new Background(background));
		anchorPaneTablero.setPrefWidth(backgroundWeight);
		anchorPaneTablero.setPrefHeight(backgroundHeight);
		
		VBox infoPartida = new VBox(backgroundHeight * 0.01);
		infoPartida.setAlignment(Pos.CENTER);
		infoPartida.setPadding(new Insets(10, 50, 10, 10));
		
		InfoLabel playerLabel = new InfoLabel("Puntaje de los Jugadores", backgroundHeight * .05, Pos.CENTER, true);
		playerLabel.setTranslateY(imageHeight * 0.05);
		GridPane pointsPane = new GridPane();
		pointsPane.setHgap(backgroundHeight * 0.5);
		pointsPane.setVgap(backgroundWeight * 0.05);
		pointsPane.setPadding(new Insets(backgroundWeight * 0.1, 0, 0, backgroundWeight * 0.1));
		int ix = 0;
		int jx = 0;
		for(Jugador jugador : jugadores) {
			InfoLabel pointsLabel = new InfoLabel("Jugador " + jugador.getId() + ": " + jugador.getPuntos() , backgroundHeight * .05, Pos.CENTER_LEFT, false);
			pointsLabel.setPointsColor(PlayerColor.RED);
			pointsPane.add(pointsLabel,ix,jx);
			if (jx == 1) {
				ix++;
				jx = 0;
			}
			else
				jx++;
		}
		
		infoPartida.getChildren().addAll(playerLabel, pointsPane);
		
		double tam_ficha = jugadores.get(jugActual).getTablero().getTamFicha();
		
		VBox contenedorFichas = new VBox();
		// Panel para rotar ficha
		backgroundImage = new Image(FICHA_PAPER);
        imageWidth = backgroundImage.getWidth();
		imageHeight = backgroundImage.getHeight();
		double contenedorWidth = imageWidth;
		double contenedorHeight = contenedorWidth * imageHeight / imageWidth;
		background = new BackgroundImage(new Image(FICHA_PAPER, contenedorWidth, contenedorHeight, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
		StackPane previsualizacionFicha = new StackPane();
		previsualizacionFicha.setBackground(new Background(background));
		previsualizacionFicha.setAlignment(Pos.CENTER);
		previsualizacionFicha.setMaxHeight(contenedorHeight);
		previsualizacionFicha.setMinHeight(contenedorHeight);
		previsualizacionFicha.setMinWidth(contenedorWidth);
		previsualizacionFicha.setPadding(new Insets(tam_ficha / 2, 0, 10, previsualizacionFicha.getMinWidth() / 3));
		
		fichasTurno.setMinHeight(300);
		fichasTurno.setAlignment(Pos.CENTER);
		fichasTurno.setVgap(15);
		fichasTurno.setHgap(25);
		fichasTurno.setPadding(new Insets(0, 0, 30, 0));
		
		dockPane.getChildren().add(anchorPaneTablero);
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

		// Botones de salida
		
		HBox buttonPane = new HBox();
		buttonPane.setAlignment(Pos.CENTER);
				
		double buttonWidth = imageWidth * 0.25;
		
		KDButton seleccionarFicha = new KDButton("SELECT FICHA", buttonWidth, "violet", 14);
		seleccionarFicha.setClickSound(CONFIRM_SOUND);
		seleccionarFicha.setHoverSound(HOVER_SOUND);
		seleccionarFicha.setTranslateY(- imageHeight * 0.05);
		
		KDButton sigJugador = new KDButton("SIG JUGADOR", buttonWidth, "violet", 14);
		sigJugador.setClickSound(CONFIRM_SOUND);
		sigJugador.setHoverSound(HOVER_SOUND);
		sigJugador.setTranslateY(- imageHeight * 0.05);

		sigJugador.setDisable(true);

		seleccionarFicha.setOnMouseClicked(event -> {
			TableroKD jugActualTablero = jugadores.get(jugActual).getTablero();
			if(!jugActualTablero.isFichaColocada()) {
				return;
			}
			jugActualTablero.setDisable(true);
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
			//cuadroTablero.getChildren().clear();
			jugadores.get(jugActual).getTablero().setDisable(false);
			//cuadroTablero.add(jugadores.get(jugActual).getTablero(), 0, 0, 5, 5);
			if (jugadores.get(jugActual).getFichaSeleccionada() != null) {
				previsualizacionFicha.getChildren().add(jugadores.get(jugActual).getFichaSeleccionada());
			}
			//info.setCenter(previsualizacionFicha);
		});
		
		KDButton disconnectButton = new KDButton("DESCONECTARSE", buttonWidth, "red", 14);
		KDButton quitButton = new KDButton("SALIR", buttonWidth, "red", 14);
		disconnectButton.setClickSound(CONFIRM_SOUND);
		disconnectButton.setHoverSound(HOVER_SOUND);
		disconnectButton.setTranslateY(- imageHeight * 0.05);
		disconnectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				gameStage.close();
				menuStage.show();
			}
		});
		quitButton.setClickSound(CONFIRM_SOUND);
		quitButton.setHoverSound(HOVER_SOUND);
		quitButton.setTranslateY(- imageHeight * 0.05);
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				menuStage.close();
				gameStage.close();
			}
		});
		
		buttonPane.getChildren().addAll(seleccionarFicha, sigJugador, disconnectButton, quitButton);
		
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

					Ficha newF = new Ficha(Integer.parseInt(newFichaSeleccionada[0]),Integer.parseInt(newFichaSeleccionada[1]), Integer.parseInt(newFichaSeleccionada[3]), Integer.parseInt(newFichaSeleccionada[4]), tam_ficha);
					
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

		contenedorFichas.getChildren().addAll(previsualizacionFicha, fichasTurno);
		anchorPaneTablero.setTop(infoPartida);
		anchorPaneTablero.setCenter(contenedorFichas);
		anchorPaneTablero.setBottom(buttonPane);
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
