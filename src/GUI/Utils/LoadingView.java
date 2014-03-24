package GUI.Utils;
import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.SwingConstants;

import GUI.Panel.PanelProperties;

public class LoadingView extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8259210410679000211L;
	private final JPanel contentPanel = new JPanel();
	private JLabel messageLabel;


	/**
	 * Create the dialog.
	 */
	public LoadingView(JFrame mainFrame) {
		super(mainFrame, true);
		setType(Type.POPUP);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setResizable(false);
		setOpacity(0.9f);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoadingView.class.getResource("/images/Transmission-icon.png")));
		setModal(true);
		setBounds(100, 100, 321, 202);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		//contentPanel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 1, true), new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(180, 180, 180), new Color(180, 180, 180))));

		contentPanel.setBackground(new Color(211, 211, 211));
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			messageLabel = new JLabel("New label");
			messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(81)
							.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(messageLabel, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(67)
					.addComponent(messageLabel)
					.addGap(18)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(83, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		this.setLocationRelativeTo(mainFrame);
	}
	public String getMessageLabel() {
		return messageLabel.getText();
	}	
	
	public void setMessageLabel(String message){
		messageLabel.setText(message);
	}
}
