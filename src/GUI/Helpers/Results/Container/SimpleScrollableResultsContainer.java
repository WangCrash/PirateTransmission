package GUI.Helpers.Results.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingUtilities;

import java.awt.Color;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import GUI.Helpers.Results.HelperResultsSection;
import GUI.Helpers.Results.Items.HelperResultItem;
import GUI.Helpers.Results.Items.Films.FilmCell;
import GUI.Helpers.Results.Items.Films.FilmDetailsView;
import Model.Artista;
import Model.Disco;
import Model.FichaPelicula;
import Model.HelperItem;

import java.awt.GridLayout;

import javax.swing.ScrollPaneConstants;


public class SimpleScrollableResultsContainer extends ResultsContainer{

	private static final long serialVersionUID = 8817672622988670420L;
	
	private JPanel resultsPanel;

	/**
	 * Create the panel.
	 */
	public SimpleScrollableResultsContainer(JFrame mainFrame, HelperResultsSection parentView, HelperItem[] items) {
		super(mainFrame, items);
		this.parentView = parentView;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//scrollPane.;
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
		);
		
		resultsPanel = new JPanel();
		resultsPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		resultsPanel.setBackground(new Color(204, 255, 153));
		scrollPane.setViewportView(resultsPanel);
		resultsPanel.setLayout(new GridLayout(0, 1, 1, 10));
		setLayout(groupLayout);
		
		//testing();
		showResults();
	}

	@Override
	protected void showResults() {
		System.out.println("VA a mostrar");
		if(items == null){
			System.out.println("Items vacío");
			return;
		}
		for (int i = 0; i < items.length; i++) {
			HelperResultItem cell;
			if(items[i].getClass() == FichaPelicula.class){
				cell = new FilmCell(mainFrame, parentView, items[i], true);
			}else{
				if(items[i].getClass() == Artista.class){
					cell = null;//new ArtistCell()
				}else if(items[i].getClass() == Disco.class){
					cell = null;//new AlbumCell()
				}else{
					cell = null;
				}
			}
			if(cell != null){
				resultsPanel.add(cell);
				System.out.println("celda añadida");
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
}
