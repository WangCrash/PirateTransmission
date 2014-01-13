package GUI.Helpers.Results.Items;

import javax.swing.JPanel;

import GUI.Helpers.Results.HelperResultsSection;
import Managers.Helpers.FilmAffinityBot;
import Model.FichaPelicula;
import Model.HelperItem;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FilmDetailsPanel extends FilmResultItem implements Runnable{
	private URL imageURL;
	
	private JLabel markLabel;
	private JLabel originalTitleLabel;
	private JList castingList;
	private JPanel twinSoulsMarkFrame;
	private JLabel countryLabel;
	private JPanel usersMarkFrame;
	private JLabel usersMarkLabel;
	private JLabel twinSoulsMarkLabel;
	private JList prizesList;
	private JLabel imageLabel;
	private JLabel yearLabel;
	private JLabel titleLabel;
	private JLabel directorLabel;
	private JLabel yourMarkLabel;
	private JTable table;
	
	public FilmDetailsPanel(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem) {
		super(mainFrame, parentView, helperItem);
		
		setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(64, 64, 64), null, null, null));
		
		titleLabel = new JLabel("T\u00EDtulo");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		imageLabel = new JLabel("");
		imageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setIcon(new ImageIcon(FilmDetailsPanel.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		usersMarkFrame = new JPanel();
		usersMarkFrame.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		usersMarkLabel = new JLabel("Nota U");
		usersMarkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usersMarkLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		GroupLayout gl_usersMarkFrame = new GroupLayout(usersMarkFrame);
		gl_usersMarkFrame.setHorizontalGroup(
			gl_usersMarkFrame.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_usersMarkFrame.createSequentialGroup()
					.addContainerGap()
					.addComponent(usersMarkLabel, GroupLayout.PREFERRED_SIZE, 60, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_usersMarkFrame.setVerticalGroup(
			gl_usersMarkFrame.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_usersMarkFrame.createSequentialGroup()
					.addContainerGap()
					.addComponent(usersMarkLabel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		usersMarkFrame.setLayout(gl_usersMarkFrame);
		
		JButton btnNewButton = new JButton("Votar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rateItem();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNewLabel_2 = new JLabel("T\u00EDtulo original");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_3 = new JLabel("Director");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_4 = new JLabel("Pa\u00EDs");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_5 = new JLabel("A\u00F1o");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_6 = new JLabel("Reparto");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_7 = new JLabel("Premios");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblCrticas = new JLabel("Cr\u00EDticas");
		lblCrticas.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		markLabel = new JLabel("Nota");
		markLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		markLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(markLabel, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(14, Short.MAX_VALUE)
					.addComponent(markLabel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		originalTitleLabel = new JLabel("Value");
		originalTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		directorLabel = new JLabel("Value");
		directorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		countryLabel = new JLabel("Value");
		countryLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		yearLabel = new JLabel("Value");
		yearLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		twinSoulsMarkFrame = new JPanel();
		twinSoulsMarkFrame.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		twinSoulsMarkLabel = new JLabel("Nota U");
		twinSoulsMarkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		twinSoulsMarkLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_twinSoulsMarkFrame = new GroupLayout(twinSoulsMarkFrame);
		gl_twinSoulsMarkFrame.setHorizontalGroup(
			gl_twinSoulsMarkFrame.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_twinSoulsMarkFrame.createSequentialGroup()
					.addGap(11)
					.addComponent(twinSoulsMarkLabel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(11, Short.MAX_VALUE))
		);
		gl_twinSoulsMarkFrame.setVerticalGroup(
			gl_twinSoulsMarkFrame.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_twinSoulsMarkFrame.createSequentialGroup()
					.addGap(10)
					.addComponent(twinSoulsMarkLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		twinSoulsMarkFrame.setLayout(gl_twinSoulsMarkFrame);
		
		JLabel lblNewLabel_9 = new JLabel("Nota Almas Gemelas");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		castingList = new JList(this.getFilm().getReparto());
		castingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		castingList.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		
		prizesList = new JList(this.getFilm().getPremios());
		prizesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		prizesList.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		
		yourMarkLabel = new JLabel("Tu Nota");
		yourMarkLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		table = new JTable();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 398, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(originalTitleLabel, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(directorLabel, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
						.addComponent(countryLabel, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(54)
					.addComponent(yearLabel, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addComponent(lblNewLabel_6, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(castingList, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
					.addGap(19)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(19)
					.addComponent(lblNewLabel_9, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addGap(34)
					.addComponent(twinSoulsMarkFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(17)
							.addComponent(lblNewLabel_7, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(lblCrticas, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)))
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(table, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
							.addGap(31)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(prizesList, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
							.addGap(20)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(16)
									.addComponent(yourMarkLabel))
								.addComponent(usersMarkFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(titleLabel)
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
							.addGap(17)
							.addComponent(lblNewLabel_3)
							.addGap(17)
							.addComponent(lblNewLabel_4))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(originalTitleLabel)
							.addGap(18)
							.addComponent(directorLabel)
							.addGap(14)
							.addComponent(countryLabel))
						.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lblNewLabel_5))
						.addComponent(yearLabel))
					.addGap(44)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_6))
						.addComponent(castingList, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(9)
							.addComponent(lblNewLabel_7))
						.addComponent(prizesList, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(yourMarkLabel)
							.addGap(6)
							.addComponent(usersMarkFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(15)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCrticas)
								.addComponent(table, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnNewButton))
					.addGap(100)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(25)
							.addComponent(lblNewLabel_9))
						.addComponent(twinSoulsMarkFrame, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)))
		);
		setLayout(groupLayout);
	}
	public void initLabels() {
		FichaPelicula film = this.getFilm();
		try {
			imageURL = new URL(film.getImageUrl());
			Thread t = new Thread(this);
			t.start();
		} catch (MalformedURLException e) {
			System.out.println("BAD URL: " + film.getImageUrl());
		}
		titleLabel.setText(film.getTitulo());
		originalTitleLabel.setText(film.getTituloOriginal());
		countryLabel.setText(film.getPais());
		yearLabel.setText(film.getAño());
		
		markLabel.setText(film.getValoracion());
		
		boolean userLogged = FilmAffinityBot.getInstance().isLogged();
		yourMarkLabel.setVisible(userLogged);
		usersMarkFrame.setVisible(userLogged);
		usersMarkLabel.setText(film.getNotaUsuario());
		usersMarkLabel.setVisible(userLogged);
		twinSoulsMarkFrame.setVisible(userLogged);
		twinSoulsMarkLabel.setText(film.getNotaAlmasGemelas());
		twinSoulsMarkLabel.setVisible(userLogged);
	}
	
	@Override
	public void run() {
		getFilmImage();
	}
	
	private void getFilmImage() {
		ImageIcon image = new ImageIcon(imageURL);
		imageLabel.setText("");
		imageLabel.setBorder(null);
		imageLabel.setIcon(image);
	}

	@Override
	public void filmSuccesfullyRated(FichaPelicula film) {
		this.setFilm(film);
		boolean flag = film.getNotaUsuario().isEmpty();
		yourMarkLabel.setVisible(!flag);
		usersMarkFrame.setVisible(!flag);
		usersMarkLabel.setText(film.getNotaUsuario());
		usersMarkLabel.setVisible(!flag);
	}
}
