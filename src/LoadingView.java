import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


public class LoadingView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5741217996927322720L;
	private JProgressBar progressBar;
	private JLabel messageLabel;

	/**
	 * Create the panel.
	 */
	public LoadingView() {
		
		progressBar = new JProgressBar();
		
		messageLabel = new JLabel("Texto");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(31)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addComponent(messageLabel)
					.addGap(11)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		setLayout(groupLayout);

	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}
	public JLabel getMessageLabel() {
		return messageLabel;
	}
}
