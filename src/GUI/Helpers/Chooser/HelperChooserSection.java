package GUI.Helpers.Chooser;

import javax.swing.JPanel;

import java.awt.SystemColor;

import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import GUI.LoadingView;
import Managers.Helpers.FilmAffinityBot;
import Managers.Helpers.HelperManager;
import Model.HelperItem;
import Utils.UtilTools;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HelperChooserSection extends JPanel implements Runnable {
	private static final long serialVersionUID = 9179013116749250575L;

	private HelperManager helperManager;
	private Map<String, String> filters;
	private HelperItem[] recommendations;
	
	private JFrame mainFrame;
	private String[] comboModel;
	private JComboBox helperChooserComboBox;
	
	private static String FILMS_COMBO_OPTION = "Películas";
	private static String MUSIC_COMBO_OPTION = "Música";
	private JButton getRecommendationsButton;
	private JButton setUpFiltersButton;
	private LoadingView loadingView;
	/**
	 * Create the panel.
	 */
	public HelperChooserSection(JFrame rootFrame) {
		this.mainFrame = rootFrame;
		this.filters = new HashMap<String, String>();
		//de momento por defecto se usa filmaffinity
		this.helperManager = FilmAffinityBot.getInstance();
		
		setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(64, 64, 64), null, null, null));
		setBackground(new Color(204, 255, 153));
		
		comboModel = new String[]{FILMS_COMBO_OPTION, MUSIC_COMBO_OPTION};
		helperChooserComboBox = new JComboBox(comboModel);
		helperChooserComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String option = comboModel[helperChooserComboBox.getSelectedIndex()];
				if(option.equals(FILMS_COMBO_OPTION)){
					helperManager = FilmAffinityBot.getInstance();
					setUpFiltersButton.setEnabled(true);
				}else{
					//helperManager = LastFMMAnager.getinstance();
					setUpFiltersButton.setEnabled(false);
				}
			}
		});
		
		getRecommendationsButton = new JButton("Ver Recomendaciones");
		getRecommendationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRecommendations();
			}
		});
		setUpFiltersButton = new JButton("Ajustar Filtros");
		setUpFiltersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFiltersSetUpView();
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addComponent(getRecommendationsButton, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
					.addComponent(setUpFiltersButton, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addGap(31))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(87, Short.MAX_VALUE)
					.addComponent(helperChooserComboBox, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
					.addGap(76))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(8)
					.addComponent(helperChooserComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(getRecommendationsButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(setUpFiltersButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		setLayout(groupLayout);

	}
	
	public Map<String, String> getFilters(){
		return filters;			
	}
	
	public void setFilters(Map<String, String> filters){
		this.filters = filters;
	}
	
	private void openFiltersSetUpView() {
		RecommendationsFiltersView recommView = new RecommendationsFiltersView(mainFrame, this);
		recommView.setVisible(true);
	}
	
	private void showRecommendations() {
		System.out.println("FILTERS: " + filters);
		if(!helperManager.isLogged()){
			new UtilTools().showWarningDialog(mainFrame, "", "No se ha iniciado sesión en " + helperManager.getHelperName());
			return;
		}
		
		Thread t = new Thread(this);
		t.start();
		showLoadingView();
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
		recommendations = helperManager.getRecommendations(filters);
		for(int i = 0; i < recommendations.length;i++){
			System.out.println(recommendations[i]);
		}
		hideLoadingView();
	}
}
