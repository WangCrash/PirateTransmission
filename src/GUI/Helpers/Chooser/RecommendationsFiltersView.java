package GUI.Helpers.Chooser;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.naming.LimitExceededException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;

import Managers.Helpers.FilmAffinityBot;
import Utils.UtilTools;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;

public class RecommendationsFiltersView extends JDialog {

	private static final long serialVersionUID = 8898559080248268287L;
	
	private JFrame mainFrame;
	private HelperChooserSection parentView;
	private String[] genreComboModel;
	private String[] decadeComboModel;
	private String[] toComboModel;
	private String[] resultsLimitComboModel;
	Map<Integer, String> genreCorrespondency;
	
	private final JPanel contentPanel = new JPanel();
	private JComboBox genreComboBox;
	private JComboBox resultsLimitComboBox;
	private JComboBox decadeComboBox;
	private JComboBox toComboBox;

	/**
	 * Create the dialog.
	 */
	public RecommendationsFiltersView(JFrame rootFrame, HelperChooserSection parentView) {
		super(rootFrame, true);
		getContentPane().setForeground(Color.ORANGE);
		this.mainFrame = rootFrame;
		this.parentView = parentView;
		
		setAttributes();
		setUndecorated(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.background"));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel label = new JLabel("D\u00E9cada");
		
		decadeComboBox = new JComboBox(decadeComboModel);
		decadeComboBox.setSelectedIndex(0);
		
		JLabel label_1 = new JLabel("Hasta");
		
		toComboBox = new JComboBox(toComboModel);
		toComboBox.setSelectedIndex(0);
		
		JLabel label_2 = new JLabel("G\u00E9nero");
		
		genreComboBox = new JComboBox(genreComboModel);
		genreComboBox.setSelectedIndex(0);
		
		JLabel label_3 = new JLabel("Limitar el n\u00FAmero de resultados");
		
		resultsLimitComboBox = new JComboBox(resultsLimitComboModel);
		resultsLimitComboBox.setSelectedIndex(0);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(52)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(genreComboBox, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(decadeComboBox, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(toComboBox, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(resultsLimitComboBox, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(54, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(46)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(genreComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(30)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(2)
							.addComponent(decadeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(2)
							.addComponent(toComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(26)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(2)
							.addComponent(resultsLimitComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(56, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setUpFilters();
						close();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						close();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		initializeCombos();
		this.setLocationRelativeTo(this.mainFrame);
	}

	private void initializeCombos() {
		Map<String, String> filters = parentView.getFilters();
		String genre = filters.get(FilmAffinityBot.FILMAFFINITY_FILTERS_GENRE_KEY);
		int code = 0;
		int genreIndex = 0;
		if(genre != null){
			try{
				code = Integer.parseInt(genre);
				genreIndex = code;
				if(code != FilmAffinityBot.FILMAFFINITY_GENRE_KEY_ALL){
					genreIndex = findValue(genreComboModel, genreCorrespondency.get(code));
				}
			}catch(NumberFormatException e){
				genreIndex = 0;
			}
		}
		genreComboBox.setSelectedIndex(genreIndex);
		
		String decade = filters.get(FilmAffinityBot.FILMAFFINITY_FILTERS_FROM_YEAR_KEY);
		int decadeIndex = 0;
		if(decade != null){
			decadeIndex = findValue(decadeComboModel, decade);
		}
		decadeComboBox.setSelectedIndex(decadeIndex);
		
		String to = filters.get(FilmAffinityBot.FILMAFFINITY_FILTERS_TO_YEAR_KEY);
		int toIndex = 0;
		if(to != null){
			toIndex = findValue(toComboModel, to);
		}
		toComboBox.setSelectedIndex(toIndex);
		
		String limit = filters.get(FilmAffinityBot.FILMAFFINITY_FILTERS_LIMIT_KEY);
		int limitIndex = 0;
		if(limit != null){
			limitIndex = findValue(resultsLimitComboModel, limit);
		}
		resultsLimitComboBox.setSelectedIndex(limitIndex);
	}
	
	private int findValue(String[] model, String value){
		int index = 0;
		boolean found = false;
		while(index < model.length){
			if(value.equals(model[index])){
				found = true;
				break;
			}
			index++;
		}
		if(!found){
			index = 0;
		}
		return index;
	}
	
	private Integer findValue(Map<Integer, String> map, String value){
		Integer result = null;
		for (Map.Entry<Integer, String> entry : map.entrySet())
		{
			if(entry.getValue().equals(value)){
				result = entry.getKey();
				break;
			}
		}
		return result;
	}
	
	private void setUpFilters(){
		Map<String, String> filters = parentView.getFilters();
		
		int selectedGenre = (genreComboBox.getSelectedIndex() != -1)?genreComboBox.getSelectedIndex():0;
		Integer genre = findValue(genreCorrespondency, genreComboModel[selectedGenre]);
		if(genre == null){
			genre = FilmAffinityBot.FILMAFFINITY_GENRE_KEY_ALL;
		}
		filters.put(FilmAffinityBot.FILMAFFINITY_FILTERS_GENRE_KEY, genre.toString());
		
		int selectedDecade = (decadeComboBox.getSelectedIndex() != -1)?decadeComboBox.getSelectedIndex():0;
		String decade = decadeComboModel[selectedDecade];
		if(!decade.isEmpty()){
			filters.put(FilmAffinityBot.FILMAFFINITY_FILTERS_FROM_YEAR_KEY, decade);
		}
		
		int selectedTo = (toComboBox.getSelectedIndex() != -1)?toComboBox.getSelectedIndex():0;
		String to = toComboModel[selectedTo];
		if(!to.isEmpty()){
			filters.put(FilmAffinityBot.FILMAFFINITY_FILTERS_TO_YEAR_KEY, to);
		}
		
		int selectedLimit = (resultsLimitComboBox.getSelectedIndex() != -1)?resultsLimitComboBox.getSelectedIndex():0;
		String limit = resultsLimitComboModel[selectedLimit];
		if(!limit.isEmpty()){
			filters.put(FilmAffinityBot.FILMAFFINITY_FILTERS_LIMIT_KEY, limit);
		}
		parentView.setFilters(filters);
	}

	private void setAttributes() {
		genreComboModel = new String[]{
				"Todos los géneros"
				, "Acción"
				, "Animación"
				, "Aventuras"
				, "Bélico"
				, "Ciencia Ficción"
				, "Cine Negro"
				, "Comedia"
				, "Desconocido"
				, "Documental"
				, "Drama"
				, "Fantástico"
				, "Infantil"
				, "Intriga"
				, "Musical"
				, "Romance"
				, "Serie de TV"
				, "Terror"
				, "Thriller"
				, "Western"
		};
		genreCorrespondency = new HashMap<Integer, String>();
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_ACTION), genreComboModel[1]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_ANIMATION), genreComboModel[2]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_ADVENTURE), genreComboModel[3]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_WAR), genreComboModel[4]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_SCIENCE_FICTION), genreComboModel[5]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_NOIR), genreComboModel[6]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_COMEDY), genreComboModel[7]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_UNKNOWN), genreComboModel[8]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_DOCUMENTARY), genreComboModel[9]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_DRAMA), genreComboModel[10]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_FANTASTIC), genreComboModel[11]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_INFANTILE), genreComboModel[12]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_INTRIGUE), genreComboModel[13]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_MUSICAL), genreComboModel[14]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_ROMANCE), genreComboModel[15]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_TV_SERIE), genreComboModel[16]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_TERROR), genreComboModel[17]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_THRILLER), genreComboModel[18]);
		genreCorrespondency.put(new Integer(FilmAffinityBot.FILMAFFINITY_GENRE_KEY_WESTERN), genreComboModel[19]);
		
		decadeComboModel = new String[]{
				""
				, "1910"
				, "1920"
				, "1930"
				, "1940"
				, "1950"
				, "1960"
				, "1970"
				, "1980"
				, "1990"
				, "2000"
				, "2010"
		};
		toComboModel = new String[]{
				""
				, "1920"
				, "1930"
				, "1940"
				, "1950"
				, "1960"
				, "1970"
				, "1980"
				, "1990"
				, "2000"
				, "2010"
				, "2020"
		};
		resultsLimitComboModel = new String[]{
				"20"
				, "50"
		};
	}
	
	private void close() {
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
