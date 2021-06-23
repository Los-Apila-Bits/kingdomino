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
	
	private Ficha ficha;

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
		cuadroTablero.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
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
//		previsualizacionFicha.setPadding(new Insets(30, 0, 10, 50));
//		Rectangle contenedorFicha = new Rectangle(TAM_CASILLA * 2, 150);
//		contenedorFicha.setFill(Color.BISQUE);
//		Label textoFichaEjemplo = new Label("TERRENO TERRENO");
//		textoFichaEjemplo.setMinSize(TAM_CASILLA*2, TAM_CASILLA);
//		previsualizacionFicha.getChildren().addAll(contenedorFicha, textoFichaEjemplo);
//		infoPartida.getChildren().add(previsualizacionFicha);
		
		previsualizacionFicha.setMinHeight(TAM_CASILLA*3);
		previsualizacionFicha.setMinWidth(TAM_CASILLA*3);
		previsualizacionFicha.getChildren().add(new Ficha(1,2));
		
		ficha = new Ficha(1,2);
		this.tablero.addFicha(ficha);
		
		GridPane fichasTurno = new GridPane();

		ColumnConstraints columnas = new ColumnConstraints();
		columnas.setPercentWidth(50);
		fichasTurno.getColumnConstraints().add(columnas);
		fichasTurno.setAlignment(Pos.CENTER);
		fichasTurno.setVgap(15);
		fichasTurno.setPadding(new Insets(0, 0, 30, 0));
		fichasTurno.add(new Label("Ficha 1"), 0, 0);
		fichasTurno.add(new Label("Ficha 2"), 0, 1);
		fichasTurno.add(new Label("Ficha 3"), 0, 2);
		fichasTurno.add(new Label("Ficha 4"), 1, 0);
		fichasTurno.add(new Label("Ficha 5"), 1, 1);
		fichasTurno.add(new Label("Ficha 6"), 1, 2);
		
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
	
//	public static void main(String[] args) {
//		launch(args);
//	}

	public static TableroKD getTablero() {
		return instancia.tablero;
	}

}