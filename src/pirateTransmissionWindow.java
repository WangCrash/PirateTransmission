
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Managers.PirateBayBot;
import Model.ArchivoTorrent;


public class pirateTransmissionWindow implements Runnable{
	private static JPanel listContent; 
	public void run() {
        // Invoked on the event dispatching thread.
        // Construct and show GUI.
		JFrame mainFrame = new JFrame("PirateTransmission v0.1");
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField busqueda = new JTextField("", 20);
		busqueda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JTextField textField = (JTextField)arg0.getSource();
				ArchivoTorrent[] torrents;
				try {
					torrents = PirateBayBot.searchTorrent(textField.getText().trim(), PirateBayBot.CATEGORY_ALL, PirateBayBot.ORDERBY_SEEDERS);
				} catch (Exception e) {
					torrents = new ArchivoTorrent[0];
				}
				drawTorrentsList(torrents);
			}
		});
		listContent = new JPanel(false);
		
		mainFrame.getContentPane().add(new JLabel("Buscar:"), BorderLayout.WEST);
		mainFrame.getContentPane().add(busqueda, BorderLayout.EAST);
		
		mainFrame.pack();
		
		mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new pirateTransmissionWindow());
    }
    
    private static void drawTorrentsList(ArchivoTorrent[] torrents){
    	for(int i = 0; i < torrents.length; i++){
    	}
    }
}
