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

import GUI.Configuration.ConfigView;
import Managers.Helpers.FilmAffinityBot;
import Managers.Helpers.HelperManager;
import Model.HelperItem;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HelperChooserSection extends JPanel {
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
	/**
	 * Create the panel.
	 */
	public HelperChooserSection(JFrame parentFrame) {
		setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(64, 64, 64), null, null, null));
		this.mainFrame = parentFrame;
		this.filters = new HashMap<String, String>();
		setBackground(SystemColor.activeCaption);
		
		comboModel = new String[]{FILMS_COMBO_OPTION, MUSIC_COMBO_OPTION};
		helperChooserComboBox = new JComboBox(comboModel);
		helperChooserComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String option = comboModel[helperChooserComboBox.getSelectedIndex()];
				if(option.equals(FILMS_COMBO_OPTION)){
					helperManager = FilmAffinityBot.getInstance();
					setUpFiltersButton.setEnabled(false);
				}else{
					//helperManager = LastFMMAnager.getinstance();
					setUpFiltersButton.setEnabled(false);
				}
			}
		});
		
		getRecommendationsButton = new JButton("Ver Recomendaciones");
		getRecommendationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recommendations = helperManager.getRecommendations(filters);
				//send to results panel
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
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(31)
					.addComponent(getRecommendationsButton, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
					.addComponent(setUpFiltersButton, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addGap(27))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(105, Short.MAX_VALUE)
					.addComponent(helperChooserComboBox, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
					.addGap(77))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(helperChooserComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
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
		RecommendationsFieltersView recommView = new RecommendationsFieltersView(mainFrame, this);
		mainFrame.setEnabled(false);
		recommView.setVisible(true);
	}
}
