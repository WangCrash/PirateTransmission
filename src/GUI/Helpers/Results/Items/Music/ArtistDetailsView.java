package GUI.Helpers.Results.Items.Music;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import GUI.Helpers.Results.HelperResultsSection;
import Managers.Helpers.LastFMManager;
import Model.HelperItem;
import Model.Artista;
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
import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.JButton;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.ScrollPaneConstants;

import java.awt.SystemColor;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


@SuppressWarnings("serial")
public class ArtistDetailsView extends MusicResultItem {
	private Artista artista;
	private JPanel tagsPanel;
	private JLabel imageLabel;
	private JButton rateButton;
	private JLabel titleLabel;
	private JList<String> discographyList;
	private JList<String> similarsList;
	private JEditorPane bioEditorPane;

	public ArtistDetailsView(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem) {
		super(mainFrame, parentView, helperItem);
		setBackground(new Color(204, 255, 153));
		this.setArtista((Artista)helperItem);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(64, 64, 64), null, null, null));
		panel.setBackground(SystemColor.menu);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 430, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		titleLabel = new JLabel(getArtista().getNombre());
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		rateButton = new JButton("");
		rateButton.setIcon(new ImageIcon(ArtistDetailsView.class.getResource("/images/HelperResults/like-music-icon.png")));
		rateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel label_1 = new JLabel("Bio");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel label_2 = new JLabel("Artistas Similares");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JScrollPane similarsScrollPane = new JScrollPane();
		
		JLabel label_3 = new JLabel("G\u00E9neros");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		tagsPanel = new JPanel();
		tagsPanel.setBackground(SystemColor.menu);
		tagsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JScrollPane bioScrollPane = new JScrollPane();
		bioScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel label_4 = new JLabel("Discograf\u00EDa");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JScrollPane discographyScrollPane = new JScrollPane();
		
		imageLabel = new JLabel("");
		imageLabel.setIcon(new ImageIcon(ArtistDetailsView.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		discographyList = new JList<String>();
		discographyList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2 && discographyList.getSelectedIndex() != -1){
					searchAlbum();
				}
			}
		});
		discographyScrollPane.setViewportView(discographyList);
		
		similarsList = new JList<String>();
		similarsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2 && similarsList.getSelectedIndex() != -1){
					searchSimilarArtist();
				}
			}
		});
		similarsScrollPane.setViewportView(similarsList);
		
		bioEditorPane = new JEditorPane();
		bioEditorPane.setContentType("text/html");
		bioEditorPane.setEditable(false);
		bioScrollPane.setViewportView(bioEditorPane);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(6)
					.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 411, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(148)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(178)
					.addComponent(rateButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addComponent(label_3)
					.addGap(67)
					.addComponent(tagsPanel, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(similarsScrollPane, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addComponent(label_4)
					.addGap(49)
					.addComponent(discographyScrollPane, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addComponent(bioScrollPane, GroupLayout.PREFERRED_SIZE, 394, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(14)
					.addComponent(titleLabel)
					.addGap(18)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(rateButton, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addGap(23)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(5)
							.addComponent(label_3))
						.addComponent(tagsPanel, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
					.addGap(23)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(1)
							.addComponent(label_2))
						.addComponent(similarsScrollPane, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
					.addGap(22)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(label_4)
						.addComponent(discographyScrollPane, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addComponent(label_1)
					.addGap(11)
					.addComponent(bioScrollPane, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
		initLabels();
	}

	private void initLabels() {
		Artista artista = this.getArtista();
		getArtistImage();
		setUpRateButton();
		if(artista.getTags() == null){
			getItemTags();
		}else{
			showArtistTags();
		}
		showSimilarArtists();
		showDiscography();
		showBio();
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
		this.setHelperItem(artista);
	}

	@Override
	public void searchItemTorrent() {
		this.parentView.searchTorrent(getArtista().getNombre(), getArtista());
	}

	@Override
	public void rateItem() {
		if(LastFMManager.getInstance().rateItem(getArtista())){
			getArtista().setIsRated(true);
			setUpRateButton();
			new UtilTools().showInfoOKDialog(mainFrame, "", "Añadido a tu biblioteca");
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "Ha ocurrido un error inesperado");
		}
	}
	
	@Override
	public void removeItem(){
		if(LastFMManager.getInstance().removeFromLibrary(getArtista())){
			getArtista().setIsRated(false);
			setUpRateButton();
			new UtilTools().showInfoOKDialog(mainFrame, "", "Artista eliminado");
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No se ha podido eliminar el artista");
		}
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
			JLabel tag = new JLabel("<HTML><U>" + artistTags[i] + "<U><HTML>");
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
	
	private void showSimilarArtists(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				setArtista(LastFMManager.getInstance().getSimilarArtists(getArtista()));
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						String[] similars = new String[getArtista().getSimilares().length];
						for (int i = 0; i < similars.length; i++) {
							similars[i] = getArtista().getSimilares()[i].getNombre();
						}
						similarsList.setListData(similars);
					}
				});
			}
		});
		t.start();
	}
	
	private void showBio() {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				setArtista(LastFMManager.getInstance().getArtistBio(getArtista()));
				bioEditorPane.setText(getArtista().getBio());
				System.out.println(getArtista().getBio());
			}
		});
		t.start();
	}

	private void showDiscography() {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				setArtista(LastFMManager.getInstance().getArtistTopAlbums(getArtista()));
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						String[] discog = new String[getArtista().getDiscografia().length];
						for (int i = 0; i < discog.length; i++) {
							String albumName = getArtista().getDiscografia()[i].getNombre();
							if(getArtista().getDiscografia()[i].getAño() != 0){
								albumName += " (" + artista.getDiscografia()[i].getAño() + ")";
							}
							discog[i] = albumName;
						}
						discographyList.setListData(discog);
					}
				});
			}
		});
		t.start();
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
				//imageLabel.setBorder(null);
				imageLabel.setIcon(new UtilTools().getScaledImageIcon(image.getImage(), 140, 170));
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						revalidate();
					}
				});
			}
		});
		t.start();
	}
	
	private void searchSimilarArtist() {
		String search =	getArtista().getSimilares()[similarsList.getSelectedIndex()].getNombre();
		parentView.searchItem(search, LastFMManager.LASTFM_ARTIST_SEARCH_OPTION);
	}
	
	private void searchAlbum(){
		String search = getArtista().getNombre() + " " + getArtista().getDiscografia()[discographyList.getSelectedIndex()].getNombre();
		parentView.searchItem(search, LastFMManager.LASTFM_ALBUM_SEARCH_OPTION);
	}
	
	private void searchTag(String tag){
		tag = tag.replaceAll("<HTML>", "");
		tag = tag.replaceAll("<U>", "");
		String search = tag;
		parentView.searchItem(search, LastFMManager.LASTFM_TAG_SEARCH_OPTION);
	}
	
	private void setUpRateButton(){
		if(getArtista().getIsRated()){
			rateButton.setToolTipText("Eliminar de tu colección");
			rateButton.setIcon(new ImageIcon(ArtistCell.class.getResource("/images/HelperResults/dislike-music-icon.png")));
		}else{
			rateButton.setToolTipText("Añadir a tu colección");
			rateButton.setIcon(new ImageIcon(ArtistCell.class.getResource("/images/HelperResults/like-music-icon.png")));
		}
	}
}
