package GUI.Transmisiones;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import GUI.Panel.PanelProperties;
import GUI.Panel.SimpleContentPanel;
import Managers.Persistent.PersistentDataManager;
import Model.Artista;
import Model.Disco;
import Model.FichaPelicula;
import Model.HelperItem;
import Model.Transmision;
import Utils.UtilTools;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class TransmisionesView extends JDialog {

	private final JPanel contentPanel = new SimpleContentPanel();
	private JPanel cellsPanel;
	private JFrame mainFrame;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TransmisionesView dialog = new TransmisionesView(new JFrame());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
		setResizable(false);
		this.mainFrame = mainFrame;
		setBounds(100, 100, 532, 403);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.BLACK, null, null, null));
		panel.setBackground(PanelProperties.BACKGROUND);
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
		
		showPersistentTransmissions();
		
		this.setLocationRelativeTo(this.mainFrame);
	}
	
	private void showPersistentTransmissions(){
		if(cellsPanel.getComponentCount() > 0){
			cellsPanel.removeAll();
		}
		Transmision[] transmissions = PersistentDataManager.getInstance().listPersistentObjects();
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
		this.revalidate();
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
}
