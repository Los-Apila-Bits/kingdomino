package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Screen;
import javafx.stage.Stage;
import components.Ficha;
import components.InfoLabel;
import components.Jugador;
import components.KDButton;
import settings.Settings;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.PlayerColor;

import static utils.Sounds.*;
import static utils.Materials.*;

public class ViewPartida {
	public static final int TAM_CASILLA = 140;
	public static final int TAM_PREV = 70;
	
	private static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

	private Settings settings;
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	private Stage menuStage;
	private HBox dockPane;
	private GridPane fichasTurno = new GridPane();
	
	public ViewPartida(Settings settings) {
		this.settings = settings;
		int cantJugadores = 2;
		for(int i =0 ; i< cantJugadores;i++) {
			jugadores.add(new Jugador(i+1, "AMARILLO"));			
		}
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
			GridPane tablero = jugador.getTablero();
			tablero.setLayoutX(tableroWidth/2 - jugadorTablero / 2);
			AnchorPane.setTopAnchor(tablero, 100d);
			AnchorPane.setBottomAnchor(tablero, 100d);
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
		
		VBox infoPartida = new VBox(backgroundHeight * 0.1);
		infoPartida.setAlignment(Pos.CENTER);
		infoPartida.setPadding(new Insets(10, 50, 10, 10));
		
		InfoLabel playerLabel = new InfoLabel("Puntaje de los Jugadores", backgroundHeight * .05, Pos.CENTER, true);
		playerLabel.setTranslateY(imageHeight * 0.05);
		GridPane pointsPane = new GridPane();
		pointsPane.setHgap(backgroundHeight * 0.5);
		pointsPane.setVgap(backgroundWeight * 0.05);
		pointsPane.setPadding(new Insets(backgroundWeight * 0.05, 0, 0, backgroundWeight * 0.05));
		int i = 0;
		int j = 0;
		for(Jugador jugador : jugadores) {
			InfoLabel pointsLabel = new InfoLabel("Jugador " + jugador.getId() + ": " + jugador.getPuntos() , backgroundHeight * .05, Pos.CENTER_LEFT, false);
			pointsLabel.setPointsColor(PlayerColor.RED);
			pointsPane.add(pointsLabel,i,j);
			if (j == 1) {
				i++;
				j = 0;
			}
			else
				j++;
		}
		
		infoPartida.getChildren().addAll(playerLabel, pointsPane);
		// Panel para rotar ficha
		StackPane previsualizacionFicha = new StackPane();
		previsualizacionFicha.setAlignment(Pos.CENTER);
		previsualizacionFicha.setStyle("-fx-background-color:#F9C112;");
		previsualizacionFicha.setMaxHeight(TAM_CASILLA * 3);
		previsualizacionFicha.setMinWidth(TAM_CASILLA * 3);

		Ficha fichaAInsertar = new Ficha(1, 2, 1, 0, TAM_CASILLA);

		previsualizacionFicha.getChildren().add(fichaAInsertar);
		previsualizacionFicha.setPadding(new Insets(TAM_CASILLA / 2, 0, 10, previsualizacionFicha.getMinWidth() / 3));

		fichasTurno.setMinHeight(300);
		fichasTurno.setAlignment(Pos.CENTER);
		fichasTurno.setVgap(15);
		fichasTurno.setHgap(25);
		fichasTurno.setPadding(new Insets(0, 0, 30, 0));

		List<String> fuentes = javafx.scene.text.Font.getFamilies();
		List<String> fuentesFantasia = javafx.scene.text.Font.getFontNames("Fantasy");
		
		
		HBox buttonPane = new HBox();
		buttonPane.setAlignment(Pos.CENTER);
		
		double buttonWidth = imageWidth * 0.35;
		
		KDButton disconnectButton = new KDButton("DESCONECTARSE", buttonWidth, "yellow");
        KDButton quitButton = new KDButton("SALIR", buttonWidth, "yellow");
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
        buttonPane.getChildren().addAll(disconnectButton, quitButton);
		anchorPaneTablero.setTop(infoPartida);
		anchorPaneTablero.setCenter(previsualizacionFicha);
		anchorPaneTablero.setCenter(fichasTurno);
		anchorPaneTablero.setBottom(buttonPane);
		dockPane.getChildren().add(anchorPaneTablero);
	}
	
	public static void actualizarPuntos() {
		jugadores.get(0).getLabelPuntos().setText("Jugador 1: " + jugadores.get(0).getTablero().getTableroLogico().getPuntos());
	}

}