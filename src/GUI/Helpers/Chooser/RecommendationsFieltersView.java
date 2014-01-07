package GUI.Helpers.Chooser;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
import java.util.Map;

public class RecommendationsFieltersView extends JDialog {

	private static final long serialVersionUID = 8898559080248268287L;
	
	private JFrame mainFrame;
	private HelperChooserSection parentView;
	private String[] genreComboModel;
	private int[] genreCodes;
	private String[] decadeComboModel;
	private String[] toComboModel;
	private String[] resultsLimitComboModel;
	
	private final JPanel contentPanel = new JPanel();
	private JLabel lblGnero;
	private JComboBox resultsLimitComboBox;
	private JComboBox genreComboBox;
	private JComboBox decadeComboBox;
	private JComboBox toComboBox;

	/**
	 * Create the dialog.
	 */
	public RecommendationsFieltersView(JFrame mainFrame, HelperChooserSection parentView) {
		super(mainFrame);
		this.mainFrame = mainFrame;
		this.parentView = parentView;
		
		setAttributes();
		setUndecorated(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblGnero = new JLabel("G\u00E9nero");
		}
		
		JLabel lblDesde = new JLabel("D\u00E9cada");
		
		decadeComboBox = new JComboBox(decadeComboModel);
		
		JLabel lblHasta = new JLabel("Hasta");
		
		JLabel lblLimitarElNmero = new JLabel("Limitar el n\u00FAmero de resultados");
		
		toComboBox = new JComboBox(toComboModel);
		
		resultsLimitComboBox = new JComboBox(resultsLimitComboModel);
		
		genreComboBox = new JComboBox(genreComboModel);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(61)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblDesde, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(decadeComboBox, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblHasta, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(toComboBox, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblGnero, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(genreComboBox, 0, 259, Short.MAX_VALUE))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblLimitarElNmero, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(resultsLimitComboBox, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))))
					.addGap(61))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(57)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblGnero, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(genreComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(30)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDesde, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHasta, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(toComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(decadeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLimitarElNmero, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(resultsLimitComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(69, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
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
	}

	private void initializeCombos() {
		Map<String, String> filters = parentView.getFilters();
		String genre = filters.get(FilmAffinityBot.FILMAFFINITY_FILTERS_GENRE_KEY);
		int code = 0;
		if(genre != null){
			try{
				code = Integer.parseInt(genre);
			}catch(NumberFormatException e){
				code = 0;
			}
		}
		genreComboBox.setSelectedIndex(code);
		
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
		decadeComboBox.setSelectedIndex(toIndex);
		
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
	
	private void setUpFilters(){
		Map<String, String> filters = parentView.getFilters();
		
		String genre = String.valueOf(genreComboBox.getSelectedIndex() - 1);
		filters.put(FilmAffinityBot.FILMAFFINITY_FILTERS_GENRE_KEY, genre);
		
		String decade = decadeComboModel[decadeComboBox.getSelectedIndex()];
		if(!decade.isEmpty()){
			filters.put(FilmAffinityBot.FILMAFFINITY_FILTERS_FROM_YEAR_KEY, decade);
		}
		
		String to = toComboModel[toComboBox.getSelectedIndex()];
		if(!to.isEmpty()){
			filters.put(FilmAffinityBot.FILMAFFINITY_FILTERS_TO_YEAR_KEY, to);
		}
		
		String limit = resultsLimitComboModel[resultsLimitComboBox.getSelectedIndex()];
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
				, "Thriller"
				, "Western"
		};
		genreCodes = new int[]{
				FilmAffinityBot.FILMAFFINITY_GENRE_KEY_ALL
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_ACTION
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_ANIMATION
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_ADVENTURE
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_WAR
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_SCIENCE_FICTION
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_NOIR
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_COMEDY
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_UNKNOWN
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_DOCUMENTARY
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_DRAMA
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_FANTASTIC
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_INFANTILE
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_INTRIGUE
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_MUSICAL
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_ROMANCE
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_TV_SERIE
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_THRILLER
				, FilmAffinityBot.FILMAFFINITY_GENRE_KEY_WESTERN
		};
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
		mainFrame.setEnabled(true);
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
