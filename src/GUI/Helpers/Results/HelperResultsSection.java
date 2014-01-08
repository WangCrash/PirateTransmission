package GUI.Helpers.Results;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;

import java.awt.Color;

import javax.swing.ScrollPaneConstants;

import Model.HelperItem;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.CardLayout;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

public class HelperResultsSection extends JPanel {
	
	private static final long serialVersionUID = -263118633110893005L;
	
	private JFrame mainFrame;
	HelperItem[] results;

	/**
	 * Create the panel.
	 */
	public HelperResultsSection(JFrame rootFrame) {
		setBorder(new LineBorder(new Color(64, 64, 64)));
		this.mainFrame = rootFrame;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 255, 153));
		scrollPane.setViewportView(panel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
		);
		setLayout(groupLayout);

	}
}
