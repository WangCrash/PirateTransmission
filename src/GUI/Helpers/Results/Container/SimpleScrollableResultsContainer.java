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
import GUI.Helpers.Results.Items.Films.FilmCell;
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
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		resultsPanel = new JPanel();
		resultsPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		resultsPanel.setBackground(new Color(204, 255, 153));
		scrollPane.setViewportView(resultsPanel);
		resultsPanel.setLayout(new GridLayout(0, 1, 1, 2));
		setLayout(groupLayout);
		
		//testing();
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
			FilmCell cell = new FilmCell(mainFrame, parentView, items[i], true);
			resultsPanel.add(cell);
			System.out.println("celda a�adida");
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
