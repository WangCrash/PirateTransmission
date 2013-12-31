import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JColorChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;


public class ColorTestings implements ChangeListener{

	private JFrame frame;
	private JColorChooser colorChooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ColorTestings window = new ColorTestings();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ColorTestings() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 698, 401);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		colorChooser = new JColorChooser();
		colorChooser.getSelectionModel().addChangeListener(this);
		frame.getContentPane().add(colorChooser, BorderLayout.CENTER);
	}
	
	public void stateChanged(ChangeEvent e){
		Color newColor = colorChooser.getColor();
		System.out.println(newColor);
		System.out.println(newColor.getAlpha());
	}

}
