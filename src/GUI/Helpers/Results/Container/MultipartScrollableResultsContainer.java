package GUI.Helpers.Results.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingUtilities;

import java.awt.Color;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import GUI.Helpers.Results.HelperResultsSection;
import GUI.Helpers.Results.Items.HelperResultItem;
import GUI.Helpers.Results.Items.Films.FilmDetailsView;
import GUI.Helpers.Results.Items.Music.AlbumDetailsView;
import GUI.Helpers.Results.Items.Music.ArtistDetailsView;
import Model.Artista;
import Model.Disco;
import Model.FichaPelicula;
import Model.HelperItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.ImageIcon;

public class MultipartScrollableResultsContainer extends ResultsContainer{
	
	private static final long serialVersionUID = 4915343321261715309L;
	
	private HelperResultsSection parentView;
	private HelperItem helperItem;
	private HelperResultItem itemDetailsPanel;
	
	private JButton searchTorrentButton;
	private JPanel resultsPanel;
	private JButton sendBackButton;

	/**
	 * Create the panel.
	 */
	public MultipartScrollableResultsContainer(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem, boolean enableBackButton) {
		super(mainFrame, null);
		setBackground(SystemColor.menu);
		this.parentView = parentView;
		this.helperItem = helperItem;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		JPanel staticPane = new JPanel();
		staticPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(64, 64, 64), null, null, null));
		staticPane.setBackground(new Color(204, 255, 153));
		
		sendBackButton = new JButton("");
		sendBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				goBack();
			}
		});
		sendBackButton.setIcon(new ImageIcon(MultipartScrollableResultsContainer.class.getResource("/images/backButton.png")));
		sendBackButton.setEnabled(enableBackButton);
		
		searchTorrentButton = new JButton("");
		searchTorrentButton.setToolTipText("Buscar Torrent\r\n");
		searchTorrentButton.setIcon(new ImageIcon(MultipartScrollableResultsContainer.class.getResource("/images/HelperResults/search-torrent-icon.png")));
		searchTorrentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				itemDetailsPanel.searchItemTorrent();
			}
		});
		GroupLayout gl_staticPane = new GroupLayout(staticPane);
		gl_staticPane.setHorizontalGroup(
			gl_staticPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_staticPane.createSequentialGroup()
					.addContainerGap(210, Short.MAX_VALUE)
					.addComponent(searchTorrentButton)
					.addGap(204))
		);
		gl_staticPane.setVerticalGroup(
			gl_staticPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_staticPane.createSequentialGroup()
					.addGap(2)
					.addComponent(searchTorrentButton, GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE)
					.addGap(3))
		);
		staticPane.setLayout(gl_staticPane);
		
		resultsPanel = new JPanel();
		resultsPanel.setBackground(new Color(204, 204, 153));
		scrollPane.setViewportView(resultsPanel);
		resultsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(1)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(sendBackButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(446, Short.MAX_VALUE))))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(staticPane, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
					.addGap(1))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(sendBackButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(staticPane, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		setLayout(groupLayout);
		showResults();
	}

	@Override
	protected void showResults() {
		System.out.println("Showing results");
		if(this.helperItem.getClass() == FichaPelicula.class){
			itemDetailsPanel = new FilmDetailsView(mainFrame, parentView, helperItem);
		}else if(this.helperItem.getClass() == Artista.class){
			itemDetailsPanel = new ArtistDetailsView(mainFrame, parentView, helperItem);
		}else if(this.helperItem.getClass() == Disco.class){
			itemDetailsPanel = new AlbumDetailsView(mainFrame, parentView, helperItem);
		}
		//itemDetailsPanel.setBackground(new Color(204, 255, 153));
		resultsPanel.add(itemDetailsPanel);
		//resultsPanel.add(new JButton("ADFSASDFASDFASDF"));
		resultsPanel.revalidate();
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				//parentView.revalidate();
				mainFrame.validate();
			}
		});
	}
	public void goBack(){
		System.out.println("GOING BACK");
		parentView.showResults();
	}

	@Override
	public void setScrollPosition(int verticalValue) {
	}

	@Override
	public int getScrollValue(){
		return 0;
	}
}
