package components;

import java.awt.event.ActionListener;
import java.beans.EventHandler;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

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
		for(int i = 0; i<5; i++) {
			for(int j = 0; j<5; j++) {
				int x = i;
				int y = j;
				setAlignment(Pos.CENTER);
				Casilla casilla = new Casilla(x, y);
//				casilla.addEventHandler(MouseEvent e, new EventHandler(e, getAccessibleRoleDescription(), getAccessibleRoleDescription(), getAccessibleHelp()));;
				add(casilla, x, y);
				casillas[i][j] = casilla;
			}
		}
		casillas[2][2].setCasilla(new Terreno(0));
	}
	
	
	private int getX(int index) {
		return index % 5;
	}
	
	private int getY(int index) {
		return (index - getX(index)) / 5;
	}
	
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
	
//	private class CasillaHandler implements ActionListener{
//		public void actionPerformed(ActionEvent e) {
//			Object source = e.getSource();
//			
//			for(int i = 0;i<5;i++) {
//				for(int j=0;j<5;j++) {
//					if(source ==casillas[i][j])
//					{
//						System.out.println("hola");
//						return;
//					}
//				}
//			}
//		}
//
//		@Override
//		public void actionPerformed(java.awt.event.ActionEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//	}
}

