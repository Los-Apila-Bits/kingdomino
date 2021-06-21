package Ventana;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import juego.Terreno;

public class JTerreno extends JLabel {

	private static final long serialVersionUID = 1L;
	public Terreno tipo;// = new Terreno(0,0);
	public final int i;
	public final int j;
	ImageIcon icono;
	public JTerreno(Icon icono, int i, int j) {	
		super(icono);
		tipo = new Terreno(0,0);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.i = i;
		this.j = j;
	}
	public JTerreno(Terreno terreno) {
		super(new ImageIcon());
		this.i=0;
		this.j=0;
		this.tipo = terreno;
		BufferedImage img = null;
		try {
			icono = new ImageIcon(ImageIO.read(new File("Graficos-chino/"+tipo.getTipo()+""+tipo.getCantCoronas()+".jpg")));
			setIcon(icono);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setTerreno(Terreno tipo) {
		this.tipo = tipo;
	}
}
