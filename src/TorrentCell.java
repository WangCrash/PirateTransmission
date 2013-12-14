import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.LineBorder;

import Managers.TransmissionManager;
import Model.ArchivoTorrent;
import Utils.UtilTools;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


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
		System.out.println("creando celda");
		this.archivoTorrent = torrent;
		setBorder(new LineBorder(new Color(128, 128, 128), 2, true));
		
		categoryLabel = new JLabel("Categor\u00EDa");
		categoryLabel.setText(torrent.getCategoria());
		
		titleLabel = new JLabel("T\u00EDtulo");
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
					if(TransmissionManager.addTorrent(archivoTorrent)){
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
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(categoryLabel, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(102, Short.MAX_VALUE)
					.addComponent(addTorrentButton)
					.addGap(27)
					.addComponent(showDetailsButton)
					.addGap(90))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(9)
					.addComponent(categoryLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(titleLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(showDetailsButton)
						.addComponent(addTorrentButton))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
