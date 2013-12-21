package GUI;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.LineBorder;

import Model.ArchivoTorrent;
import Utils.UtilTools;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.SystemColor;


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
		categoryLabel.setBackground(SystemColor.text);
		categoryLabel.setText(torrent.getCategoria());
		
		titleLabel = new JLabel("T\u00EDtulo");
		titleLabel.setBackground(SystemColor.text);
		titleLabel.setText(archivoTorrent.getTitulo());
		
		JButton showDetailsButton = new JButton("Ver Detalles");
		showDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(archivoTorrent != null){
					new UtilTools().openURLInNavigator(archivoTorrent.getDetailsURL());
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
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 415, GroupLayout.PREFERRED_SIZE)
						.addComponent(categoryLabel, GroupLayout.PREFERRED_SIZE, 415, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(28, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(122, Short.MAX_VALUE)
					.addComponent(addTorrentButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(showDetailsButton)
					.addGap(112))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(categoryLabel)
					.addGap(5)
					.addComponent(titleLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(addTorrentButton)
						.addComponent(showDetailsButton))
					.addGap(10))
		);
		setLayout(groupLayout);

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
