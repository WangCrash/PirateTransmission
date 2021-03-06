package GUI.Helpers.Results.Container;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import GUI.MainWindow;
import GUI.Helpers.Results.HelperResultsSection;
import GUI.Helpers.Results.Items.HelperResultItem;
import GUI.Helpers.Results.Items.Films.FilmCell;
import GUI.Helpers.Results.Items.Music.AlbumCell;
import GUI.Helpers.Results.Items.Music.ArtistCell;
import GUI.Panel.PanelProperties;
import Model.Artista;
import Model.Disco;
import Model.FichaPelicula;
import Model.HelperItem;

import java.awt.GridLayout;

import javax.swing.ScrollPaneConstants;


public class SimpleScrollableResultsContainer extends ResultsContainer{

	private static final long serialVersionUID = 8817672622988670420L;
	
	private JPanel resultsPanel;
	private JScrollPane scrollPane;

	public SimpleScrollableResultsContainer(){
		this(null, null, null);
	}
	/**
	 * Create the panel.
	 */
	public SimpleScrollableResultsContainer(MainWindow mainFrame, HelperResultsSection parentView, HelperItem[] items) {
		super(mainFrame, items);
		this.parentView = parentView;
		
		setOpaque(false);
		setBackground(PanelProperties.TRANSPARENT_BACKGROUND);
		
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//scrollPane.setBackground(PanelProperties.TRANSPARENT_BACKGROUND);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 485, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 460, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		resultsPanel = new JPanel();
		resultsPanel.setOpaque(false);
		resultsPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		resultsPanel.setBackground(PanelProperties.TRANSPARENT_BACKGROUND);
		scrollPane.setViewportView(resultsPanel);
		resultsPanel.setLayout(new GridLayout(0, 1, 1, 10));
		setLayout(groupLayout);
		
		showResults();
	}

	@Override
	protected void showResults() {
		System.out.println("VA a mostrar");
		if(items == null){
			System.out.println("Items vac�o");
			return;
		}
		for (int i = 0; i < items.length; i++) {
			HelperResultItem cell;
			if(items[i].getClass() == FichaPelicula.class){
				cell = new FilmCell(mainFrame, parentView, items[i]);
			}else{
				if(items[i].getClass() == Artista.class){
					cell = new ArtistCell(mainFrame, parentView, items[i]);
				}else if(items[i].getClass() == Disco.class){
					cell = new AlbumCell(mainFrame, parentView, items[i]);
				}else{
					cell = null;
				}
			}
			if(cell != null){
				resultsPanel.add(cell);
				System.out.println("celda a�adida");
			}
		}
		resultsPanel.revalidate();
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				mainFrame.revalidate();
			}
		});
	}
	
	public JPanel getContentPanel() {
		return resultsPanel;
	}

	@Override
	public void setScrollPosition(int verticalValue) {
		Rectangle visible = scrollPane.getViewport().getVisibleRect();
		scrollPane.getViewport().scrollRectToVisible(new Rectangle(visible.x, verticalValue, visible.width, visible.height));
	}

	@Override
	public int getScrollValue() {
		return scrollPane.getVerticalScrollBar().getValue();
	}
}
