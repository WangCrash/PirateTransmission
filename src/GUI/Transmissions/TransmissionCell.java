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
import Utils.UtilTools;

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
	private JLabel customFieldLabel1;
	private JLabel customTagLabel2;
	private JLabel customTagLabel1;
	private JLabel customFieldLabel2;

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
		itemTypeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
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
		
		dateLabel = new JLabel("Fecha");
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		customTagLabel1 = new JLabel("New label");
		customTagLabel1.setVisible(false);
		
		customTagLabel2 = new JLabel("New label");
		customTagLabel2.setVisible(false);
		
		customFieldLabel1 = new JLabel("New label");
		customFieldLabel1.setVisible(false);
		
		customFieldLabel2 = new JLabel("New label");
		customFieldLabel2.setVisible(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(itemTypeLabel, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
							.addGap(75)
							.addComponent(dateLabel, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
						.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 441, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(itemImageLabel, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(customTagLabel1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(customFieldLabel1, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(customTagLabel2, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(customFieldLabel2, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(53)
									.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(customButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
							.addComponent(ratingImageLabel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
					.addGap(9))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(itemTypeLabel)
						.addComponent(dateLabel))
					.addGap(13)
					.addComponent(titleLabel)
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(itemImageLabel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(customTagLabel1)
								.addComponent(customFieldLabel1))
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(customTagLabel2)
								.addComponent(customFieldLabel2))
							.addGap(12)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(deleteButton)
								.addComponent(customButton)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(45)
							.addComponent(ratingImageLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
	
	protected void setAppropiateRatingImage() {
		ImageIcon ratingImage;
		if(transmission.getRated()){
			ratingImage = new ImageIcon(getClass().getResource("/images/transmission-rated.png"));
		}else{
			ratingImage = new ImageIcon(getClass().getResource("/images/transmission-not-rated.png"));
		}
		ratingImageLabel.setIcon(new UtilTools().getScaledImage(ratingImage.getImage(), 40, 40));
	}
	protected JLabel getCustomFieldLabel1() {
		return customFieldLabel1;
	}
	protected JLabel getCustomTagLabel2() {
		return customTagLabel2;
	}
	protected JLabel getCustomTagLabel1() {
		return customTagLabel1;
	}
	protected JLabel getCustomFieldLabel2() {
		return customFieldLabel2;
	}
}
