package GUI.Helpers.Results;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

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
	
	private JButton searchTorrentButton;
	private JPanel resultsPanel;
	private JButton sendBackButton;

	/**
	 * Create the panel.
	 */
	public MultipartScrollableResultsContainer(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem, HelperItem[] items) {
		super(mainFrame, items);
		setBackground(SystemColor.menu);
		this.parentView = parentView;
		this.helperItem = helperItem;
		this.items = items;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(1, 24, 441, 251);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		JPanel staticPane = new JPanel();
		staticPane.setBounds(1, 279, 441, 37);
		staticPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(64, 64, 64), null, null, null));
		staticPane.setBackground(new Color(204, 255, 153));
		
		sendBackButton = new JButton("");
		sendBackButton.setIcon(new ImageIcon(MultipartScrollableResultsContainer.class.getResource("/images/backButton.png")));
		sendBackButton.setBounds(209, 2, 37, 23);
		sendBackButton.setEnabled(items != null);
		
		searchTorrentButton = new JButton("Buscar Torrent");
		searchTorrentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GroupLayout gl_staticPane = new GroupLayout(staticPane);
		gl_staticPane.setHorizontalGroup(
			gl_staticPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_staticPane.createSequentialGroup()
					.addGap(165)
					.addComponent(searchTorrentButton)
					.addContainerGap(170, Short.MAX_VALUE))
		);
		gl_staticPane.setVerticalGroup(
			gl_staticPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_staticPane.createSequentialGroup()
					.addComponent(searchTorrentButton, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
					.addContainerGap())
		);
		staticPane.setLayout(gl_staticPane);
		setLayout(null);
		
		resultsPanel = new JPanel();
		resultsPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		resultsPanel.setBackground(new Color(204, 255, 153));
		scrollPane.setViewportView(resultsPanel);
		resultsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		add(scrollPane);
		add(sendBackButton);
		add(staticPane);

	}

	@Override
	protected void showResults() {
		
	}
	public void goBack(){
		if(items != null){
			parentView.showResults(items);
		}
	}
}
