package GUI.Helpers.Results;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import GUI.Helpers.Results.Items.FilmCell;
import Model.HelperItem;

import java.awt.GridLayout;


public class SimpleScrollableResultsContainer extends ResultsContainer{

	private static final long serialVersionUID = 8817672622988670420L;
	
	private HelperResultsSection parentView;
	
	private JPanel contentPanel;

	/**
	 * Create the panel.
	 */
	public SimpleScrollableResultsContainer(JFrame mainFrame, HelperResultsSection parentView, HelperItem[] items) {
		super(mainFrame, items);
		this.parentView = parentView;
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
		);
		
		contentPanel = new JPanel();
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		contentPanel.setBackground(new Color(204, 255, 153));
		scrollPane.setViewportView(contentPanel);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		setLayout(groupLayout);
		
		showResults();
	}

	@Override
	protected void showResults() {
		if(items == null){
			System.out.println("Items vacío");
			return;
		}
		for (int i = 0; i < items.length; i++) {
			//FilmCell cell = new FilmCell(mainFrame, parentView, items[i], true);
			JButton button = new JButton("BOTÓN");
			contentPanel.add(button);
			System.out.println("celda añadida");
		}
		contentPanel.revalidate();
	}
	
	public JPanel getContentPanel() {
		return contentPanel;
	}
}
