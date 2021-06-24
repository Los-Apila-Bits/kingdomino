package views;

import java.awt.Frame;
import java.io.IOException;

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
import juego.Tablero;
import juego.Terreno;
import juego.Mazo;
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
	
	private Ficha ficha;
	
	private GridPane fichasTurno = new GridPane();

	private Mazo mazo;
//	private Label estadoCasilla;
	private TableroKD tablero;
	private static ViewPartida instancia;
	public ViewPartida(double h, double w) {
		this.width = w;
		this.height = h;
	}

	public void start(Stage primaryStage) throws IOException {
		instancia = this;
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
		cuadroTablero.add(tablero = new TableroKD(), 0, 0, 5, 5);
		cuadroTablero.setAlignment(Pos.CENTER);
		cuadroTablero.setMinHeight(100);
		cuadroTablero.setMinWidth(700);
		cuadroTablero.setGridLinesVisible(false);

//		estadoCasilla = new Label();
//		estadoCasilla.setAlignment(Pos.BOTTOM_LEFT);
//		estadoCasilla.setPadding(new Insets(10, 0, 10, 10));
		
		//Panel de la derecha. Información de los jugadores, ficha a colocar y del turno
		BorderPane info = new BorderPane();
		
		info.setMaxWidth(400);

		//Contenedor vertical con información de jugadores
		VBox infoPartida = new VBox();
		infoPartida.setPadding(new Insets(10, 50, 10, 10));
		infoPartida.getChildren().add(new Label("Información de los Jugadores"));
		infoPartida.getChildren().add(new Label("Jugador 1: 34 puntos"));
		infoPartida.getChildren().add(new Label("Jugador 2: 20 puntos"));
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
		
		
		
		fichasTurno.setMinHeight(300);

//		ColumnConstraints columnas = new ColumnConstraints();
//		columnas.setPercentWidth(33);
//		fichasTurno.getColumnConstraints().add(columnas);
		fichasTurno.setAlignment(Pos.CENTER);
		fichasTurno.setVgap(15);
		fichasTurno.setHgap(25);
		fichasTurno.setPadding(new Insets(0, 0, 30, 0));
		
		info.setTop(infoPartida);
		info.setCenter(previsualizacionFicha);
		info.setBottom(fichasTurno);
		
		root.setTop(mb);
		root.setCenter(cuadroTablero);
		root.setRight(info);
		Scene scene = new Scene(root, width, height);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		while(!mazo.mazoVacio()) {
			//aca tendria que ir la logica donde sacamos las fichas y hacemos el curso de la partida
		}
		
	}
	
//	public static void main(String[] args) {
//		launch(args);
//	}

	public static TableroKD getTablero() {
		return instancia.tablero;
	}
	
	public void setFichas(int terreno1, int terreno2, int val1 , int val2) {
		this.fichasTurno.add(new Ficha(terreno1,terreno2, TAM_PREV), val1, val2);
	}

	
}
