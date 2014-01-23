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
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
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
		artistLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		songsList = new JList<String>();
		songsScrollPane.setViewportView(songsList);
		
		aboutAlbumEditorPane = new JEditorPane();
		aboutAlbumEditorPane.setContentType("text/html");
		aboutAlbumScrollPane.setViewportView(aboutAlbumEditorPane);
		
		rateButton = new JButton("No me Gusta");
		rateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rateButton.setText(getDisco().isRated()?rateButton.getText():"Me Gusta");
		rateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(getDisco().isRated()){
					removeItem();
				}else{
					rateItem();
				}
			}
		});
		
		JLabel lblGnero = new JLabel("G\u00E9nero");
		lblGnero.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		tagsPanel = new JPanel();
		tagsPanel.setBackground(new Color(204, 255, 153));
		
		imageLabel = new JLabel("");
		imageLabel.setIcon(new ImageIcon(AlbumDetailsView.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblArtista)
									.addGap(39)
									.addComponent(artistLabel, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblArtistasSimilares, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(songsScrollPane))
									.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
										.addComponent(lblGnero, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
										.addGap(35)
										.addComponent(tagsPanel, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblBio, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(aboutAlbumScrollPane, GroupLayout.PREFERRED_SIZE, 422, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(168, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(rateButton, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))
					.addGap(162))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(titleLabel)
					.addGap(18)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addComponent(rateButton)
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblGnero, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(tagsPanel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblArtista)
						.addComponent(artistLabel))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(29)
							.addComponent(lblArtistasSimilares))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(28)
							.addComponent(songsScrollPane, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)))
					.addGap(28)
					.addComponent(lblBio)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(aboutAlbumScrollPane, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		tagsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setLayout(groupLayout);
		initLabels();
	}

	private void initLabels() {		
		Disco disco = getDisco();
		getAlbumImage();
		titleLabel.setText(disco.getNombre());
		artistLabel.setText(disco.getArtista());
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
		parentView.searchTorrent(getDisco().getArtista() + " " + getDisco().getNombre());
	}
	
	@Override
	public void rateItem() {
		if(LastFMManager.getInstance().rateItem(getDisco())){
			getDisco().setRated(true);
			rateButton.setText("No me Gusta");
			new UtilTools().showInfoOKDialog(mainFrame, "", "Añadido a tu biblioteca");
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "No ha sido posible añadir el álbum");
		}
	}
	
	@Override
	public void removeItem(){
		if(LastFMManager.getInstance().removeAlbum(disco)){
			getDisco().setRated(false);
			rateButton.setText("Me Gusta");
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
}
