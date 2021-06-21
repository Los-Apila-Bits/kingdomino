package Ventana;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import juego.Ficha;


@SuppressWarnings("serial")
public class JTablero extends JPanel {
    public static final int CELL_WIDTH = 50;
    public static final int SIDE = 5;
    private JTerreno[][] grid = new JTerreno[SIDE][SIDE];
    private Icon emptyIcon;
    private Icon colorIcon;
    public Ficha ficha;
    public JTablero() {
        setBackground(Color.BLACK);
        emptyIcon = createIcon(new Color(0, 0, 0, 0));
        colorIcon = createIcon(Color.RED);
        setLayout(new GridLayout(SIDE, SIDE, 1, 1)); 
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
        		try {
        			JTerreno label = (JTerreno) e.getSource();
        			label.setIcon(new ImageIcon(ImageIO.read(new File("Graficos-chino/"+ficha.getTerreno1().getTipo()+""+ficha.getTerreno1().getCantCoronas()+".jpg"))));  // selected JLabel holds disk
        			grid[label.i][label.j+1].setIcon(new ImageIcon(ImageIO.read(new File("Graficos-chino/"+ficha.getTerreno2().getTipo()+""+ficha.getTerreno2().getCantCoronas()+".jpg"))));
        			//grid[label.i][label.j+1].setEnabled(false);
        			//label.setEnabled(false);
        		} catch (IOException ex) {
        			ex.printStackTrace();
        		}
            }
        };

        // iterate through the grid 2D array, creating JLabels and adding
        // blank icon as well as a MouseListener
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new JTerreno(emptyIcon,i,j); // blank icon
                grid[i][j].setOpaque(true);
                grid[i][j].setBackground(Color.WHITE);
                add(grid[i][j]);
                grid[i][j].addMouseListener(mouseListener);
            }
        }
    }
    // code to create blank icon or disk icon of color of choice
    private Icon createIcon(Color color) {
        BufferedImage img = new BufferedImage(CELL_WIDTH, CELL_WIDTH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        int gap = 2;
        g2.fillOval(gap, gap, CELL_WIDTH - 2 * gap, CELL_WIDTH - 2 * gap);
        g2.dispose();
        return new ImageIcon(img);
    }
    
    public void setFicha(Ficha ficha) {
    	this.ficha = ficha;
    }
}