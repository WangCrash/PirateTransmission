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
import Managers.ApplicationConfiguration;
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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class HelperSearcherSection extends JPanel implements Runnable {
	private static final long serialVersionUID = 9179013116749250575L;

	private HelperItem[] results;
	private int searchOption;
	
	private MainWindow mainFrame;
	
	private JButton configSearchButton;
	private LoadingView loadingView;
	private JTextField searchField;
	private JLabel titleLabel;
	
	public HelperSearcherSection(){
		this(null);
	}
	
	/**
	 * Create the panel.
	 */
	public HelperSearcherSection(MainWindow mainFrame) {
		this.mainFrame = mainFrame;		
		
		setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(64, 64, 64), null, null, null));
		setBackground(new Color(204, 255, 153));
		
		configSearchButton = new JButton("");
		configSearchButton.setIcon(new ImageIcon(HelperSearcherSection.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		configSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showConfigSearchView();
			}
		});
		
		titleLabel = new JLabel("New label");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		searchField = new JTextField();
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchItem();
			}
		});
		searchField.setMargin(new Insets(12, 12, 12, 12));
		searchField.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		searchField.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 462, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(searchField, GroupLayout.PREFERRED_SIZE, 418, GroupLayout.PREFERRED_SIZE)
							.addGap(9)
							.addComponent(configSearchButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
					.addGap(11))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(3)
					.addComponent(titleLabel)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
							.addGap(10))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(configSearchButton, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
							.addContainerGap())))
		);
		setLayout(groupLayout);
		
		drawOptionsByHelper();
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
		results = ApplicationConfiguration.getInstance().getCurrentHelperManager().searchItem(searchField.getText().trim(), searchOption);
		System.out.println(results);
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	mainFrame.getHelperResultsSection().showResults(results);
				hideLoadingView();
				//mainFrame.revalidate();
		    }
		});
	}
	
	private String getOptionName(int option){
		String result = "";
		for (Map.Entry<String, Integer> entry : ApplicationConfiguration.getInstance().getCurrentHelperManager().getSearchOptions().entrySet()){
			if(entry.getValue() == option){
				result = entry.getKey();
			}
		}
		return result;
	}
	
	private void searchItem(){
		HelperManager helperManager = ApplicationConfiguration.getInstance().getCurrentHelperManager();
		if(!helperManager.isLogged()){
			new UtilTools().showWarningDialog(mainFrame, "", "No se ha iniciado sesión en " + helperManager.getHelperName());
			return;
		}
		Thread t = new Thread(this);
		t.start();
		showLoadingView();
	}
	
	public void searchItem(String search, int option) {
		setSearchOption(option);
		searchField.setText(search.trim());
		searchItem();
	}
	
	private void showConfigSearchView() {
		HelperManager helper = ApplicationConfiguration.getInstance().getCurrentHelperManager();
		SearchOptionsView searchOptionsView = new SearchOptionsView(mainFrame, this, "Opciones de búsqueda de película", helper.getSearchOptions(), searchOption);
		searchOptionsView.setVisible(true);
	}
	
	public void drawOptionsByHelper(){
		setSearchOption(ApplicationConfiguration.getInstance().getCurrentHelperManager().getDefaultSearchOption());
		setTitle();
	}
	
	public void setTitle() {
		String title;
		if(ApplicationConfiguration.getInstance().getCurrentHelperManager().getHelperName().equals(HelperManager.HELPERMANAGER_NAME_FILMS)){
			title = "Búsqueda de películas";
		}else{
			title = "Búsqueda de música";
		}
		titleLabel.setText(title + " por " + getOptionName(searchOption));
	}

	public int getSearchOption(){
		return searchOption;
	}

	public void setSearchOption(int searchOption) {
		this.searchOption = searchOption;
		setTitle();
	}
	public JButton getConfigSearchButton() {
		return configSearchButton;
	}
}
