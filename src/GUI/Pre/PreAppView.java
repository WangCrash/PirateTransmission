package GUI.Pre;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;

import GUI.MainWindow;
import GUI.OneArgumentRunnableObject;

import java.awt.Font;

@SuppressWarnings("serial")
public class PreAppView extends JDialog {

	private JPanel contentPane;
	private JLabel messageLabel;

	/**
	 * Launch the application.
	 */
	public static void main(MainWindow mainFrame) {
		EventQueue.invokeLater(new OneArgumentRunnableObject(mainFrame) {
			
			@Override
			public void run() {
				try {
					PreAppView preView = new PreAppView((MainWindow) this.getArgument());
					preView.setVisible(true);
					preView.initializeApplication();
					preView.setVisible(false);
					preView.dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PreAppView(JFrame mainFrame) {
		super(mainFrame, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setResizable(false);
		setBounds(100, 100, 485, 315);
		contentPane = new JPanel();
		contentPane.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), UIManager.getBorder("Button.border")));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(PreAppView.class.getResource("/images/Transmission-icon.png")));
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(PreAppView.class.getResource("/images/app-title.png")));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		messageLabel = new JLabel("");
		messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 463, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(11)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(126)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(4)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addGap(17)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addComponent(messageLabel)
					.addGap(4))
		);
		contentPane.setLayout(gl_contentPane);
		//this.setLocationRelativeTo(mainFrame);
		System.out.println("Preview Constructor finished");
	}
	public JLabel getMessageLabel() {
		return messageLabel;
	}
	
	public void initializeApplication(){
		PreApp pre = new PreApp(this);
		pre.initializingApplication();
	}
}
