package GUI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import GUI.Configuration.ConfigView;
import GUI.Helpers.Chooser.HelperChooserSection;
import GUI.Helpers.Results.HelperResultsSection;
import GUI.Helpers.Searcher.HelperSearcherSection;
import GUI.Panel.PanelProperties;
import GUI.Panel.SimpleContentPanel;
import GUI.PirateBay.PiratebaySection;
import GUI.Transmisiones.TransmisionesView;
import GUI.Utils.LoadingView;
import Managers.ApplicationConfiguration;
import Managers.Helpers.FilmAffinityBot;
import Managers.Helpers.HelperManager;
import Managers.Helpers.LastFMManager;
import Managers.Persistent.PersistentDataManager;
import Model.FichaPelicula;
import Model.HelperItem;
import Utils.OneArgumentRunnableObject;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.WindowFocusListener;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private static volatile MainWindow instance = null;
	
	private JPanel contentPane;
	private JPanel piratebayPanel;
	private JPanel helperRecommendations;
	private JPanel helperSearcher;
	private JPanel helperResults;
	
	private PiratebaySection pirateBaySection;
	private HelperChooserSection helperChooserSection;
	private HelperSearcherSection helperSearcherSection;
	private HelperResultsSection helperResultsSection;
	private JMenu mnTransmissions;
	private JMenuItem mntmVerTransmissions;
	
	public static MainWindow getInstance(){
		synchronized (FilmAffinityBot.class) {
			if(instance == null){
				instance = new MainWindow();
			}
		}
		return instance;
	}

	/**
	 * Launch the application.
	 */
	private static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					MainWindow mainFrame = new MainWindow();//MainWindow.getInstance();					
					mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void startApplication(){
		main(null);
	}

	/**
	 * Create the frame.
	 */
	private MainWindow() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				closeApplication();
			}
		});
	
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/Transmission-icon.png")));
		setTitle("Pirate Transmission");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 963, 733);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu settingsMenu = new JMenu("Preferencias");
		menuBar.add(settingsMenu);
		
		JMenuItem mntmConfiguracin = new JMenuItem("Configuraci\u00F3n");
		mntmConfiguracin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openConfigView();
			}
		});
		settingsMenu.add(mntmConfiguracin);
		
		mnTransmissions = new JMenu("Transmisiones");
		menuBar.add(mnTransmissions);
		
		mntmVerTransmissions = new JMenuItem("Ver Transmisiones");
		mntmVerTransmissions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openTransmissionsView();
			}
		});
		mnTransmissions.add(mntmVerTransmissions);
		contentPane = new SimpleContentPanel(963, 733);
		//contentPane.setBackground(new Color(153, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		piratebayPanel = new JPanel();
		piratebayPanel.setBackground(new Color(153, 0, 204, 0));
		
		JPanel helperPanel = new JPanel();
		helperPanel.setBackground(new Color(204, 204, 51, 0));
		
		helperRecommendations = new JPanel();
		helperRecommendations.setBackground(new Color(255, 255, 204, 0));
		
		helperSearcher = new JPanel();
		helperSearcher.setBackground(new Color(0, 51, 255, 0));
		
		helperResults = new JPanel();
		helperResults.setBackground(PanelProperties.TRANSPARENT_BACKGROUND);
		GroupLayout gl_helperPanel = new GroupLayout(helperPanel);
		gl_helperPanel.setHorizontalGroup(
			gl_helperPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_helperPanel.createSequentialGroup()
					.addGroup(gl_helperPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(helperResults, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(helperRecommendations, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(helperSearcher, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
					.addGap(6))
		);
		gl_helperPanel.setVerticalGroup(
			gl_helperPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_helperPanel.createSequentialGroup()
					.addComponent(helperRecommendations, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(helperSearcher, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(helperResults, GroupLayout.PREFERRED_SIZE, 481, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		helperPanel.setLayout(gl_helperPanel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(piratebayPanel, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(helperPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(piratebayPanel, GroupLayout.PREFERRED_SIZE, 675, GroupLayout.PREFERRED_SIZE)
						.addComponent(helperPanel, GroupLayout.PREFERRED_SIZE, 673, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		includeSections();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}

	private void includeSections() {
		pirateBaySection = new PiratebaySection(this);
		pirateBaySection.setBackground(new Color(51, 204, 102, 0));
		piratebayPanel.add(pirateBaySection);
		
		helperChooserSection = new HelperChooserSection(this);
		helperRecommendations.add(helperChooserSection);
		
		helperSearcherSection = new HelperSearcherSection(this);
		helperSearcher.add(helperSearcherSection);
		
		helperResultsSection = new HelperResultsSection(this);
		helperResultsSection.setOpaque(false);
		helperResults.add(helperResultsSection);
		
		helperChooserSection.showRecommendations();
	}
	
	private void openConfigView() {
		ConfigView configView = new ConfigView(this);
		configView.setVisible(true);
	}
	
	private void openTransmissionsView() {
		TransmisionesView transmissionsView = new TransmisionesView(this);
		transmissionsView.setVisible(true);
	}
	
	public void searchTorrent(String search, HelperItem item){
		Thread searchTorrentThread = new Thread(new OneArgumentRunnableObject(search) {
			
			@Override
			public void run() {
				pirateBaySection.searchTorrent((String)this.getArgument());
			}
		});
		searchTorrentThread.start();
		
		Thread makeItemPersistentThread = new Thread(new OneArgumentRunnableObject(item) {
			
			@Override
			public void run() {
				HelperItem item = (HelperItem)this.getArgument();
				if(item.getClass() == FichaPelicula.class){
					FichaPelicula film = (FichaPelicula)item;
					if(film.getDataUcd() == null || film.getDataUcd().isEmpty()){
						film = FilmAffinityBot.getInstance().fillFichaPelicula(film);
						item = film;
					}
				}
				PersistentDataManager.getInstance().addTransmission(item);
			}
		});
		makeItemPersistentThread.start();
	}
	
	public void searchItem(String search, int option){
		helperSearcherSection.searchItem(search, option);
	}
	
	public HelperResultsSection getHelperResultsSection(){
		return this.helperResultsSection;
	}
	
	public void HelperManagerWasChanged(HelperManager helper){
		ApplicationConfiguration.getInstance().setCurrentHelperManager(helper);
		this.helperSearcherSection.drawOptionsByHelper();
		helperChooserSection.showRecommendations();
	}
	
	public Color color(double val) {
	    double H = val * 0.3; 
	    double S = 0.9; 
	    double B = 0.9; 
	    int rgb = Color.HSBtoRGB((float)H, (float)S, (float)B);
	    int red = (rgb >> 16) & 0xFF;
	    int green = (rgb >> 8) & 0xFF;
	    int blue = rgb & 0xFF;
	    Color color = new Color(red, green, blue, 0x33);
	    return color;
	}

	private void closeApplication() {
		LoadingView loadingView = new LoadingView(this);
		loadingView.setMessageLabel("Cerrando...");
		Thread closingManagersThread = new Thread(new OneArgumentRunnableObject(loadingView) {
			
			@Override
			public void run() {
				LoadingView loadingView = (LoadingView)this.getArgument();
				if(FilmAffinityBot.getInstance().isLogged()){
					loadingView.setMessageLabel("Cerrando sesión en FilmAffinity...");
					FilmAffinityBot.getInstance().terminateManager();
				}
				
				if(LastFMManager.getInstance().isLogged()){
					loadingView.setMessageLabel("Cerrando sesión en LastFM...");
					LastFMManager.getInstance().logout();
				}
				if(PersistentDataManager.getInstance().isStarted()){
					loadingView.setMessageLabel("Cerrando controlador de persistencia...");
					PersistentDataManager.getInstance().finalizeManager();
				}
				loadingView.setVisible(false);
				loadingView.dispose();
			}
		});
		closingManagersThread.start();
		loadingView.setVisible(true);
	}
}
