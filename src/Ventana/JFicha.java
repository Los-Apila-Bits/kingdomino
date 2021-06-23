package Ventana;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import juego.Ficha;
import juego.Terreno;

public class JFicha extends JPanel{

	private static final long serialVersionUID = 1L;
	private Ficha ficha;
	private JTerreno[] terreno = new JTerreno[2];
	
	public JFicha() {
		setLayout(new GridLayout(1, 2, 1, 1)); 
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.ficha = new Ficha(1,new Terreno(0,0),new Terreno(1,0));
        terreno[0] = new JTerreno(new Terreno(0,0));
        terreno[1] = new JTerreno(new Terreno(1,0));
        terreno[0].setOpaque(true);
        terreno[1].setBackground(Color.WHITE);
		terreno[0].setIcon(terreno[0].icono);
		terreno[1].setIcon(terreno[1].icono);
		this.add(terreno[0]);
		this.add(terreno[1]);
	}
	
	public void setFicha(Ficha ficha) {
		
		this.ficha = ficha;
		terreno[0].setTerreno(ficha.getTerreno1());
		terreno[1].setTerreno(ficha.getTerreno2());
		terreno[0].setIcon(terreno[0].icono);
		terreno[1].setIcon(terreno[1].icono);
		add(terreno[0]);
		add(terreno[1]);
	}
	
//	public void rotarFicha() {
//		ficha.girarFicha();
//	}
	
	public int[] getDireccion() {
		return this.ficha.getDireccion();
	}
	
	public Ficha getFicha() {
		return this.ficha;
	}
}
