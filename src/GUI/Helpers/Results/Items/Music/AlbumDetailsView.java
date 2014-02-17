package GUI.Helpers.Results.Items.Music;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import GUI.Helpers.Results.HelperResultsSection;
import Managers.Helpers.LastFMManager;
import Model.Disco;
import Model.HelperItem;
import Utils.UtilTools;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.ImageIcon;

import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


@SuppressWarnings("serial")
public class AlbumDetailsView extends MusicResultItem {
	private Disco disco;
	private JEditorPane aboutAlbumEditorPane;
	private JList<String> songsList;
	private JLabel titleLabel;
	private JLabel artistLabel;
	private JPanel tagsPanel;
	private JLabel imageLabel;
	private JButton rateButton;

	public AlbumDetailsView(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem) {
		super(mainFrame, parentView, helperItem);
		this.setDisco((Disco)helperItem);
		
		JScrollPane aboutAlbumScrollPane = new JScrollPane();
		
		JLabel lblBio = new JLabel("Sobre este disco");
		lblBio.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JScrollPane songsScrollPane = new JScrollPane();
		
		JLabel lblArtistasSimilares = new JLabel("Canciones");
		lblArtistasSimilares.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		titleLabel = new JLabel("Album");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblArtista = new JLabel("Artista");
		lblArtista.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		artistLabel = new JLabel("artista");
		artistLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
					searchArtist();
				}
			}
		});
		artistLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		songsList = new JList<String>();
		songsScrollPane.setViewportView(songsList);
		
		aboutAlbumEditorPane = new JEditorPane();
		aboutAlbumEditorPane.setContentType("text/html");
		aboutAlbumScrollPane.setViewportView(aboutAlbumEditorPane);
		
		rateButton = new JButton("No me Gusta");
		rateButton.setIcon(new ImageIcon(AlbumDetailsView.class.getResource("/images/HelperResults/like-music-icon.png")));
		rateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rateButton.setText("");
		rateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(getDisco().getIsRated()){
					removeItem();
				}else{
					rateItem();
				}
			}
		});
		
		JLabel lblGnero = new JLabel("G\u00E9nero");
		lblGnero.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		tagsPanel = new JPanel();
		tagsPanel.setBackground(SystemColor.menu);
		
		imageLabel = new JLabel("");
		imageLabel.setIcon(new ImageIcon(AlbumDetailsView.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		tagsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(165)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(183)
					.addComponent(rateButton))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblGnero, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(tagsPanel, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblArtista)
					.addGap(39)
					.addComponent(artistLabel, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblArtistasSimilares, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(songsScrollPane, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblBio, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(aboutAlbumScrollPane, GroupLayout.PREFERRED_SIZE, 422, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(titleLabel)
					.addGap(18)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addComponent(rateButton, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(8)
							.addComponent(lblGnero, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addComponent(tagsPanel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblArtista)
						.addComponent(artistLabel))
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lblArtistasSimilares))
						.addComponent(songsScrollPane, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addComponent(lblBio)
					.addGap(6)
					.addComponent(aboutAlbumScrollPane, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE))
		);
		setLayout(groupLayout);
		initLabels();
	}

	private void initLabels() {		
		Disco disco = getDisco();
		getAlbumImage();
		titleLabel.setText(disco.getNombre());
		setUpRateButton();
		artistLabel.setText("<HTML><U>" + disco.getArtista() + "<U><HTML>");
		artistLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		if(disco.getTags() == null){
			getItemTags();
		}else{
			showAlbumTags();
		}
		showSongs();
		showDescription();
	}

	private void showDescription() {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				setDisco(LastFMManager.getInstance().getAlbumWikiText(getDisco()));
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						aboutAlbumEditorPane.setText(getDisco().getWikiText());
					}
				});
			}
		});
		t.start();
	}

	private void showSongs() {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				setDisco(LastFMManager.getInstance().getAlbumTracks(getDisco()));
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						String[] tracks = new String[getDisco().getCanciones().length];
						for (int i = 0; i < tracks.length; i++) {
							tracks[i] = getDisco().getCanciones()[i];
						}
						songsList.setListData(tracks);
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

	public Disco getDisco() {
		return disco;
	}

	public void setDisco(Disco disco) {
		this.disco = disco;
		this.setHelperItem(disco);
	}

	@Override
	public void searchItemTorrent() {		
		parentView.searchTorrent(getDisco().getArtista() + " " + getDisco().getNombre(), getDisco());
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
		if(LastFMManager.getInstance().removeAlbum(disco)){
			getDisco().setIsRated(false);
			setUpRateButton();
			new UtilTools().showInfoOKDialog(mainFrame, "", "Álbum eliminado");
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No se ha podido eliminar el álbum");
		}
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
	public JLabel getImageLabel() {
		return imageLabel;
	}
	
	private void getAlbumImage() {
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
				imageLabel.setIcon(new UtilTools().getScaledImage(image.getImage(), imageLabel.getWidth(), imageLabel.getHeight()));
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
