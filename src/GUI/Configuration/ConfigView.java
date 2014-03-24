package GUI.Configuration;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;

import GUI.Panel.PanelProperties;
import Managers.ApplicationConfiguration;
import Managers.Helpers.FilmAffinityBot;
import Managers.Helpers.LastFMManager;
import Managers.TorrentClient.TransmissionManager;
import Managers.TorrentClient.microTorrentManager;
import Utils.UtilTools;

import java.util.Map;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


@SuppressWarnings("serial")
public class ConfigView extends JDialog {

	/**
	 * 
	 */
	private JPanel contentPane;
	private JTabbedPane sectionsPane;
	private ConfigurationSection[] sections;
	private Map<String, String> configProperties;
	private JFrame mainFrame;
	private JButton AcceptButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigView frame = new ConfigView(null);
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
	public ConfigView(JFrame mainFrame) {
		super(mainFrame, true);
		this.mainFrame = mainFrame;
		
		setType(Type.UTILITY);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("Configuraci\u00F3n");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 486, 446);
		setIconImage(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		UIManager.put("TabbedPane.contentOpaque", false);
		sectionsPane = new JTabbedPane(JTabbedPane.TOP);
		sectionsPane.setBackground(PanelProperties.BACKGROUND);
		sectionsPane.setBorder(PanelProperties.BORDER);
		sectionsPane.setOpaque(false);
		Component[] sectionsPaneComponents = sectionsPane.getComponents();
		for(int i = 0; i < sectionsPaneComponents.length; i++){
			sectionsPaneComponents[i].setBackground(PanelProperties.BACKGROUND);
		}
			
		GeneralSectionConfig generalSection = new GeneralSectionConfig(this, ApplicationConfiguration.getInstance());
				
		TorrentClientSectionConfig transmissionSection = new TorrentClientSectionConfig(this, TransmissionManager.getInstance());
		
		TorrentClientSectionConfig microTorrentSection = new TorrentClientSectionConfig(this, microTorrentManager.getInstance());
		
		UtilTools utils = new UtilTools();
		Map<String, String> config = utils.getConfiguration();
		String filmAffinityUser = "";
		String filmAffinityPassword = "";
		if(config != null){
			filmAffinityUser = config.get(FilmAffinityBot.FILMAFFINITY_USER_AUTH_CONFIG_KEY);
			filmAffinityPassword = config.get(FilmAffinityBot.FILMAFFINITY_PASSWORD_AUTH_CONFIG_KEY);
		}
		SimpleSectionConfig filmAffinitySection = new SimpleSectionConfig(this, filmAffinityUser, filmAffinityPassword);
		filmAffinitySection.setManager(FilmAffinityBot.getInstance());
		
		String lastFMUser = "";
		String lastFMPassword = "";
		if(config != null){
			lastFMUser = config.get(LastFMManager.LASTFM_USER_AUTH_CONFIG_KEY);
			lastFMPassword = config.get(LastFMManager.LASTFM_PASSWORD_AUTH_CONFIG_KEY);
		}
		SimpleSectionConfig lastFMSection = new SimpleSectionConfig(this, lastFMUser, lastFMPassword);
		lastFMSection.setManager(LastFMManager.getInstance());
		
		sectionsPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		sectionsPane.addTab("General",	generalSection);
		sectionsPane.addTab("Transmission", transmissionSection);
		sectionsPane.addTab("microTorrent", microTorrentSection);
		sectionsPane.addTab("FilmAffinity", filmAffinitySection);
		sectionsPane.addTab("LastFM", lastFMSection);
		sectionsPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println("KEY_TYPED: " + e.getKeyChar());
				ConfigView.this.keyReleased(e);
			}
		});
		
		sections = new ConfigurationSection[]{generalSection, transmissionSection, microTorrentSection, filmAffinitySection, lastFMSection};
		sectionsPane.setSelectedIndex(0);
		
		AcceptButton = new JButton("Aceptar");
		AcceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkConfigSectionsAndSave();
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(143)
					.addComponent(AcceptButton)
					.addGap(44)
					.addComponent(btnCancelar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(153))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(sectionsPane, GroupLayout.PREFERRED_SIZE, 462, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(24, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(sectionsPane, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancelar)
						.addComponent(AcceptButton))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(mainFrame);
		drawSection(0);
	}
	private void drawSection(int selectedSection) {
		switch (selectedSection) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;

		default:
			break;
		}
		System.out.println("Sección: " + selectedSection);
	}
	
	private void close() {
		this.setVisible(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	private boolean validateSections(){
		int i = 0;
		boolean result = true;
		while(i < sections.length){
			if(!sections[i].isValidPassLength()){
				new UtilTools().showWarningDialog(mainFrame, "Error de configuración", "La contraseña de " + sectionsPane.getTitleAt(i) + " no tiene 4 caracteres o más");
				sectionsPane.setSelectedIndex(i);
				result = false;
				break;
			}
			i++;
		}
		return result;
	}
	
	//si cambia para una sección alguna de las properties avisa de ello
	private boolean setChangedValues(Map<String, String> changedValues){
		boolean result = false;
		for (Map.Entry<String, String> entry : changedValues.entrySet())
		{
			configProperties.put(entry.getKey(), entry.getValue());
			if(!result){
				result = true;
			}
		}
		return result;
	}
	
	private void checkConfigSectionsAndSave() {
		if(!validateSections()){
			return;
		}
		UtilTools tools = new UtilTools();
		configProperties = tools.getConfiguration();
		boolean[] needsToReboot = new boolean[sectionsPane.getTabCount()];
		for (int i = 0; i < sectionsPane.getTabCount(); i++) {
			System.out.println(sectionsPane.getTitleAt(i));
			System.out.println(sections[i].getChangedValues());
			needsToReboot[i] = setChangedValues(sections[i].getChangedValues());
		}
		tools.setConfiguration(configProperties);
		//reiniciar managers cuya configuración haya cambiado
		String message = "";
		for (int j = 0; j < needsToReboot.length; j++) {
			System.out.print(sectionsPane.getTitleAt(j) + " " + needsToReboot[j]);
			if(!needsToReboot[j]){
				continue;
			}
			if(!sections[j].getManager().initManager()){
				message += "\t" + sectionsPane.getTitleAt(j) + "\n";
			}
		}
		if(!message.isEmpty()){
			tools.showWarningDialog(this, "Error", "No se ha podido conectar con:\n" + message);
		}else{
			close();
		}
	}
	
	public void keyReleased(KeyEvent keyEvent) {
		if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
			checkConfigSectionsAndSave();
		}else if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE){
			close();
		}
	}
}
