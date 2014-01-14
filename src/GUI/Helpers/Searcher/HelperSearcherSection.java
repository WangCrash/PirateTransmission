package GUI.Helpers.Searcher;

import javax.swing.JPanel;


import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import GUI.LoadingView;
import GUI.MainWindow;
import Managers.Helpers.FilmAffinityBot;
import Managers.Helpers.HelperManager;
import Model.HelperItem;
import Utils.UtilTools;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Font;

public class HelperSearcherSection extends JPanel implements Runnable {
	private static final long serialVersionUID = 9179013116749250575L;

	private HelperManager helperManager;
	private HelperItem[] results;
	
	private MainWindow mainFrame;
	
	private JButton configSearchButton;
	private LoadingView loadingView;
	private JTextField searchField;
	private JLabel titleLabel;
	/**
	 * Create the panel.
	 */
	public HelperSearcherSection() {
		//this.mainFrame = rootFrame;
		//de momento por defecto se usa filmaffinity
		this.helperManager = FilmAffinityBot.getInstance();
		
		setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(64, 64, 64), null, null, null));
		setBackground(new Color(204, 255, 153));
		
		configSearchButton = new JButton("");
		configSearchButton.setIcon(new ImageIcon(HelperSearcherSection.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		configSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		searchField = new JTextField();
		searchField.setMargin(new Insets(12, 12, 12, 12));
		searchField.setBorder(new LineBorder(new Color(171, 173, 179), 2, true));
		searchField.setColumns(10);
		
		titleLabel = new JLabel("New label");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(searchField)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(configSearchButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(8))
				.addComponent(titleLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(4)
					.addComponent(titleLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(configSearchButton, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
						.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
					.addContainerGap())
		);
		setLayout(groupLayout);

	}
	
	private void showLoadingView() {
		loadingView = new LoadingView(mainFrame);
		loadingView.setMessageLabel("Esperando...");
		loadingView.setVisible(true);
	}
	
	private void hideLoadingView(){
		loadingView.setVisible(false);
		loadingView = null;
	}

	@Override
	public void run() {
		//recommendations = helperManager.getRecommendations(filters);
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	mainFrame.getHelperResultsSection().showResults(results);
				hideLoadingView();
				//mainFrame.revalidate();
		    }
		});
	}
	public void setTitle(String title) {
		titleLabel.setText(title);
	}
}
