package GUI.Helpers.Results.Items.Films.ReviewsTable;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Toolkit;

public class ReviewDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JLabel authorLabel;
	private JTextPane reviewTextPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ReviewDialog dialog = new ReviewDialog(null, "", "");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ReviewDialog(JFrame mainFrame, String author, String review) {
		setUndecorated(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ReviewDialog.class.getResource("/images/Transmission-icon.png")));
		setBounds(100, 100, 450, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		
		authorLabel = new JLabel("New label");
		authorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(authorLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(21)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(10)
					.addComponent(authorLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		
		reviewTextPane = new JTextPane();
		scrollPane.setViewportView(reviewTextPane);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		setLocationRelativeTo(mainFrame);
		initLabels(author, review);
	}
	private void initLabels(String author, String review) {
		authorLabel.setText(author);
		reviewTextPane.setText(review);
	}

	public JLabel getAuthorLabel() {
		return authorLabel;
	}
	public JTextPane getReviewTextPane() {
		return reviewTextPane;
	}
}
