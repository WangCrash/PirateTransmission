package GUI.Helpers.Results;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import GUI.MainWindow;
import GUI.Helpers.Results.Container.MultipartScrollableResultsContainer;
import GUI.Helpers.Results.Container.ResultsContainer;
import GUI.Helpers.Results.Container.SimpleScrollableResultsContainer;
import GUI.Panel.PanelProperties;
import Managers.Helpers.FilmAffinityBot;
import Model.FichaPelicula;
import Model.HelperItem;
import Utils.UtilTools;

import java.awt.FlowLayout;

public class HelperResultsSection extends JPanel {
	
	private static final long serialVersionUID = -263118633110893005L;
	
	private MainWindow mainFrame;
	private JPanel rootPanel;
	private ResultsContainer resultsContainer;
	private HelperItem[] items;
	private int resultsScrollValue;

	public HelperResultsSection(){
		this(null);
	}
	/**
	 * Create the panel.
	 */
	public HelperResultsSection(MainWindow rootFrame) {
		this.mainFrame = rootFrame;
		
		setBackground(PanelProperties.TRANSPARENT_BACKGROUND);
		setBorder(PanelProperties.BORDER);		
		setOpaque(false);
		
		rootPanel = new JPanel();
		rootPanel.setOpaque(false);
		rootPanel.setBackground(PanelProperties.TRANSPARENT_BACKGROUND);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(rootPanel, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(rootPanel, GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
		);
		FlowLayout fl_rootPanel = new FlowLayout(FlowLayout.CENTER, 0, 0);
		rootPanel.setLayout(fl_rootPanel);
		setLayout(groupLayout);
	}

	public void searchTorrent(String search, int category, HelperItem item){
		this.mainFrame.searchTorrent(search, category, item);
	}
	
	public void showResults(HelperItem[] items){
		if(items == null || items.length == 0){
			new UtilTools().showInfoOKDialog(mainFrame, "", "No hay resultados");
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
		if(resultsScrollValue != 0){
			System.out.println("setting scroll position to " + resultsScrollValue);
			resultsContainer.setScrollPosition(resultsScrollValue);
		}
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
		resultsScrollValue = resultsContainer.getScrollValue();
		if(rootPanel.getComponentCount() > 0){
			rootPanel.removeAll();
		}
		if(item.getClass() == FichaPelicula.class){
			item = FilmAffinityBot.getInstance().fillFichaPelicula((FichaPelicula)item);
		}
		resultsContainer = new MultipartScrollableResultsContainer(this.mainFrame, this, item, (items.length > 1));
		rootPanel.add(resultsContainer);
	}
	
	public void searchItem(String search, int option){
		mainFrame.searchItem(search, option);
	}
}
