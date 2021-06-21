package Ventana;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class JMenu extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JMenu frame = new JMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Menu");
		setBounds(100, 100, 210, 145);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Empezar Partida");
		btnNewButton.setBounds(31, 68, 126, 23);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(50, 40, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Ingrese Cantidad Jugadores");
		lblNewLabel.setBounds(23, 11, 173, 14);
		contentPane.add(lblNewLabel);
	}
}
