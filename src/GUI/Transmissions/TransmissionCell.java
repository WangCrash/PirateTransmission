package GUI.Transmissions;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import Model.Transmission;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public abstract class TransmissionCell extends JPanel {
	protected JFrame mainFrame;
	protected TransmissionsView parentView;
	protected Transmission transmission;
	
	private JLabel ratingImageLabel;
	private JButton customButton;
	private JLabel itemImageLabel;
	private JLabel dateLabel;
	private JButton deleteButton;
	private JLabel itemTypeLabel;
	private JLabel titleLabel;

	/**
	 * Create the panel.
	 */
	public TransmissionCell(JFrame mainFrame, TransmissionsView parentView, Transmission transmission) {
		this.mainFrame = mainFrame;
		this.parentView = parentView;
		this.transmission = transmission;
		
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		itemTypeLabel = new JLabel("Tipo de Item");
		itemTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		itemTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		titleLabel = new JLabel("T\u00EDtulo");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		itemImageLabel = new JLabel("");
		itemImageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		itemImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		itemImageLabel.setIcon(new ImageIcon(TransmissionCell.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		
		deleteButton = new JButton("Borrar");
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteTransmission();
			}
		});
		
		customButton = new JButton("lo que sea");
		customButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		customButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rateItem();
			}
		});
		
		ratingImageLabel = new JLabel("");
		ratingImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ratingImageLabel.setIcon(new ImageIcon(TransmissionCell.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		ratingImageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblAadido = new JLabel("A\u00F1adido");
		lblAadido.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		dateLabel = new JLabel("Fecha");
		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(itemTypeLabel, GroupLayout.PREFERRED_SIZE, 411, GroupLayout.PREFERRED_SIZE)
						.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 411, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(itemImageLabel, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblAadido, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addGap(31)
									.addComponent(dateLabel, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(24)
									.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
									.addGap(20)
									.addComponent(customButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
							.addGap(33)
							.addComponent(ratingImageLabel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(itemTypeLabel)
					.addGap(6)
					.addComponent(titleLabel)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(itemImageLabel, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(14)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblAadido)
								.addComponent(dateLabel))
							.addGap(34)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(deleteButton)
								.addComponent(customButton)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(14)
							.addComponent(ratingImageLabel)))
					.addGap(11))
		);
		setLayout(groupLayout);

	}
	protected JLabel getRatingImageLabel() {
		return ratingImageLabel;
	}
	protected JButton getCustomButton() {
		return customButton;
	}
	protected JLabel getItemImageLabel() {
		return itemImageLabel;
	}
	protected JLabel getDateLabel() {
		return dateLabel;
	}
	protected JButton getDeleteButton() {
		return deleteButton;
	}
	protected JLabel getItemTypeLabel() {
		return itemTypeLabel;
	}
	protected JLabel getTitleLabel() {
		return titleLabel;
	}
	
	private void deleteTransmission() {
		parentView.deleteTransmission(transmission);
	}
	
	protected abstract void initLabels();
	
	protected abstract void rateItem();
}
