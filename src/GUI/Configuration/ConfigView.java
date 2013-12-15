package GUI.Configuration;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import java.awt.Window.Type;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;


public class ConfigView extends JFrame {

	private JPanel contentPane;
	private JPanel selectedSectionPanel;
	private JTextField userField;
	private JPasswordField passwordField;

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
		setType(Type.UTILITY);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("Configuraci\u00F3n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 487, 339);
		setIconImage(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JList<String> sectionsList = new JList<String>();
		sectionsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(!arg0.getValueIsAdjusting()){
					JList<String> list = (JList<String>)arg0.getSource();
					drawSection(list.getSelectedIndex());
				}
			}
		});
		sectionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sectionsList.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		sectionsList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Transmission", "microTorrent", "FilmAffinity", "LastFM", "The Pirate Bay"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		setLocationRelativeTo(mainFrame);
		
		JLabel sectionsLabel = new JLabel("SECCIONES");
		sectionsLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		selectedSectionPanel = new JPanel();
		selectedSectionPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(sectionsList)
							.addGap(18)
							.addComponent(selectedSectionPanel, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
						.addComponent(sectionsLabel, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(sectionsLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(sectionsList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(selectedSectionPanel, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JLabel userLabel = new JLabel("User");
		
		userField = new JTextField();
		userField.setEnabled(false);
		userField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setEnabled(false);
		
		JLabel lblNewLabel = new JLabel("Password");
		
		JButton aceptarButton = new JButton("Aceptar");
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JCheckBox chckbxRequi = new JCheckBox("Requiere credenciales");
		GroupLayout gl_selectedSectionPanel = new GroupLayout(selectedSectionPanel);
		gl_selectedSectionPanel.setHorizontalGroup(
			gl_selectedSectionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_selectedSectionPanel.createSequentialGroup()
					.addContainerGap(23, Short.MAX_VALUE)
					.addGroup(gl_selectedSectionPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_selectedSectionPanel.createSequentialGroup()
							.addComponent(chckbxRequi)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_selectedSectionPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(Alignment.TRAILING, gl_selectedSectionPanel.createSequentialGroup()
								.addGroup(gl_selectedSectionPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
									.addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_selectedSectionPanel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(passwordField, Alignment.LEADING)
									.addComponent(userField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
								.addContainerGap(50, Short.MAX_VALUE))
							.addGroup(Alignment.TRAILING, gl_selectedSectionPanel.createSequentialGroup()
								.addComponent(aceptarButton)
								.addGap(18)
								.addComponent(btnCancelar)
								.addGap(78)))))
		);
		gl_selectedSectionPanel.setVerticalGroup(
			gl_selectedSectionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selectedSectionPanel.createSequentialGroup()
					.addGap(18)
					.addComponent(chckbxRequi)
					.addGap(18)
					.addGroup(gl_selectedSectionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(userField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(userLabel))
					.addGap(38)
					.addGroup(gl_selectedSectionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
					.addGroup(gl_selectedSectionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancelar)
						.addComponent(aceptarButton))
					.addContainerGap())
		);
		selectedSectionPanel.setLayout(gl_selectedSectionPanel);
		contentPane.setLayout(gl_contentPane);
		drawSection(0);
	}
	private void drawSection(int selectedSection) {
		this.selectedSectionPanel.removeAll();
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
}
