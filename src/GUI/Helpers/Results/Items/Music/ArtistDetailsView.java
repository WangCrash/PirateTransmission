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


@SuppressWarnings("serial")
public class ArtistDetailsView extends MusicResultItem {
	private Artista artista;
	private JEditorPane bioEditorPane;
	private JList<String> discographyList;
	private JList<String> similarsList;
	private JLabel titleLabel;
	private JPanel tagsPanel;
	private JLabel imageLabel;

	public ArtistDetailsView(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem) {
		super(mainFrame, parentView, helperItem);
		this.setArtista((Artista)helperItem);
		
		JScrollPane bioScrollPane = new JScrollPane();
		
		JLabel lblBio = new JLabel("Bio");
		lblBio.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JScrollPane discographyScrollPane = new JScrollPane();
		
		JLabel lblNewLabel = new JLabel("Discograf\u00EDa");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JScrollPane similarsScrollPane = new JScrollPane();
		
		JLabel lblArtistasSimilares = new JLabel("Artistas Similares");
		lblArtistasSimilares.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		titleLabel = new JLabel("Artist");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		similarsList = new JList<String>();
		similarsScrollPane.setViewportView(similarsList);
		
		discographyList = new JList<String>();
		discographyScrollPane.setViewportView(discographyList);
		
		bioEditorPane = new JEditorPane();
		bioScrollPane.setViewportView(bioEditorPane);
		
		JLabel lblNewLabel_1 = new JLabel("G\u00E9neros");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		tagsPanel = new JPanel();
		
		JButton rateButton = new JButton("Me gusta");
		rateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rateItem();
			}
		});
		
		imageLabel = new JLabel("");
		imageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		imageLabel.setIcon(new ImageIcon(ArtistDetailsView.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tagsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 141, GroupLayout.PREFERRED_SIZE)
							.addComponent(rateButton)
							.addGap(134))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(bioScrollPane, 0, 0, Short.MAX_VALUE)
							.addComponent(lblBio, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblNewLabel_1)
								.addGap(67)
								.addComponent(tagsPanel, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblArtistasSimilares, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
								.addGap(4)
								.addComponent(similarsScrollPane, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblNewLabel)
								.addGap(49)
								.addComponent(discographyScrollPane, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(31, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(143)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(143, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(19)
					.addComponent(titleLabel)
					.addGap(18)
					.addComponent(imageLabel, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rateButton)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(tagsPanel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lblArtistasSimilares))
						.addComponent(similarsScrollPane, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(discographyScrollPane, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addComponent(lblBio)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(bioScrollPane, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
		);
		setLayout(groupLayout);
		initLabels();
	}

	private void initLabels() {
		Artista artista = this.getArtista();
		getArtistImage();
		titleLabel.setText(artista.getNombre());
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
			new UtilTools().showInfoOKDialog(mainFrame, "", "A�adido a tu biblioteca");
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "Ha ocurrido un error inesperado");
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
			JLabel tag = new JLabel(artistTags[i]);
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
							discog[i] = getArtista().getDiscografia()[i].getNombre();
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
}
