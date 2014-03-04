package GUI.Transmisiones;

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

import GUI.Panel.PanelProperties;
import Managers.Persistent.PersistentDataManager;
import Model.Transmision;
import Utils.OneArgumentRunnableObject;
import Utils.UtilTools;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Component;

@SuppressWarnings("serial")
public abstract class TransmisionCell extends JPanel {
	protected JFrame mainFrame;
	protected TransmisionesView parentView;
	protected Transmision transmission;
	
	private JLabel ratingImageLabel;
	private JButton customButton;
	private JLabel itemImageLabel;
	private JLabel dateLabel;
	private JButton deleteButton;
	private JLabel itemTypeLabel;
	private JLabel titleLabel;
	private JLabel customFieldLabel1;
	private JLabel customTagLabel1;

	
	public TransmisionCell(){
		this(new JFrame(), new TransmisionesView(new JFrame()), new Transmision("name", new Date()));
	}
	/**
	 * Create the panel.
	 */
	public TransmisionCell(JFrame mainFrame, TransmisionesView parentView, Transmision transmission) {
		this.mainFrame = mainFrame;
		this.parentView = parentView;
		this.transmission = transmission;
		
//		setMaximumSize(new Dimension(457, 180));
//		setMinimumSize(new Dimension(457, 180));
		setBackground(PanelProperties.BACKGROUND);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		itemTypeLabel = new JLabel("Tipo de Item");
		itemTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		itemTypeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		titleLabel = new JLabel("T\u00EDtulo");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		itemImageLabel = new JLabel("");
		itemImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		itemImageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		itemImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		itemImageLabel.setIcon(new ImageIcon(TransmisionCell.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		
		deleteButton = new JButton("");
		deleteButton.setIcon(new ImageIcon(TransmisionCell.class.getResource("/images/Transmisiones/remove-ico.png")));
		deleteButton.setToolTipText("Eliminar");
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteTransmission();
			}
		});
		
		customButton = new JButton("");
		customButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		customButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rateItem();
			}
		});
		
		ratingImageLabel = new JLabel("");
		ratingImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ratingImageLabel.setIcon(new ImageIcon(TransmisionCell.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		ratingImageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		dateLabel = new JLabel("Fecha");
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
		dateLabel.setText(sdf.format(transmission.getFecha()));
		
		customTagLabel1 = new JLabel("New label");
		customTagLabel1.setVisible(false);
		
		customFieldLabel1 = new JLabel("New label");
		customFieldLabel1.setVisible(false);
		customButton.setText("");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(itemTypeLabel, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
							.addComponent(dateLabel, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(itemImageLabel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(customTagLabel1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(customFieldLabel1, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(74)
									.addComponent(deleteButton)
									.addGap(18)
									.addComponent(customButton, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
									.addGap(63)
									.addComponent(ratingImageLabel, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)))
							.addGap(1)))
					.addGap(9))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(itemTypeLabel)
						.addComponent(dateLabel))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(itemImageLabel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(titleLabel)
							.addGap(21)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(customTagLabel1)
								.addComponent(customFieldLabel1))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(22)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(16)
											.addComponent(deleteButton))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(16)
											.addComponent(customButton, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(ratingImageLabel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))))
					.addGap(9))
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
		ratingImageLabel.setIcon(new UtilTools().getScaledImageIcon(ratingImage.getImage(), 40, 40));
	}
	protected JLabel getCustomFieldLabel1() {
		return customFieldLabel1;
	}
	
	protected JLabel getCustomTagLabel1() {
		return customTagLabel1;
	}
	
	protected void updateTransmission(){
		Thread updateTransmission = new Thread(new OneArgumentRunnableObject(transmission) {
			
			@Override
			public void run() {
				PersistentDataManager.getInstance().updateTransmission(transmission);
			}
		});
		updateTransmission.start();
	}
}
