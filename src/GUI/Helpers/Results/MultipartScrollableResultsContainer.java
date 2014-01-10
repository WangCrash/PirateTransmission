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

public class MultipartScrollableResultsContainer extends ResultsContainer{
	
	private static final long serialVersionUID = 4915343321261715309L;
	
	private HelperResultsSection parentView;
	
	private JButton searchTorrentButton;
	private JPanel resultsPanel;

	/**
	 * Create the panel.
	 */
	public MultipartScrollableResultsContainer(JFrame mainFrame, HelperResultsSection parentView, HelperItem[] items) {
		super(mainFrame, items);
		this.parentView = parentView;		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		JPanel staticPane = new JPanel();
		staticPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(64, 64, 64), null, null, null));
		staticPane.setBackground(new Color(204, 255, 153));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(staticPane, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(staticPane, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
		);
		
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
					.addComponent(searchTorrentButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		staticPane.setLayout(gl_staticPane);
		
		resultsPanel = new JPanel();
		resultsPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		resultsPanel.setBackground(new Color(204, 255, 153));
		scrollPane.setViewportView(resultsPanel);
		resultsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		setLayout(groupLayout);

	}

	@Override
	protected void showResults() {
		
	}
	public JButton getSearchTorrentButton() {
		return searchTorrentButton;
	}
	public JPanel getContentPanel() {
		return resultsPanel;
	}
}
