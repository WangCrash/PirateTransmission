package GUI.Helpers.Results;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;

import java.awt.Color;

import javax.swing.ScrollPaneConstants;

import GUI.PirateBay.PiratebaySection;
import Model.HelperItem;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.CardLayout;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;

public class HelperResultsSection extends JPanel {
	
	private static final long serialVersionUID = -263118633110893005L;
	
	private JFrame mainFrame;
	private PiratebaySection piratebaySection;
	private JPanel rootPanel;
	private ResultsContainer resultsContainer;

	/**
	 * Create the panel.
	 */
	public HelperResultsSection(JFrame rootFrame, PiratebaySection piratebaySection) {
		this.mainFrame = rootFrame;
		this.piratebaySection = piratebaySection;
		
		setBorder(new LineBorder(new Color(64, 64, 64)));		
		rootPanel = new JPanel();
		rootPanel.setForeground(new Color(0, 0, 0));
		rootPanel.setBackground(new Color(204, 255, 153));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(rootPanel, GroupLayout.PREFERRED_SIZE, 432, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(rootPanel, GroupLayout.PREFERRED_SIZE, 325, Short.MAX_VALUE)
					.addContainerGap())
		);
		FlowLayout fl_rootPanel = new FlowLayout(FlowLayout.CENTER, 0, 0);
		rootPanel.setLayout(fl_rootPanel);
		setLayout(groupLayout);
		//includeSections();
	}

	public void searchTorrent(String search){
		piratebaySection.searchTorrent(search);
	}
	
	public void showResults(HelperItem[] items){
		if(items.length == 0){
			return;
		}
		if(items.length == 1){
			resultsContainer = new MultipartScrollableResultsContainer(this.mainFrame, this, items);
		}else{
			resultsContainer = new SimpleScrollableResultsContainer(this.mainFrame, this, items);
		}
		rootPanel.add(resultsContainer);
	}
}
