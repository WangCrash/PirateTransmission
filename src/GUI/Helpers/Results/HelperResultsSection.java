package GUI.Helpers.Results;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import java.awt.Color;

import javax.swing.ScrollPaneConstants;

import GUI.PirateBay.PiratebaySection;
import Model.FichaPelicula;
import Model.HelperItem;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.CardLayout;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;
import java.awt.SystemColor;

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
		setBackground(new Color(230, 230, 250));
		this.mainFrame = rootFrame;
		this.piratebaySection = piratebaySection;
		
		setBorder(new LineBorder(new Color(64, 64, 64)));		
		rootPanel = new JPanel();
		rootPanel.setBackground(new Color(204, 255, 153));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(rootPanel, GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(rootPanel, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
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
		if(items == null){
			System.out.println("items es null");
			return;
		}
		else if(items.length == 0){
			System.out.println("no hay items");
			return;
		}
		if(items.length == 1){
			System.out.println("multipart results container");
			resultsContainer = new MultipartScrollableResultsContainer(this.mainFrame, this, items);
		}else{
			System.out.println("simple results container");
			resultsContainer = new SimpleScrollableResultsContainer(this.mainFrame, this, items);
		}
		rootPanel.add(resultsContainer);
	}
}
