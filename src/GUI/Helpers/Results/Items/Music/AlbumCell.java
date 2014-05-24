package GUI.Helpers.Results.Items.Music;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.Helpers.Results.HelperResultsSection;
import GUI.Panel.PanelProperties;
import Managers.PirateBayBot;
import Managers.Helpers.LastFMManager;
import Model.Disco;
import Model.HelperItem;
import Utils.UtilTools;

import java.awt.Cursor;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class AlbumCell extends MusicResultItem {
	private Disco disco;
	private JPanel tagsPanel;
	private JLabel titleLabel;
	private JButton showDetailsButton;
	private JLabel imageLabel;
	private JButton searchTorrentButton;
	private JButton rateButton;
	private JLabel lblNewLabel;
	private JLabel artistLabel;

	public AlbumCell(JFrame mainFrame, HelperResultsSection parentView, HelperItem item) {
		super(mainFrame, parentView, item);
		setOpaque(false);
		setDisco((Disco)item);
		
		setBackground(PanelProperties.BACKGROUND);
		setBorder(PanelProperties.BORDER);
		
		searchTorrentButton = new JButton("");
		searchTorrentButton.setIcon(new ImageIcon(AlbumCell.class.getResource("/images/HelperResults/search-torrent-icon.png")));
		searchTorrentButton.setToolTipText("Buscar Torrent");
		searchTorrentButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchTorrentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchItemTorrent();
			}
		});
		
		showDetailsButton = new JButton("");
		showDetailsButton.setToolTipText("Ver Detalles");
		showDetailsButton.setIcon(new ImageIcon(AlbumCell.class.getResource("/images/HelperResults/show-details-icon.png")));
		showDetailsButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		showDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showItemDetails();
			}
		});
		
		imageLabel = new JLabel("");
		imageLabel.setIcon(new ImageIcon(AlbumCell.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		titleLabel = new JLabel("Title");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		tagsPanel = new JPanel();
		tagsPanel.setBackground(PanelProperties.TRANSPARENT_BACKGROUND);
		
		rateButton = new JButton("No me Gusta");
		rateButton.setIcon(new ImageIcon(AlbumCell.class.getResource("/images/HelperResults/like-music-icon.png")));
		rateButton.setToolTipText("A\u00F1adir a tu colecci\u00F3n");
		rateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rateButton.setText("");
		rateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getDisco().getIsRated()){
					removeItem();
				}else{
					rateItem();
				}
			}
		});
		
		lblNewLabel = new JLabel("Artista");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		artistLabel = new JLabel("New label");
		artistLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
					searchArtist();
				}
			}
		});
		artistLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(88)
					.addComponent(lblNewLabel))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(artistLabel, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
						.addComponent(tagsPanel, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(38)
							.addComponent(searchTorrentButton)
							.addGap(10)
							.addComponent(showDetailsButton, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(rateButton, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(titleLabel)
					.addGap(18)
					.addComponent(lblNewLabel)
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(imageLabel, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
							.addGap(22))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(artistLabel)
							.addGap(11)
							.addComponent(tagsPanel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(searchTorrentButton)
								.addComponent(showDetailsButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addComponent(rateButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))))
					.addGap(24))
		);
		setLayout(groupLayout);
		initLabels();
	}
	
	private void initLabels() {
		getArtistImage();
		titleLabel.setText(getDisco().getNombre());
		setUpRateButton();
		artistLabel.setText("<HTML><U>" + getDisco().getArtista() + "<U><HTML>");
		artistLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		getItemTags();
	}

	@Override
	protected void getItemTags() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				setDisco(LastFMManager.getInstance().getAlbumTags(getDisco()));
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						showAlbumTags();
					}
				});
			}
		});
		t.start();
	}
	

	private void showAlbumTags() {
		if(getDisco().getTags() == null){
			return;
		}
		String[] albumTags = getDisco().getNFirstTags(5);
		for (int i = 0; i < albumTags.length; i++) {
			JLabel tag = new JLabel("<HTML><U>" + albumTags[i] + "<U><HTML>");
			tag.setCursor(new Cursor(Cursor.HAND_CURSOR));
			tag.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JLabel tag = (JLabel)e.getSource();
					if(e.getClickCount() == 1){
						searchTag(tag.getText());
					}
				}
			});
			tag.setFont(new Font("Tahoma", Font.PLAIN, 11));
			tagsPanel.add(tag);
		}
	}

	@Override
	public void searchItemTorrent() {
		parentView.searchTorrent(getDisco().getArtista() + " " + getDisco().getNombre(), PirateBayBot.CATEGORY_MUSIC, getDisco());
	}

	@Override
	public void rateItem() {
		if(LastFMManager.getInstance().rateItem(getDisco())){
			getDisco().setIsRated(true);
			setUpRateButton();
			new UtilTools().showInfoOKDialog(mainFrame, "", "Añadido a tu biblioteca");
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No ha sido posible añadir el álbum");
		}
	}
	
	@Override
	public void removeItem(){
		if(LastFMManager.getInstance().removeFromLibrary(disco)){
			getDisco().setIsRated(false);
			setUpRateButton();
			new UtilTools().showInfoOKDialog(mainFrame, "", "Álbum eliminado");
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No se ha podido eliminar el álbum");
		}
	}

	public Disco getDisco() {
		return disco;
	}

	public void setDisco(Disco disco) {
		this.disco = disco;
		this.setHelperItem(disco);
	}
	
	private void showItemDetails() {
		parentView.showItemDetails(getDisco());
	}
	
	private void getArtistImage() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				if(getDisco().getImageURL() == null){
					System.out.println("Error: imagen no válida");
					return;
				}
				ImageIcon image = new ImageIcon(getDisco().getImageURL());
				imageLabel.setText("");
				imageLabel.setBorder(null);
				System.out.println("Ancho: " + (int)imageLabel.getSize().width + ", Alto:" + (int)imageLabel.getSize().getHeight());
				imageLabel.setIcon(new UtilTools().getScaledImageIcon(image.getImage(), 72, 79));
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						revalidate();
					}
				});
			}
		});
		t.start();
	}
	private void searchArtist() {
		String search = getDisco().getArtista();
		parentView.searchItem(search, LastFMManager.LASTFM_ARTIST_SEARCH_OPTION);		
	}
	
	private void searchTag(String tag){
		tag = tag.replaceAll("<HTML>", "");
		tag = tag.replaceAll("<U>", "");
		String search = tag;
		parentView.searchItem(search, LastFMManager.LASTFM_TAG_SEARCH_OPTION);
	}
	
	private void setUpRateButton(){
		if(getDisco().getIsRated()){
			rateButton.setToolTipText("Eliminar de tu colección");
			rateButton.setIcon(new ImageIcon(ArtistCell.class.getResource("/images/HelperResults/dislike-music-icon.png")));
		}else{
			rateButton.setToolTipText("Añadir a tu colección");
			rateButton.setIcon(new ImageIcon(ArtistCell.class.getResource("/images/HelperResults/like-music-icon.png")));
		}
	}
}
