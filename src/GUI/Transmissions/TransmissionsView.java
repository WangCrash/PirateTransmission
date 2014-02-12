package GUI.Transmissions;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import Managers.Persistent.FilmTransmissionCell;
import Managers.Persistent.PersistentDataManager;
import Model.FichaPelicula;
import Model.HelperItem;
import Model.Transmission;
import Utils.UtilTools;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class TransmissionsView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel cellsPanel;
	private JFrame mainFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TransmissionsView dialog = new TransmissionsView(new JFrame());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TransmissionsView(JFrame mainFrame) {
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
		contentPanel.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		panel.add(scrollPane);
		
		cellsPanel = new JPanel();
		cellsPanel.setBackground(new Color(204, 255, 153));
		scrollPane.setViewportView(cellsPanel);
		cellsPanel.setLayout(new GridLayout(0, 1, 0, 40));
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
		Transmission[] transmissions = PersistentDataManager.getInstance().listPersistentObjects();
		if(transmissions == null){
			new UtilTools().showWarningDialog(mainFrame, "Error", "No se ha podido recuperar la lista de transmissions");
		}else if(transmissions.length == 0){
			new UtilTools().showInfoOKDialog(mainFrame, "", "No hay transmissions");
		}else{
			for (int i = 0; i < transmissions.length; i++) {
				HelperItem item = transmissions[i].getHelperItem();
				if(item.getClass() == FichaPelicula.class){
					cellsPanel.add(new FilmTransmissionCell(mainFrame, this, transmissions[i]));
				}
			}
			
		}
		this.revalidate();
		//controlar la posición del scroll vertical
	}
	
	public void deleteTransmission(Transmission transmission){
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
