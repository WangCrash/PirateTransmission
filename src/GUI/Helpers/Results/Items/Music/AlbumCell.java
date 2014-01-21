package GUI.Helpers.Results.Items.Music;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.Helpers.Results.HelperResultsSection;
import GUI.Helpers.Results.Container.ResultsContainer;
import Managers.Helpers.LastFMManager;
import Model.Disco;
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
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		searchTorrentButton = new JButton("Buscar Torrent");
		searchTorrentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchItemTorrent();
			}
		});
		
		showDetailsButton = new JButton("Ver Detalles");
		showDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showItemDetails();
			}
		});
		
		imageLabel = new JLabel("");
		imageLabel.setIcon(new ImageIcon(AlbumCell.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		titleLabel = new JLabel("Title");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		tagsPanel = new JPanel();
		
		rateButton = new JButton("Me Gusta");
		rateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rateItem();
			}
		});
		
		lblNewLabel = new JLabel("Artista");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		artistLabel = new JLabel("New label");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(titleLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(tagsPanel, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
										.addComponent(artistLabel, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
										.addComponent(lblNewLabel)))))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(88)
							.addComponent(searchTorrentButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(showDetailsButton, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rateButton, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(titleLabel)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(artistLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tagsPanel, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(searchTorrentButton)
								.addComponent(showDetailsButton)
								.addComponent(rateButton))
							.addGap(12))
						.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		setLayout(groupLayout);
		initLabels();
	}
	
	private void initLabels() {
		getArtistImage();
		titleLabel.setText(getDisco().getNombre());
		artistLabel.setText(getDisco().getArtista());
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
						showArtistTags();
					}
				});
			}
		});
		t.start();
	}
	

	private void showArtistTags() {
		if(getDisco().getTags() == null){
			return;
		}
		String[] albumTags = getDisco().getNFirstTags(5);
		for (int i = 0; i < albumTags.length; i++) {
			JLabel tag = new JLabel(albumTags[i]);
			tagsPanel.add(tag);
		}
	}

	@Override
	public void searchItemTorrent() {
		parentView.searchTorrent(getDisco().getArtista() + " " + getDisco().getNombre());
	}

	@Override
	public void rateItem() {
		if(LastFMManager.getInstance().rateItem(getDisco())){
			new UtilTools().showInfoOKDialog(mainFrame, "", "Añadido a tu biblioteca");
		}else{
			new UtilTools().showWarningDialog(mainFrame, "Error", "Ha ocurrido un error inesperado");
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
