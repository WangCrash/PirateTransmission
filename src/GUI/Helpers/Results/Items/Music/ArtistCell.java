package GUI.Helpers.Results.Items.Music;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.Helpers.Results.HelperResultsSection;
import Managers.Helpers.LastFMManager;
import Model.Artista;
import Model.HelperItem;
import Utils.UtilTools;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ArtistCell extends MusicResultItem {
	private Artista artista;
	private JPanel tagsPanel;
	private JLabel titleLabel;
	private JButton showDetailsButton;
	private JLabel imageLabel;
	private JButton searchTorrentButton;
	private JButton rateButton;

	public ArtistCell(JFrame mainFrame, HelperResultsSection parentView, HelperItem item) {
		super(mainFrame, parentView, item);
		setArtista((Artista)item);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		searchTorrentButton = new JButton("Buscar Torrent");
		searchTorrentButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchTorrentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchItemTorrent();
			}
		});
		
		showDetailsButton = new JButton("Ver Detalles");
		showDetailsButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		showDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showItemDetails();
			}
		});
		
		imageLabel = new JLabel("");
		imageLabel.setIcon(new ImageIcon(ArtistCell.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		titleLabel = new JLabel("Title");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		tagsPanel = new JPanel();
		
		rateButton = new JButton("No me Gusta");
		rateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rateButton.setText(getArtista().isRated()?rateButton.getText():"Me Gusta");
		rateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getArtista().isRated()){
					removeItem();
				}else{
					rateItem();
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(searchTorrentButton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(showDetailsButton, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rateButton, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
								.addComponent(tagsPanel, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)))
						.addComponent(titleLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(titleLabel)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(tagsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(searchTorrentButton)
								.addComponent(showDetailsButton)
								.addComponent(rateButton))))
					.addContainerGap())
		);
		setLayout(groupLayout);
		initLabels();
	}
	
	private void initLabels() {
		getArtistImage();
		titleLabel.setText(getArtista().getNombre());
		getItemTags();
	}

	@Override
	protected void getItemTags() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				setArtista(LastFMManager.getInstance().getArtistTags(artista));
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						showArtistTags();
					}
				});
			}
		});
		t.start();
	}
	

	private void showArtistTags() {
		if(getArtista().getTags() == null){
			return;
		}
		String[] artistTags = getArtista().getNFirstTags(5);
		for (int i = 0; i < artistTags.length; i++) {
			JLabel tag = new JLabel(artistTags[i]);
			tag.setFont(new Font("Tahoma", Font.PLAIN, 11));
			tagsPanel.add(tag);
		}
	}

	@Override
	public void searchItemTorrent() {
		parentView.searchTorrent(getArtista().getNombre());
	}

	@Override
	public void rateItem() {
		if(LastFMManager.getInstance().rateItem(getArtista())){
			getArtista().setRated(true);
			rateButton.setText("No me Gusta");
			new UtilTools().showInfoOKDialog(mainFrame, "", "Añadido a tu biblioteca");
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "Ha ocurrido un error inesperado");
		}
	}
	
	@Override
	public void removeItem(){
		if(LastFMManager.getInstance().removeArtist(getArtista())){
			getArtista().setRated(false);
			rateButton.setText("Me Gusta");
			new UtilTools().showInfoOKDialog(mainFrame, "", "Artista eliminado");
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No se ha podido eliminar el artista");
		}
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
		this.setHelperItem(artista);
	}
	
	private void showItemDetails() {
		parentView.showItemDetails(getArtista());
	}
	
	private void getArtistImage() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				if(getArtista().getImageURL() == null){
					System.out.println("Error: imagen no válida");
					return;
				}
				ImageIcon image = new ImageIcon(getArtista().getImageURL());
				imageLabel.setText("");
				imageLabel.setBorder(null);
				System.out.println("Ancho: " + (int)imageLabel.getSize().width + ", Alto:" + (int)imageLabel.getSize().getHeight());
				imageLabel.setIcon(new UtilTools().getScaledImage(image.getImage(), 72, 79));
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						revalidate();
					}
				});
			}
		});
		t.start();
	}
}
