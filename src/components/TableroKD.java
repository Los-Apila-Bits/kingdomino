package components;

import java.awt.event.ActionListener;
//import java.beans.EventHandler;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import views.ViewPartida;

public class TableroKD extends GridPane{
	
	private Casilla[][] casillas = new Casilla[5][5];
	private int turnoActual = 1;
	
	public TableroKD() {
	
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
		
		//------------- Click en Tablero ------------------
		setOnMouseClicked( event -> onMouseClicked(event));
		
		//------------ Codigo Drag and Drop en ----------------
		final GridPane target = this;
		//Drag over event handler is used for the receiving node to allow movement
	    target.setOnDragOver(new EventHandler<DragEvent>() {
	        public void handle(DragEvent event) {
	            //data is dragged over to target
	            //accept it only if it is not dragged from the same node
	            //and if it has image data
	            if(event.getGestureSource() != target && event.getDragboard().hasImage()){
	                //allow for moving
	                event.acceptTransferModes(TransferMode.MOVE);
	            }
	            event.consume();
	        }
	    });

	    //Drag entered changes the appearance of the receiving node to indicate to the player that they can place there
	    target.setOnDragEntered(new EventHandler<DragEvent>() {
	        public void handle(DragEvent event) {
	            //The drag-and-drop gesture entered the target
	            //show the user that it is an actual gesture target
	            if(event.getGestureSource() != target && event.getDragboard().hasImage()){
	                //source.setVisible(false);
	                target.setOpacity(0.7);
	                System.out.println("Drag entered");
	            }
	            event.consume();
	        }
	    });

	    //Drag exited reverts the appearance of the receiving node when the mouse is outside of the node
	    target.setOnDragExited(new EventHandler<DragEvent>() {
	        public void handle(DragEvent event) {
	            //mouse moved away, remove graphical cues
	            //source.setVisible(true);
	            target.setOpacity(1);

	            event.consume();
	        }
	    });

	    //Drag dropped draws the image to the receiving node
	    target.setOnDragDropped(new EventHandler<DragEvent>() {
	    	public void handle(DragEvent event) {
	    	    //Data dropped
	    	    //If there is an image on the dragboard, read it and use it
	    	    Dragboard db = event.getDragboard();
	    	    boolean success = false;
	    	    Node node = event.getPickResult().getIntersectedNode();
	    	    if(node != target && db.hasImage()){

	    	        Integer cIndex = GridPane.getColumnIndex(node);
	    	        Integer rIndex = GridPane.getRowIndex(node);
	    	        int x = cIndex == null ? 0 : cIndex;
	    	        int y = rIndex == null ? 0 : rIndex;
	    	        //target.setText(db.getImage()); --- must be changed to target.add(source, col, row)
	    	        //target.add(source, 5, 5, 1, 1);
	    	        //Places at 0,0 - will need to take coordinates once that is implemented
	    	        
	    	        ImageView image = new ImageView(db.getImage());

	    	        image.setFitHeight(ViewPartida.TAM_CASILLA);
	    	        image.setFitWidth(ViewPartida.TAM_CASILLA);
	    	        // TODO: set image size; use correct column/row span
	    	        target.add(image, x, y);
	    	        success = true;
	    	    }
	    	    //let the source know whether the image was successfully transferred and used
	    	    event.setDropCompleted(success);

	    	    event.consume();
	    	}
	    });
	  //------------------------------------------------------------
		
		for(int i = 0; i<5; i++) {
			for(int j = 0; j<5; j++) {
				int x = i;
				int y = j;
				setAlignment(Pos.CENTER);
				Casilla casilla = new Casilla(x, y);
				add(casilla, x, y);
				casillas[i][j] = casilla;
			}
		}
		casillas[2][2].setCasilla(new Terreno(0));
	}
	
	//------------Funcion de click en tablero------------
	private void onMouseClicked(Event event) {
		Node node = (Node) event.getTarget();
        int row = GridPane.getRowIndex(node);
        int column = GridPane.getColumnIndex(node);
        casillas[column][row].setCasilla(new Terreno(2));
        System.out.println("Fila" + row + "Columna" + column );
	}
    //----------------------------------------------------
	
	
//	private int getX(int index) {
//		return index % 5;
//	}
//	
//	private int getY(int index) {
//		return (index - getX(index)) / 5;
//	}
	
//	public Casilla getCasilla(int x, int y) {
//		return x < 0 || x > 4 || y < 0 || y > 4 ? null : casillas[y * 5 + x];
//	}
//	
//	public void setTerreno(Terreno terreno) {
//		getCasilla(terreno.getX(), terreno.getY()).setCasilla(terreno);
//	}
	
	public int obtenerTurnoActual() {
		return this.turnoActual;
	}
	
	public void setTurnoActual(int turnoActual) {
		this.turnoActual = turnoActual;
	}
	

}

