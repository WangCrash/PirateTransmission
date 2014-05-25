package GUI.Transmisiones;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.LayerUI;

import GUI.Helpers.Searcher.SearcherView;
import GUI.Panel.PanelProperties;
import GUI.Panel.SimpleContentPanel;
import GUI.Panel.SimpleWallpaperLayerUI;
import GUI.SearchOptions.SearchOptionsView;
import Managers.Persistent.PersistentDataManager;
import Managers.TorrentClient.TransmissionManager;
import Model.Artista;
import Model.Disco;
import Model.FichaPelicula;
import Model.HelperItem;
import Model.Transmision;
import Utils.UtilTools;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class TransmisionesView extends JDialog implements SearcherView{
	
	private static final int Transmision_All = 1;
	private static final int Transmision_Films = 2;
	private static final int Transmision_Music = 3;

	//private final JPanel contentPanel = new JPanel();
	private final JPanel contentPanel = new SimpleContentPanel(532, 403);
	private JPanel cellsPanel;
	private JFrame mainFrame;
	private JScrollPane scrollPane;
	private JButton configQueryButton;
	private int selectedTransmissionType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TransmisionesView dialog = new TransmisionesView(new JFrame());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.testBehavior();
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TransmisionesView(JFrame mainFrame) {
		super(mainFrame, true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					close();
				}
			}
		});
		setResizable(false);
		this.mainFrame = mainFrame;
		setBounds(100, 100, 532, 448);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		LayerUI<Container> layerUI = new SimpleWallpaperLayerUI(532, 403);
		//JLayer<Container> contentPanelLayer = new JLayer<Container>(contentPanel, layerUI);
		JLayer<Container> contentPaneLayer = new JLayer<Container>();
		
		configQueryButton = new JButton();
		configQueryButton.setText("Tipo");
		configQueryButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent clickEvent) {
				Map<String, Integer> options = new HashMap<String, Integer>();
				options.put("Todo", 1);
				options.put("Películas", 2);
				options.put("Música", 3);
				SearchOptionsView searchOptionsView = new SearchOptionsView(TransmisionesView.this.mainFrame
																			, TransmisionesView.this
																			, "filtrar por tipo de transmisión"
																			, options
																			, selectedTransmissionType);
				searchOptionsView.setVisible(true);
			}
		});
		getContentPane().add(configQueryButton, BorderLayout.NORTH);
		
		//getContentPane().add(contentPanelLayer, BorderLayout.CENTER);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();		
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.BLACK, null, null, null));
		panel.setBackground(PanelProperties.TRANSPARENT_BACKGROUND);
		panel.setBorder(PanelProperties.BORDER);
		
		contentPanel.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBackground(PanelProperties.TRANSPARENT_BACKGROUND);
		
		panel.add(scrollPane);
		
		cellsPanel = new JPanel();
		cellsPanel.setBackground(PanelProperties.TRANSPARENT_BACKGROUND);
		scrollPane.setViewportView(cellsPanel);
		cellsPanel.setLayout(new GridLayout(0, 1, 0, 5));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(240, 240, 240), 4));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						close();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		selectedTransmissionType = Transmision_All;
		
		showPersistentTransmissions();
		
		this.setLocationRelativeTo(this.mainFrame);
	}
	
	private void showPersistentTransmissions(){
		if(cellsPanel.getComponentCount() > 0){
			cellsPanel.removeAll();
		}
		Transmision[] transmissions = PersistentDataManager.getInstance().listPersistentObjects(translateTypeToPersistentManager(selectedTransmissionType));
		if(transmissions == null){
			new UtilTools().showWarningDialog(mainFrame, "Error", "No se ha podido recuperar la lista de transmisiones");
		}else if(transmissions.length > 0){
			for (int i = 0; i < transmissions.length; i++) {
				HelperItem item = transmissions[i].getHelperItem();
				if(item.getClass() == FichaPelicula.class){
					cellsPanel.add(new FilmTransmisionCell(mainFrame, this, transmissions[i]));
				}else if(item.getClass() == Artista.class){
					cellsPanel.add(new ArtistTransmisionCell(mainFrame, this, transmissions[i]));
				}else if(item.getClass() == Disco.class){
					cellsPanel.add(new AlbumTransmisionCell(mainFrame, this, transmissions[i]));
				}
			}
		}else{
			scrollPane.remove(cellsPanel);
			scrollPane.setViewportView(cellsPanel);
		}
		cellsPanel.revalidate();
		//controlar la posición del scroll vertical
	}

	public void deleteTransmission(Transmision transmission){
		if(!PersistentDataManager.getInstance().deleteTransmission(transmission)){
			new UtilTools().showWarningDialog(mainFrame, "Error", "La base de datos no responde");
		}
		
		showPersistentTransmissions();
	}
	
	private void close() {
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void testBehavior(){
		PersistentDataManager.getInstance().initManager();
		showPersistentTransmissions();
		PersistentDataManager.getInstance().finalizeManager();
	}

	@Override
	public JButton getConfigSearchButton() {
		return configQueryButton;
	}

	@Override
	public void setSearchOption(int searchOption) {
		selectedTransmissionType = searchOption;
		
		switch (searchOption) {
		case Transmision_All:	
			configQueryButton.setText("Todo");
			break;
		case Transmision_Films:
			configQueryButton.setText("Películas");
			break;
		case Transmision_Music:
			configQueryButton.setText("Música");
		default:
			break;
		}
		
		showPersistentTransmissions();
	}
	
	public int translateTypeToPersistentManager(int type){
		switch (type) {
		case Transmision_All:	
			return PersistentDataManager.CATEGORY_ALL; 
		case Transmision_Films:
			return PersistentDataManager.CATEGORY_FILMS;
		case Transmision_Music:
			return PersistentDataManager.CATEGORY_MUSIC;
		default:
			return -1;
		}
	}
}
