package GUI.Helpers.Results;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingUtilities;

import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import GUI.Helpers.Results.Items.FilmCell;
import Model.HelperItem;

import java.awt.GridLayout;
import javax.swing.ScrollPaneConstants;


public class SimpleScrollableResultsContainer extends ResultsContainer{

	private static final long serialVersionUID = 8817672622988670420L;
	
	private HelperResultsSection parentView;
	
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
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
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

	private void testing() {
		for(int i = 0; i < 10; i++){
			JLabel label = new JLabel("LABEL " + i);
			resultsPanel.add(label);
		}
	}

	@Override
	protected void showResults() {
		if(items == null){
			System.out.println("Items vacío");
			return;
		}
		for (int i = 0; i < items.length; i++) {
			FilmCell cell = new FilmCell(mainFrame, parentView, items[i], true);
			resultsPanel.add(cell);
			System.out.println("celda añadida");
		}
	}
	
	public JPanel getContentPanel() {
		return resultsPanel;
	}
}
