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
import GUI.PirateBay.PiratebaySection;
import Managers.ApplicationConfiguration;
import Managers.PirateBayBot;
import Managers.Helpers.FilmAffinityBot;
import Utils.UtilTools;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -6836764944504173037L;
	private JPanel contentPane;
	private JPanel piratebayPanel;
	private JPanel helperRecommendations;
	private JPanel helperSearcher;
	private JPanel helperResults;
	
	private PiratebaySection pirateBaySection;
	private HelperChooserSection helperChooserSection;
	private HelperResultsSection helperResultsSection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
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
	public MainWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(FilmAffinityBot.getInstance().isLogged()){
					FilmAffinityBot.getInstance().terminateManager();
				}
			}
		});
		
		ApplicationConfiguration.getInstance().initManager();
		setIconImage(Toolkit.getDefaultToolkit().getImage(PirateTransmissionDemo.class.getResource("/images/Transmission-icon.png")));
		//sustituir por un objeto helpermanager que apunte al helper guardado en la configuración
		if(!FilmAffinityBot.getInstance().initManager()){
			new UtilTools().showWarningDialog(this, "Error", "No se ha podido iniciar sesión en " + FilmAffinityBot.getInstance().getHelperName());
		}
		if(!ApplicationConfiguration.getInstance().getDefaultTorrentClient().initManager()){
			String clientName = ApplicationConfiguration.getInstance().getDefaultTorrentClient().getTorrentClientName();
			new UtilTools().showWarningDialog(this, "Error", "No se ha podido conectar con " + clientName);
		}
		if(!PirateBayBot.getInstance().initManager()){
			new UtilTools().showWarningDialog(this, "Error", "No se ha podido conectar con PirateBay. Es posible que haya cambiado la dirección de su servidor");
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/Transmission-icon.png")));
		setTitle("Pirate Transmission");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 918, 599);
		
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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		piratebayPanel = new JPanel();
		
		JPanel helperPanel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(piratebayPanel, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(helperPanel, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(piratebayPanel, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
				.addComponent(helperPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		
		helperRecommendations = new JPanel();
		
		helperSearcher = new JPanel();
		
		helperResults = new JPanel();
		GroupLayout gl_helperPanel = new GroupLayout(helperPanel);
		gl_helperPanel.setHorizontalGroup(
			gl_helperPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_helperPanel.createSequentialGroup()
					.addGroup(gl_helperPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(helperRecommendations, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
						.addComponent(helperResults, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
						.addComponent(helperSearcher, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_helperPanel.setVerticalGroup(
			gl_helperPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_helperPanel.createSequentialGroup()
					.addComponent(helperRecommendations, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(helperSearcher, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(helperResults, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
		);
		helperPanel.setLayout(gl_helperPanel);
		contentPane.setLayout(gl_contentPane);
		includeSections();
	}

	private void includeSections() {
		pirateBaySection = new PiratebaySection(this);
		piratebayPanel.add(pirateBaySection);
		
		helperChooserSection = new HelperChooserSection(this);
		helperRecommendations.add(helperChooserSection);
		
		helperResultsSection = new HelperResultsSection(this, pirateBaySection);
		helperResults.add(helperResultsSection);
	}
	
	private void openConfigView() {
		ConfigView configView = new ConfigView(this);
		configView.setVisible(true);
	}
	
	public void searchTorrent(String search){
		pirateBaySection.searchTorrent(search);
	}
	
	public HelperResultsSection getHelperResultsSection(){
		return this.helperResultsSection;
	}
}
