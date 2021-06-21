package Ventana;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;

public class JJugador extends JFrame {
	public static final int CELL_WIDTH = 50;
	public static final int SIDE = 5;
	private JPanel contentPane;
	private JTablero tablero;
	private JFicha ficha;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JJugador frame = new JJugador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JJugador() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 858, 548);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		tablero = new JTablero();
		tablero.setBounds(10, 104, 489, 394);
		contentPane.add(tablero);
		ficha = new JFicha();
		ficha.setLocation(545, 336);
		ficha.setSize(180, 80);
		contentPane.add(ficha);
		tablero.setFicha(ficha.getFicha());
		
	}
}
