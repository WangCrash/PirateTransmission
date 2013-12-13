import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Managers.PirateBayBot;
import Managers.TransmissionManager;
import Model.ArchivoTorrent;
import Utils.UtilTools;


public class UserGUI implements Runnable {
	private JFrame mainFrame; 
	private JPanel listContent;
	private JDialog alert;
	private ActionListener buttonsAction;
	private ArchivoTorrent[] torrents;
	
	public UserGUI(){
		// Invoked on the event dispatching thread.
        // Construct and show GUI.
		alert = new JDialog(mainFrame, "Error", true);
		if(!TransmissionManager.initManager()){
			JLabel messageLabel = new JLabel("No se ha podido conectar con Transmission");
			JButton alertButton = new JButton("Ok");
			alertButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.exit(1);
				}
			});
			alert.add(messageLabel);
			alert.add(alertButton);
			alert.setVisible(true);
			alert.pack();
			System.out.println("External error: couldn't authenticate on Transmission.");
			return;
		}
		
		mainFrame = new JFrame("PirateTransmission v0.1");
		mainFrame.setPreferredSize(new Dimension(400, 370));
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField busquedaTextField = new JTextField("", 30);
		busquedaTextField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JTextField textField = (JTextField)arg0.getSource();
				try {
					torrents = PirateBayBot.searchTorrent(textField.getText().trim(), PirateBayBot.CATEGORY_ALL, PirateBayBot.ORDERBY_SEEDERS);
				} catch (Exception e) {
					torrents = new ArchivoTorrent[0];
				}
				if(torrents.length > 0){
					drawTorrentsList();
				}else{
					textField.setText("No hay resultados");
				}
			}
		});
		listContent = new JPanel();
		listContent.setPreferredSize(new Dimension(390, 300));
		
		JScrollPane scroll = new JScrollPane(listContent);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(390, 300));
		
		buttonsAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton)e.getSource();
				Object tag = button.getClientProperty("tag");
				System.out.println(tag);
				int selectedIndex;
				if(tag instanceof Integer){
					selectedIndex = (Integer)tag;
					System.out.println(selectedIndex);
				}else{
					return;
				}
				ArchivoTorrent torrent = torrents[selectedIndex];
				if(e.getActionCommand().equals("ver_detalles")){
					new UtilTools().openURLInNavigator(torrent.getDetailsURL());
				}else if(e.getActionCommand().equals("añadir_torrent")){
					if(TransmissionManager.addTorrent(torrent)){
						System.out.println("TORRENT AÑADIDO");
					}else{
						System.out.println("ERROR: NO SE HA PODIDO AÑADIR EL TORRENT");
					}
				}
			}
		};
		JLabel buscarLabel = new JLabel("Buscar:");
		buscarLabel.setPreferredSize(new Dimension(50, 5));
		mainFrame.getContentPane().add(buscarLabel, BorderLayout.WEST);
		mainFrame.getContentPane().add(busquedaTextField, BorderLayout.EAST);
		mainFrame.getContentPane().add(scroll, BorderLayout.SOUTH);
		
		mainFrame.pack();
		
		mainFrame.setVisible(true);
	}

	private void drawTorrentsList(){
		if(listContent.getComponentCount() > 0){
			listContent.removeAll();
		}
		for(int i = 0; i < torrents.length; i++){
			JLabel tituloLabel = new JLabel(torrents[i].getTitulo());
			JLabel categoriaLabel = new JLabel(torrents[i].getCategoria());
	   		JButton verDetallesButton = new JButton("Ver Detalles");
			verDetallesButton.setActionCommand("ver_detalles");
	   		verDetallesButton.setEnabled(true);
	   		verDetallesButton.setToolTipText("Muestra los detalles del torrent en tu navegador.");
	   		verDetallesButton.addActionListener(buttonsAction);
	   		verDetallesButton.putClientProperty("tag", Integer.valueOf(i));
			
			JButton añadirTorrentButton = new JButton("Añadir Torrent");
			añadirTorrentButton.setActionCommand("añadir_torrent");
			añadirTorrentButton.setEnabled(true);
			añadirTorrentButton.setToolTipText("Muestra los detalles del torrent en tu navegador.");
			añadirTorrentButton.addActionListener(buttonsAction);
			añadirTorrentButton.putClientProperty("tag", Integer.valueOf(i));
			
			JPanel archivoTorrentCell = new JPanel();
			Color backgroundColor;
			if(i % 2 == 0){
				backgroundColor = Color.GRAY;
			}else{
				backgroundColor = Color.LIGHT_GRAY;
			}
				
			archivoTorrentCell.setBackground(backgroundColor);
			archivoTorrentCell.setPreferredSize(new Dimension(385, 100));
			archivoTorrentCell.add(categoriaLabel);
			archivoTorrentCell.add(tituloLabel);
			archivoTorrentCell.add(añadirTorrentButton);
			archivoTorrentCell.add(verDetallesButton);
			listContent.add(archivoTorrentCell);
			System.out.println(torrents[i]);
		}
		listContent.setPreferredSize(new Dimension(mainFrame.getWidth(), 100 * torrents.length));
		System.out.println("Num of comps: " + listContent.getComponentCount());
		mainFrame.validate();
	}
	
	public void run() {
    }
}

