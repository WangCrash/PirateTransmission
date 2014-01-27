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
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.ScrollPaneConstants;
import javax.swing.DropMode;
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
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		panel.setBackground(SystemColor.menu);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 404, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		titleLabel = new JLabel(getArtista().getNombre());
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		rateButton = new JButton("Me Gusta");
		rateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel label_1 = new JLabel("Bio");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel label_2 = new JLabel("Artistas Similares");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JScrollPane similarsScrollPane = new JScrollPane();
		
		JLabel label_3 = new JLabel("G\u00E9neros");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		tagsPanel = new JPanel();
		tagsPanel.setBackground(new Color(204, 255, 153));
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
					.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(105)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(169)
					.addComponent(rateButton))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addComponent(label_3)
					.addGap(67)
					.addComponent(tagsPanel, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
					.addGap(17))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(similarsScrollPane)
					.addGap(17))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addComponent(label_4)
					.addGap(49)
					.addComponent(discographyScrollPane)
					.addGap(17))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addComponent(bioScrollPane, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
					.addGap(17))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(14)
					.addComponent(titleLabel)
					.addGap(18)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(rateButton)
					.addGap(38)
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
						.addComponent(similarsScrollPane, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(label_4)
						.addComponent(discographyScrollPane, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addComponent(label_1)
					.addGap(6)
					.addComponent(bioScrollPane, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
		initLabels();
	}

	private void initLabels() {
		Artista artista = this.getArtista();
		getArtistImage();
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
		this.parentView.searchTorrent(getArtista().getNombre());
	}

	@Override
	public void rateItem() {
		if(LastFMManager.getInstance().rateItem(getArtista())){
			getArtista().setRated(true);
			rateButton.setText("No me Gusta");
			new UtilTools().showInfoOKDialog(mainFrame, "", "A�adido a tu biblioteca");
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
							if(getArtista().getDiscografia()[i].getA�o() != 0){
								albumName += " (" + artista.getDiscografia()[i].getA�o() + ")";
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
					System.out.println("Error: imagen no v�lida");
					return;
				}
				ImageIcon image = new ImageIcon(getArtista().getImageURL());
				imageLabel.setText("");
				//imageLabel.setBorder(null);
				imageLabel.setIcon(new UtilTools().getScaledImage(image.getImage(), 230, 170));
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
}
