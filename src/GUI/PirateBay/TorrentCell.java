package GUI.PirateBay;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.LineBorder;

import Managers.ApplicationConfiguration;
import Model.ArchivoTorrent;
import Utils.UtilTools;

import java.awt.Color;

import java.awt.SystemColor;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;


public class TorrentCell extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4103719219357362870L;

	private ArchivoTorrent archivoTorrent;
	
	private JFrame mainFrame;
	private JLabel titleLabel;
	private JLabel categoryLabel;
	/**
	 * Create the panel.
	 */
	public TorrentCell(ArchivoTorrent torrent) {
		this.archivoTorrent = torrent;
		setBorder(new LineBorder(new Color(128, 128, 128), 2, true));
		
		categoryLabel = new JLabel("Categor\u00EDa");
		categoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
		categoryLabel.setBackground(SystemColor.text);
		categoryLabel.setText(torrent.getCategoria());
		
		titleLabel = new JLabel("T\u00EDtulo");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBackground(SystemColor.text);
		titleLabel.setText(archivoTorrent.getTitulo());
		
		JButton showDetailsButton = new JButton("Ver Detalles");
		showDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((archivoTorrent != null) && (archivoTorrent.getDetailsURL() != null)){
					new UtilTools().openURLInNavigator(archivoTorrent.getDetailsURL());
				}else{
					System.out.println(archivoTorrent);
				}
			}
		});
		
		JButton addTorrentButton = new JButton("A\u00F1adir Torrent");
		addTorrentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(archivoTorrent != null){
					if(ApplicationConfiguration.getInstance().getDefaultTorrentClient().addTorrent(archivoTorrent)){
						new UtilTools().showInfoOKDialog(mainFrame, "", "Torrent añadido");
					}else{
						new UtilTools().showWarningDialog(mainFrame, "", "No se ha podido añadir el torrent");
					}
				}
			}
		});
		setLayout(new MigLayout("", "[175px][10px][171px]", "[14px][14px][23px]"));
		add(categoryLabel, "cell 0 0 3 1,growx,aligny top");
		add(titleLabel, "cell 0 1 3 1,growx,aligny top");
		add(addTorrentButton, "cell 0 2,alignx right,aligny top");
		add(showDetailsButton, "cell 2 2,alignx left,aligny top");

	}

	public JLabel getTitleLabel() {
		return titleLabel;
	}
	public JLabel getCategoryLabel() {
		return categoryLabel;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
}
