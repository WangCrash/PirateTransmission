package GUI;

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
import GUI.PirateBay.PiratebaySection;
import GUI.Transmisiones.TransmisionesView;
import Managers.ApplicationConfiguration;
import Managers.Helpers.FilmAffinityBot;
import Managers.Helpers.HelperManager;
import Managers.Helpers.LastFMManager;
import Managers.Persistent.PersistentDataManager;
import Model.FichaPelicula;
import Model.HelperItem;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -6836764944504173037L;
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
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new PreApp().initializingApplication();
					MainWindow frame = MainWindow.getInstance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private MainWindow() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(FilmAffinityBot.getInstance().isLogged()){
					FilmAffinityBot.getInstance().terminateManager();
				}
				if(LastFMManager.getInstance().isLogged()){
					LastFMManager.getInstance().logout();
				}
				if(PersistentDataManager.getInstance().isStarted()){
					PersistentDataManager.getInstance().finalizeManager();
				}
			}
		});
	
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/Transmission-icon.png")));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/Transmission-icon.png")));
		setTitle("Pirate Transmission");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 963, 733);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAplicacin = new JMenu("Aplicaci\u00F3n");
		menuBar.add(mnAplicacin);
		
		JMenuItem mntmConfiguracin = new JMenuItem("Configuraci\u00F3n");
		mntmConfiguracin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openConfigView();
			}
		});
		mnAplicacin.add(mntmConfiguracin);
		
		mnTransmissions = new JMenu("Transmisiones");
		menuBar.add(mnTransmissions);
		
		mntmVerTransmissions = new JMenuItem("Ver Transmisiones");
		mntmVerTransmissions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openTransmissionsView();
			}
		});
		mnTransmissions.add(mntmVerTransmissions);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		piratebayPanel = new JPanel();
		
		JPanel helperPanel = new JPanel();
		
		helperRecommendations = new JPanel();
		
		helperSearcher = new JPanel();
		
		helperResults = new JPanel();
		GroupLayout gl_helperPanel = new GroupLayout(helperPanel);
		gl_helperPanel.setHorizontalGroup(
			gl_helperPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_helperPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(helperSearcher, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(6))
				.addComponent(helperRecommendations, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
				.addGroup(gl_helperPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(helperResults, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_helperPanel.setVerticalGroup(
			gl_helperPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_helperPanel.createSequentialGroup()
					.addComponent(helperRecommendations, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(helperSearcher, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(helperResults, GroupLayout.PREFERRED_SIZE, 481, GroupLayout.PREFERRED_SIZE)
					.addGap(1))
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
	}

	private void includeSections() {
		pirateBaySection = new PiratebaySection(this);
		piratebayPanel.add(pirateBaySection);
		
		helperChooserSection = new HelperChooserSection(this);
		helperRecommendations.add(helperChooserSection);
		
		helperSearcherSection = new HelperSearcherSection(this);
		helperSearcher.add(helperSearcherSection);
		
		helperResultsSection = new HelperResultsSection(this);
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
}
