package GUI.Helpers.Results.Items.Films.ReviewsTable;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;

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
import javax.swing.SwingUtilities;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class ReviewDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JLabel authorLabel;
	private JTextPane reviewTextPane;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			String feotuchini = "";
			for (int i = 0; i < 30; i++) {
				feotuchini += "asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf\n";
			}
			ReviewDialog dialog = new ReviewDialog(null, "", feotuchini);
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
		super(mainFrame, true);
		setUndecorated(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ReviewDialog.class.getResource("/images/Transmission-icon.png")));
		setBounds(100, 100, 450, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();
		
		authorLabel = new JLabel("New label");
		authorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(authorLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE)
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
		reviewTextPane.setContentType("text/html");
		reviewTextPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		scrollPane.setViewportView(reviewTextPane);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JButton okButton = new JButton("OK");
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
		setLocationRelativeTo(mainFrame);
		initLabels(author, review);
	}
	private void initLabels(String author, String review) {
		authorLabel.setText(author);
		reviewTextPane.setText(review);
		reviewTextPane.setEditable(false);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Point p = scrollPane.getViewport().getViewPosition();
				p.y = 0;
				scrollPane.getViewport().setViewPosition(p);
			}
		});
	}

	private void close() {
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
