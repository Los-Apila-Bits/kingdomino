package views;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import components.KDSubScene;
import components.TableroKD;
import settings.Settings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utils.PlayerColor;

import static utils.Sounds.*;
import static utils.Materials.*;

public class ViewPartida {
	public static final int TAM_PREV = 70;
	private int turnoActual;

	private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
	private  Map<Integer, InfoLabel> jugadoresLabel = new HashMap<>();

	private Settings settings;
	private AnchorPane gamePane;
	private KDSubScene winnerSubscene;
	private final String FONT_PATH = "/resources/playfair_font.ttf";
	private Scene gameScene;
	private Stage gameStage;
	private Stage menuStage;
	private HBox dockPane;
	private GridPane fichasTurno = new GridPane();
	private Mazo mazo;
	BorderPane root = new BorderPane();
	private int jugActual = 0;
	private ScrollPane scroller;

	public ViewPartida(Settings settings) {
		this.settings = settings;
		this.turnoActual = 1;
		jugadores.add(new Jugador(1, PlayerColor.RED));
		jugadores.add(new Jugador(2, PlayerColor.GREEN));
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
		gamePane.getChildren().add(dockPane);
		gameStage.show();
	}

	public Text createWinnersText(String name, double panelWidth, double panelHeight, GaussianBlur g,
			double heightMultiplier) {

		Text winnersText = new Text();
		winnersText.setText(name);
		winnersText.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 26));
		double winnersTextWidth = winnersText.getLayoutBounds().getWidth();
		winnersText.setX(panelWidth * 0.5 - winnersTextWidth / 2);

		winnersText.setY(panelHeight * heightMultiplier);
		winnersText.setFill(Color.YELLOW);
		winnersText.setEffect(g);

		return winnersText;
	}

	public void showWinner() {

		Collections.sort(jugadores, (a, b) -> a.getTablero().getTableroLogico().getPuntos()
				- b.getTablero().getTableroLogico().getPuntos());
		
		winnerSubscene = new KDSubScene(0.45);
		gamePane.getChildren().add(winnerSubscene);
		VBox vboxWinner = new VBox (10);
		vboxWinner.setPrefHeight(winnerSubscene.getHeight());
		vboxWinner.setPrefWidth(winnerSubscene.getWidth());
		vboxWinner.setAlignment(Pos.TOP_CENTER);
		vboxWinner.setPadding(new Insets(winnerSubscene.getHeight() * 0.1, 0, 0, 0));
		double panelWidth = winnerSubscene.getWidth();
		double panelHeight = winnerSubscene.getHeight();

		Text winnersTitle = new Text();
		GaussianBlur g = new GaussianBlur();
		g.setRadius(1);
		winnersTitle.setText("FIN DE LA PARTIDA");
		winnersTitle.setY(panelHeight * 0.12);
		winnersTitle.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 40));
		double textWidth = winnersTitle.getLayoutBounds().getWidth();
		winnersTitle.setX(panelWidth / 2 - textWidth / 2);
		winnersTitle.setFill(Color.YELLOW);
		winnersTitle.setEffect(g);
		
		vboxWinner.getChildren().add(winnersTitle);
		
		for (Jugador jugador : jugadores) {
			Text textPlayer = createWinnersText(
					"El jugador" + jugador.getId() + " finalizo con ("
						+ jugador.getTablero().getTableroLogico().getPuntos() + ") puntos!",
					panelWidth, panelHeight, g, 0.60);
			vboxWinner.getChildren().add(textPlayer);
		}

		winnerSubscene.getPane().getChildren().addAll(vboxWinner);
		winnerSubscene.moveSubScene();

	}

	public void createTablero() {
		VBox content = new VBox(5);
		content.setAlignment(Pos.CENTER);
		scroller = new ScrollPane(content);
		Image backgroundImage = new Image(GAME_PANEL_1);
		double imageWidth = backgroundImage.getWidth();
		double imageHeight = backgroundImage.getHeight();
		double backgroundHeight = settings.getHeight();
		double backgroundWeight = backgroundHeight * imageWidth / imageHeight;
		BackgroundImage background = new BackgroundImage(
				new Image(GAME_PANEL_1, backgroundWeight, backgroundHeight, false, false), BackgroundRepeat.ROUND,
				BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, null);
		content.setBackground(new Background(background));
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		content.setPrefWidth(backgroundWeight);
		content.setPrefHeight(backgroundHeight);
		backgroundImage = new Image(BOARD_MAP);
		imageWidth = backgroundImage.getWidth();
		imageHeight = backgroundImage.getHeight();
		double tableroWidth = backgroundWeight * 0.85;
		double tableroHeight = tableroWidth * imageHeight / imageWidth;
		BackgroundImage tableroBackground = new BackgroundImage(
				new Image(BOARD_MAP, tableroWidth, tableroHeight, false, false), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
		for (Jugador jugador : jugadores) {
			System.out.println(jugActual);
			InfoLabel playerLabel = new InfoLabel("Jugador " + jugador.getId() + (jugActual + 1 == jugador.getId() ? " (JUGANDO)" : " (ESPERANDO)"), backgroundHeight * .05, Pos.CENTER_LEFT,
					true);
			playerLabel.setPlayerColor(jugador.getColor());
			playerLabel.setTranslateY(backgroundHeight * 0.1);
			AnchorPane anchorPaneTablero = new AnchorPane();
			anchorPaneTablero.setMaxHeight(backgroundHeight);
			anchorPaneTablero.setMaxWidth(tableroWidth);
			anchorPaneTablero.setMinWidth(tableroWidth);
			anchorPaneTablero.setMinHeight(backgroundHeight);
			anchorPaneTablero.setBackground(new Background(tableroBackground));
			double jugadorTablero = tableroHeight * 0.75;
			jugador.createTablero(jugadorTablero, this);
			TableroKD tablero = jugador.getTablero();
			tablero.setLayoutX(tableroWidth / 2 - jugadorTablero / 2);
			AnchorPane.setTopAnchor(tablero, 100d);
			AnchorPane.setBottomAnchor(tablero, 100d);
			tablero.setDisable(true);
			anchorPaneTablero.getChildren().add(tablero);
			jugadoresLabel.put(jugador.getId(), playerLabel);
			content.getChildren().addAll(playerLabel, anchorPaneTablero);
		}
		// cuadroTablero.add(tablero = new TableroKD(), 0, 0, 5, 5);

		dockPane.getChildren().add(scroller);
	}

	public void createInfoPanel() {
		BorderPane anchorPaneTablero = new BorderPane();
		Image backgroundImage = new Image(GAME_PANEL_2);
		double imageWidth = backgroundImage.getWidth();
		double imageHeight = backgroundImage.getHeight();
		double backgroundHeight = settings.getHeight();
		double backgroundWeight = backgroundHeight * imageWidth / imageHeight;
		BackgroundImage background = new BackgroundImage(
				new Image(GAME_PANEL_2, backgroundWeight, backgroundHeight, false, false), BackgroundRepeat.REPEAT,
				BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
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
		for (Jugador jugador : jugadores) {
			jugador.setLabelPuntos(backgroundHeight * 0.05);
			pointsPane.add(jugador.getLabelPuntos(), ix, jx);
			if (jx == 1) {
				ix++;
				jx = 0;
			} else
				jx++;
		}

		infoPartida.getChildren().addAll(playerLabel, pointsPane);

		double tam_ficha = jugadores.get(jugActual).getTablero().getTamFicha();

		VBox contenedorFichas = new VBox();
		// Panel para rotar ficha
		backgroundImage = new Image(FICHA_PAPER);
		imageWidth = backgroundImage.getWidth();
		imageHeight = backgroundImage.getHeight();
		double contenedorWidth = backgroundWeight * 0.78;
		double contenedorHeight = contenedorWidth * imageHeight / imageWidth;
		background = new BackgroundImage(new Image(FICHA_PAPER, contenedorWidth, contenedorHeight, false, false),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
		StackPane previsualizacionFicha = new StackPane();
		previsualizacionFicha.setBackground(new Background(background));
		previsualizacionFicha.setAlignment(Pos.CENTER);
		previsualizacionFicha.setMaxHeight(contenedorHeight);
		previsualizacionFicha.setMinHeight(contenedorHeight);
		previsualizacionFicha.setMinWidth(contenedorWidth);
		previsualizacionFicha.setPadding(new Insets(previsualizacionFicha.getMinHeight() / 2 - tam_ficha, 0 ,0, previsualizacionFicha.getMinWidth() /2 + tam_ficha/4));
		
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
		
		fichasTurno.setDisable(true);

		// Botones de salida

		HBox buttonPane = new HBox();
		buttonPane.setAlignment(Pos.CENTER);

		double buttonWidth = imageWidth * 0.25;
		
		KDButton borrarFicha = new KDButton("BORRAR FICHA", buttonWidth, "violet", 14);
		borrarFicha.setClickSound(CONFIRM_SOUND);
		borrarFicha.setHoverSound(HOVER_SOUND);
		borrarFicha.setTranslateY(- imageHeight * 0.05);
		
		KDButton seleccionarFicha = new KDButton("SELECT FICHA", buttonWidth, "violet", 14);
		seleccionarFicha.setClickSound(CONFIRM_SOUND);
		seleccionarFicha.setHoverSound(HOVER_SOUND);
		seleccionarFicha.setTranslateY(-imageHeight * 0.05);

		KDButton sigJugador = new KDButton("SIG JUGADOR", buttonWidth, "violet", 14);
		sigJugador.setClickSound(CONFIRM_SOUND);
		sigJugador.setHoverSound(HOVER_SOUND);
		sigJugador.setTranslateY(-imageHeight * 0.05);

		sigJugador.setDisable(true);
		
		borrarFicha.setOnDragDropped((new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// Data dropped
				// If there is an image on the dragboard, read it and use it
				Dragboard db = event.getDragboard();
				boolean success = false;
				Node node = event.getPickResult().getIntersectedNode();
				if (db.hasString()) {
					success = true;
					sigJugador.setDisable(false);
					jugadores.get(jugActual).getTablero().setFichaColocada(true);
					
				}
				// let the source know whether the image was successfully transferred and used
				event.setDropCompleted(success);
				event.consume();
			}

		}));
		
		borrarFicha.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// The drag-and-drop gesture entered the target
				// show the user that it is an actual gesture target
				if (event.getGestureSource() != borrarFicha && event.getDragboard().hasString()) {
					// source.setVisible(false);
					borrarFicha.setOpacity(0.7);
					//System.out.println("Drag entered");
				}
				event.consume();
			}
		});
		
		borrarFicha.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// mouse moved away, remove graphical cues
				// source.setVisible(true);
				borrarFicha.setOpacity(1);

				event.consume();
			}
		});
		
		// Drag over event handler is used for the receiving node to allow movement
		borrarFicha.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// data is dragged over to target
				// accept it only if it is not dragged from the same node
				// and if it has image data

				if (event.getGestureSource() != borrarFicha && event.getDragboard().hasString()) {
					// allow for moving
					event.acceptTransferModes(TransferMode.MOVE);
				}
				event.consume();

			}
		});

		seleccionarFicha.setOnMouseClicked(event -> {
			TableroKD jugActualTablero = jugadores.get(jugActual).getTablero();
			if (!jugActualTablero.isFichaColocada()) {
				return;
			}
			jugActualTablero.setDisable(true);
			borrarFicha.setDisable(true);
			seleccionarFicha.setDisable(true);
			fichasTurno.setDisable(false);
		});

		sigJugador.setOnMouseClicked(event -> {
			if (turnoActual > 12) {
				showWinner();
				// mostrar dialog del ganador
			}
			jugadores.get(jugActual).getTablero().setFichaColocada(false);
			previsualizacionFicha.getChildren().clear();
			borrarFicha.setDisable(false);
			seleccionarFicha.setDisable(false);
			sigJugador.setDisable(true);
			jugadoresLabel.get(jugadores.get(jugActual).getId()).setText("Jugador " + jugadores.get(jugActual).getId() + " (ESPERANDO)");
			jugActual++;
			if (jugActual == jugadores.size()) { // Debo armar una nueva pila de fichas
				List<int[]> fichas = new ArrayList<int[]>();
				
				if(mazo.getSize() >= 4) {
					for (int i = 0; i < 4; i++) {
						fichas.add(mazo.sacarFicha());
					}			
				}
				else {
					showWinner();
				}
				fichasTurno.getChildren().clear();
				addFichas(fichas);
				jugActual = 0;
				turnoActual++;
			}
			scroller.setVvalue(jugActual);
			jugadoresLabel.get(jugadores.get(jugActual).getId()).setText("Jugador " + jugadores.get(jugActual).getId() + " (JUGANDO)");
			// cuadroTablero.getChildren().clear();
			jugadores.get(jugActual).getTablero().setDisable(false);
			// cuadroTablero.add(jugadores.get(jugActual).getTablero(), 0, 0, 5, 5);
			if (jugadores.get(jugActual).getFichaSeleccionada() != null) {
				previsualizacionFicha.getChildren().add(jugadores.get(jugActual).getFichaSeleccionada());
			}
			// info.setCenter(previsualizacionFicha);
		});
		

		
		KDButton disconnectButton = new KDButton("DESCONECTARSE", buttonWidth, "red", 14);
		KDButton quitButton = new KDButton("SALIR", buttonWidth, "red", 14);
		disconnectButton.setClickSound(CONFIRM_SOUND);
		disconnectButton.setHoverSound(HOVER_SOUND);
		disconnectButton.setTranslateY(-imageHeight * 0.05);
		disconnectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				gameStage.close();
				menuStage.show();
				settings.applySettings(menuStage, THEME);
			}
		});
		quitButton.setClickSound(CONFIRM_SOUND);
		quitButton.setHoverSound(HOVER_SOUND);
		quitButton.setTranslateY(-imageHeight * 0.05);
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				menuStage.close();
				gameStage.close();
			}
		});
		
		buttonPane.getChildren().addAll(seleccionarFicha, sigJugador, borrarFicha, disconnectButton, quitButton);
		
		previsualizacionFicha.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// The drag-and-drop gesture entered the target
				// show the user that it is an actual gesture target
				if (event.getGestureSource() != previsualizacionFicha && event.getDragboard().hasString()) {
					// source.setVisible(false);
					previsualizacionFicha.setOpacity(0.7);
					// System.out.println("Drag entered");
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

					Ficha newF = new Ficha(Integer.parseInt(newFichaSeleccionada[0]),
							Integer.parseInt(newFichaSeleccionada[1]), Integer.parseInt(newFichaSeleccionada[3]),
							Integer.parseInt(newFichaSeleccionada[4]), tam_ficha);

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

	public void actualizarPuntos() {
		for(int i = 0; i<jugadores.size(); i++) {
			Jugador jugador = jugadores.get(i);
			jugador.getLabelPuntos()
					.setText("Jugador " + jugador.getId() + ": " + jugador.getTablero().getTableroLogico().getPuntos());
		}
	}

}
