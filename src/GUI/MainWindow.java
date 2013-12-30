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

import GUI.PirateBay.PiratebaySection;
import Managers.FilmAffinityBot;
import Managers.PirateBayBot;
import Utils.UtilTools;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -6836764944504173037L;
	private JPanel contentPane;
	protected JPanel piratebayPanel;

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
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(helperPanel, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(piratebayPanel, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
				.addComponent(helperPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
		);
		GroupLayout gl_helperPanel = new GroupLayout(helperPanel);
		gl_helperPanel.setHorizontalGroup(
			gl_helperPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 442, Short.MAX_VALUE)
		);
		gl_helperPanel.setVerticalGroup(
			gl_helperPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 551, Short.MAX_VALUE)
		);
		helperPanel.setLayout(gl_helperPanel);
		contentPane.setLayout(gl_contentPane);
		includeSections();
	}

	private void includeSections() {
		piratebayPanel.add(new PiratebaySection(this));
	}
}
