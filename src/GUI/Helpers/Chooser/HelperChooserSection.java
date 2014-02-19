package GUI.Helpers.Chooser;


import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import GUI.MainWindow;
import GUI.Panel.PanelProperties;
import GUI.Panel.SectionPanel;
import GUI.Utils.LoadingView;
import Managers.Helpers.FilmAffinityBot;
import Managers.Helpers.HelperManager;
import Managers.Helpers.LastFMManager;
import Model.HelperItem;
import Utils.UtilTools;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;


public class HelperChooserSection extends JPanel implements Runnable {
	private static final long serialVersionUID = 9179013116749250575L;

	private HelperManager helperManager;
	private Map<String, String> filters;
	private HelperItem[] recommendations;
	
	private MainWindow mainFrame;
	private String[] comboModel;
	private JComboBox helperChooserComboBox;
	
	public static String FILMS_COMBO_OPTION = "Películas";
	public static String MUSIC_COMBO_OPTION = "Música";
	private JButton getRecommendationsButton;
	private JButton setUpFiltersButton;
	private LoadingView loadingView;
	
	public HelperChooserSection(){
		this(null);
	}
	/**
	 * Create the panel.
	 */
	public HelperChooserSection(MainWindow rootFrame) {
		this.mainFrame = rootFrame;
		this.filters = new HashMap<String, String>();
		//de momento por defecto se usa filmaffinity
		this.helperManager = FilmAffinityBot.getInstance();
		
		setBorder(PanelProperties.BORDER);
		setBackground(PanelProperties.BACKGROUND);
		
		comboModel = new String[]{FILMS_COMBO_OPTION, MUSIC_COMBO_OPTION};
		helperChooserComboBox = new JComboBox(comboModel);
		helperChooserComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		helperChooserComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String option = comboModel[helperChooserComboBox.getSelectedIndex()];
				if(option.equals(FILMS_COMBO_OPTION)){
					helperManager = FilmAffinityBot.getInstance();
					setUpFiltersButton.setEnabled(true);
				}else{
					helperManager = LastFMManager.getInstance();
					setUpFiltersButton.setEnabled(false);
				}
				mainFrame.HelperManagerWasChanged(helperManager);
			}
		});
		
		getRecommendationsButton = new JButton("");
		getRecommendationsButton.setIcon(new ImageIcon(HelperChooserSection.class.getResource("/images/HelperChooser/ver-recomendaciones-icon.png")));
		getRecommendationsButton.setToolTipText("Ver Recomendaciones");
		getRecommendationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRecommendations();
			}
		});
		setUpFiltersButton = new JButton("");
		setUpFiltersButton.setForeground(new Color(0, 0, 0));
		setUpFiltersButton.setIcon(new ImageIcon(HelperChooserSection.class.getResource("/images/HelperChooser/ajuste-fitros-icon.png")));
		setUpFiltersButton.setToolTipText("Ajustar Filtros");
		setUpFiltersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFiltersSetUpView();
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(helperChooserComboBox, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(getRecommendationsButton, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addGap(138)))
					.addComponent(setUpFiltersButton, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addGap(7))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0, 0, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(setUpFiltersButton, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(38))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(helperChooserComboBox, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(getRecommendationsButton, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(1))))
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
	
	public void showRecommendations() {
		System.out.println("FILTERS: " + filters);
		if(!helperManager.isLogged()){
			new UtilTools().showWarningDialog(mainFrame, "", "No se ha iniciado sesión en " + helperManager.getHelperName());
			return;
		}
				
		Thread t = new Thread(this);
		t.start();
		showLoadingView();
		/*recommendations = helperManager.getRecommendations(filters);
		System.out.println("ESTAMOS EN EL EDT?: " + java.awt.EventQueue.isDispatchThread());
		mainFrame.getHelperResultsSection().showResults(recommendations);*/
	}
	
	private void showLoadingView() {
		loadingView = new LoadingView(mainFrame);
		loadingView.setMessageLabel("Esperando recomendaciones ...");
		loadingView.setVisible(true);
	}
	
	private void hideLoadingView(){
		loadingView.setVisible(false);
		loadingView = null;
	}

	@Override
	public void run() {
		recommendations = helperManager.getRecommendations(filters);
		System.out.println("Obtenidas recomendaciones para los filtros:\n" + filters);
		/*for(int i = 0; i < recommendations.length;i++){
			System.out.println(recommendations[i]);
		}*/
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	mainFrame.getHelperResultsSection().showResults(recommendations);
				hideLoadingView();
				//mainFrame.revalidate();
		    }
		});
	}
}
