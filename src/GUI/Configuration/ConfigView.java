package GUI.Configuration;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;

import Utils.UtilTools;

import java.util.Map;
import javax.swing.LayoutStyle.ComponentPlacement;


public class ConfigView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7399904300753044367L;
	private JPanel contentPane;
	private JTabbedPane sectionsPane;
	private ConfigurationSection[] sections;
	private Map<String, String> configProperties;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 486, 446);
		setIconImage(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		sectionsPane = new JTabbedPane(JTabbedPane.TOP);
		
		TransmissionConfigView transmissionSection = new TransmissionConfigView();
		sectionsPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		sectionsPane.addTab("General",	new JPanel());
		sectionsPane.addTab("Transmission", transmissionSection);
		sectionsPane.addTab("microTorrent", new JPanel());
		sectionsPane.addTab("FilmAffinity", new JPanel());
		sectionsPane.addTab("LastFM", new JPanel());
		
		sections = new ConfigurationSection[]{new TransmissionConfigView(), transmissionSection, new TransmissionConfigView(), new TransmissionConfigView()};
		sectionsPane.setSelectedIndex(0);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UtilTools tools = new UtilTools();
				configProperties = tools.getConfiguration();
				for (int i = 0; i < sectionsPane.getTabCount(); i++) {
					if(sectionsPane.getTitleAt(i).equals("Transmission")){
						saveChangedValues(sections[i].getChangedValues());
					}
				}
				tools.setConfiguration(configProperties);
				close();
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
					.addComponent(btnNewButton)
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
						.addComponent(btnNewButton))
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
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void saveChangedValues(Map<String, String> changedValues){
		for (Map.Entry<String, String> entry : changedValues.entrySet())
		{
			configProperties.put(entry.getKey(), entry.getValue());
		    System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
}
