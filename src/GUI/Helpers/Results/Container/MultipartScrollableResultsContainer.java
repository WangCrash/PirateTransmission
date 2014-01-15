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
import GUI.Helpers.Results.Items.Films.FilmDetailsView;
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
	private FilmDetailsView filmDetailsPanel;
	
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
		
		searchTorrentButton = new JButton("Buscar Torrent");
		searchTorrentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filmDetailsPanel.searchItemTorrent();
			}
		});
		GroupLayout gl_staticPane = new GroupLayout(staticPane);
		gl_staticPane.setHorizontalGroup(
			gl_staticPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_staticPane.createSequentialGroup()
					.addGap(166)
					.addComponent(searchTorrentButton)
					.addContainerGap(166, Short.MAX_VALUE))
		);
		gl_staticPane.setVerticalGroup(
			gl_staticPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_staticPane.createSequentialGroup()
					.addGap(5)
					.addComponent(searchTorrentButton, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
					.addGap(3))
		);
		staticPane.setLayout(gl_staticPane);
		
		resultsPanel = new JPanel();
		resultsPanel.setBackground(new Color(204, 255, 153));
		scrollPane.setViewportView(resultsPanel);
		resultsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(1)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(sendBackButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 451, GroupLayout.PREFERRED_SIZE)
						.addComponent(staticPane, GroupLayout.PREFERRED_SIZE, 451, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(sendBackButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(staticPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		setLayout(groupLayout);
		showResults();
	}

	@Override
	protected void showResults() {
		System.out.println("Showing results");
		filmDetailsPanel = new FilmDetailsView(mainFrame, parentView, helperItem);
		filmDetailsPanel.setBackground(new Color(204, 255, 153));
		resultsPanel.add(filmDetailsPanel);
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
}
