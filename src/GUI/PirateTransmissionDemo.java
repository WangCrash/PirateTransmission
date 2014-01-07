package GUI;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import GUI.Configuration.ConfigView;
import GUI.PirateBay.TorrentCell;
import Managers.ApplicationConfiguration;
import Managers.PirateBayBot;
import Managers.Helpers.FilmAffinityBot;
import Model.ArchivoTorrent;
import Utils.UtilTools;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.SystemColor;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class PirateTransmissionDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3411288730125732006L;

	private ArchivoTorrent[] torrents;
	
	private JPanel contentPane;
	private JTextField searchField;
	private JPanel resultsPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PirateTransmissionDemo frame = new PirateTransmissionDemo();
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
	public PirateTransmissionDemo() {
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
		setResizable(false);
		setTitle("PirateTransmissionDemo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 368);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnOpciones = new JMenu("Opciones");
		menuBar.add(mnOpciones);
		
		JMenuItem mntmConfiguracin = new JMenuItem("Configuraci\u00F3n");
		mntmConfiguracin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openConfigView();
			}
		});
		mnOpciones.add(mntmConfiguracin);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		searchPanel.setBackground(SystemColor.activeCaption);
		searchPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(searchPanel, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(searchPanel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
		);
		
		JLabel searchLabel = new JLabel("Buscar: ");
		searchLabel.setBackground(Color.LIGHT_GRAY);
		searchLabel.setBounds(30, 26, 52, 16);
		searchPanel.add(searchLabel);
		
		searchField = new JTextField();
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField textField = (JTextField)e.getSource();
				textField.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				requestSearch(textField.getText().trim());
			}
		});
		searchField.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		searchField.setBounds(133, 20, 312, 28);
		searchPanel.add(searchField);
		searchField.setColumns(10);
		
		resultsPanel = new JPanel();
		resultsPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, null, null, null));
		resultsPanel.setBackground(SystemColor.activeCaption);
		scrollPane.setViewportView(resultsPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		resultsPanel.setLayout(new GridLayout(0, 1, 0, 2));
		contentPane.setLayout(gl_contentPane);
	}
	
	private void requestSearch(String search){
		this.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		torrents = PirateBayBot.getInstance().searchTorrent(search, PirateBayBot.CATEGORY_ALL, PirateBayBot.ORDERBY_SEEDERS);
		if(torrents == null){
			new UtilTools().showWarningDialog(this, "Error", "Puede que no estés conectado");
			setDefaultCursor();
			return;
		}
		drawTorrentsList();
	}
	
	private void drawTorrentsList(){
		if(torrents.length == 0){
			new UtilTools().showInfoOKDialog(this, "", "No hay resultados");
		}else{
			if(resultsPanel.getComponentCount() > 0){
				resultsPanel.removeAll();
			}
			for(int i = 0; i < torrents.length; i++){
				TorrentCell cell = new TorrentCell(torrents[i]);
				cell.setMainFrame(this);
				if(i % 2 == 0){
					cell.setBackground(new Color(196.0f/255.0f, 196.0f/255.0f, 236.0f/255.0f));
				}else{
					cell.setBackground(new Color(176.0f/255.0f, 196.0f/255.0f, 222.0f/255.0f));//light steel blue
				}
				resultsPanel.add(cell);
			}
			resultsPanel.revalidate();
		}

		setDefaultCursor();
	}
	
	private void openConfigView() {
		// TODO Auto-generated method stub
		ConfigView configView = new ConfigView(this);
		configView.setVisible(true);
	}
	protected JPanel getResultsPanel() {
		return resultsPanel;
	}
	private void setDefaultCursor(){
		searchField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		this.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
}
