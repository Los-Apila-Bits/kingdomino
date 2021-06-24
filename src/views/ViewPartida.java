package views;

import java.awt.Frame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import components.TableroKD;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import components.Ficha;
import components.Jugador;
import juego.Tablero;
import juego.Terreno;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ViewPartida{
	private double width = 1024;
	private double height = 768;
	public static final int TAM_CASILLA = 140;
	public static final int TAM_PREV = 70;
	
	private static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

	
	public ViewPartida(double h, double w) {
		this.width = w;
		this.height = h;
		int cantJugadores = 2;
		for(int i =0 ; i< cantJugadores;i++) {
			jugadores.add(new Jugador(i+1));			
		}
	}

	public void start(Stage primaryStage) throws IOException {
//		instancia = this;
		primaryStage.setTitle("KingDomino");

		BorderPane root = new BorderPane(); // Contenedor principal de la vista
		root.setPrefSize(width, height);
		MenuBar mb = new MenuBar(); // Barra de opciones
		Menu jugar = new Menu("Jugar");
		MenuItem nuevaSala = new MenuItem("Crear nueva sala");
		MenuItem salir = new MenuItem("Salir al escritorio");
		salir.setOnAction(e -> primaryStage.close());
		jugar.getItems().addAll(nuevaSala, salir);
		mb.getMenus().addAll(jugar);

		GridPane cuadroTablero = new GridPane(); // Tablero de kingdomino
		cuadroTablero.setStyle("-fx-background-color:#FFFF7A;");
		//cuadroTablero.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		//cuadroTablero.add(tablero = new TableroKD(), 0, 0, 5, 5);
		cuadroTablero.add(jugadores.get(0).getTablero(), 0, 0, 5, 5);
		cuadroTablero.setAlignment(Pos.CENTER);
		cuadroTablero.setMinHeight(100);
		cuadroTablero.setMinWidth(700);
		cuadroTablero.setGridLinesVisible(false);
		
		//Panel de la derecha. Información de los jugadores, ficha a colocar y del turno
		BorderPane info = new BorderPane();
		
		info.setMaxWidth(400);

		//Contenedor vertical con información de jugadores
		VBox infoPartida = new VBox();
		infoPartida.setPadding(new Insets(10, 50, 10, 10));
		infoPartida.getChildren().add(new Label("Información de los Jugadores"));
		infoPartida.getChildren().add(jugadores.get(0).getLabelPuntos());
		//Panel para rotar ficha
		StackPane previsualizacionFicha = new StackPane();
		previsualizacionFicha.setAlignment(Pos.CENTER);
		previsualizacionFicha.setStyle("-fx-background-color:#F9C112;");
		//previsualizacionFicha.setBackground(new Background(new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY)));
		previsualizacionFicha.setMaxHeight(TAM_CASILLA*3);
		previsualizacionFicha.setMinWidth(TAM_CASILLA*3);
		Ficha fichaAInsertar = new Ficha(1,2, TAM_CASILLA);
		previsualizacionFicha.getChildren().add(fichaAInsertar);
		previsualizacionFicha.setPadding(new Insets(TAM_CASILLA/2, 0, 10, previsualizacionFicha.getMinWidth()/3));
		
		GridPane fichasTurno = new GridPane();
		
		fichasTurno.setMinHeight(300);
		fichasTurno.setAlignment(Pos.CENTER);
		fichasTurno.setVgap(15);
		fichasTurno.setHgap(25);
		fichasTurno.setPadding(new Insets(0, 0, 30, 0));
		
		
		
		fichasTurno.add(new Ficha(3,4, TAM_PREV), 0, 0);
		fichasTurno.add(new Ficha(2,4, TAM_PREV), 0, 1);
		fichasTurno.add(new Ficha(3,5, TAM_PREV), 1, 0);
		fichasTurno.add(new Ficha(3,2, TAM_PREV), 1, 1);
		fichasTurno.add(new Ficha(2,2, TAM_PREV), 2, 0);
		fichasTurno.add(new Ficha(1,2, TAM_PREV), 2, 1);
		
		
		
		fichasTurno.setDisable(true);
		//fichasTurno.getChildren();
		
		info.setTop(infoPartida);
		info.setCenter(previsualizacionFicha);
		info.setBottom(fichasTurno);
		
		root.setTop(mb);
		root.setCenter(cuadroTablero);
		root.setRight(info);
		Scene scene = new Scene(root, width, height);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void actualizarPuntos() {
		jugadores.get(0).getLabelPuntos().setText("Jugador 1: " + jugadores.get(0).getTablero().getTableroLogico().getPuntos());
	}

}