package GUI.Helpers.Results;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;

import GUI.Helpers.Results.Container.MultipartScrollableResultsContainer;
import GUI.Helpers.Results.Container.ResultsContainer;
import GUI.Helpers.Results.Container.SimpleScrollableResultsContainer;
import GUI.PirateBay.PiratebaySection;
import Managers.Helpers.FilmAffinityBot;
import Managers.Helpers.HelperManager;
import Model.FichaPelicula;
import Model.HelperItem;

import javax.swing.border.LineBorder;

import java.awt.FlowLayout;

public class HelperResultsSection extends JPanel {
	
	private static final long serialVersionUID = -263118633110893005L;
	
	private JFrame mainFrame;
	private PiratebaySection piratebaySection;
	private JPanel rootPanel;
	private ResultsContainer resultsContainer;
	private HelperItem[] items;

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
				.addComponent(rootPanel, GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(rootPanel, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
		);
		FlowLayout fl_rootPanel = new FlowLayout(FlowLayout.CENTER, 0, 0);
		rootPanel.setLayout(fl_rootPanel);
		setLayout(groupLayout);
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
		this.items = items;
		if(rootPanel.getComponentCount() > 0){
			rootPanel.removeAll();
		}
		if(items.length == 1){
			System.out.println("multipart results container");
			resultsContainer = new MultipartScrollableResultsContainer(this.mainFrame, this, items[0], false);
		}else{
			System.out.println("simple results container");
			resultsContainer = new SimpleScrollableResultsContainer(this.mainFrame, this, items);
		}
		rootPanel.add(resultsContainer);
	}
	
	public void showResults(){
		showResults(this.items);
	}
	
	public void showItemDetails(HelperItem item){
		if(item == null){
			System.out.println("item es null");
			return;
		}
		else if(items.length == 0){
			System.out.println("no hay items");
			return;
		}
		if(rootPanel.getComponentCount() > 0){
			rootPanel.removeAll();
		}
		
		item = FilmAffinityBot.getInstance().fillFichaPelicula((FichaPelicula)item);
		System.out.println("multipart results container");
		resultsContainer = new MultipartScrollableResultsContainer(this.mainFrame, this, item, (items.length > 1));
		rootPanel.add(resultsContainer);
	}
}
