package GUI.Helpers.Results.Items.Films;

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
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class FilmDetailsView extends FilmResultItem implements Runnable{
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
	private JTable reviewsTable;
	private JPanel infoPanel;
	
	public FilmDetailsView(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem) {
		super(mainFrame, parentView, helperItem);
		
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(64, 64, 64), null, null, null));
		setLayout(null);
		
		infoPanel = new JPanel();
		infoPanel.setBorder(UIManager.getBorder("InternalFrame.border"));
		infoPanel.setBounds(10, 6, 418, 750);
		add(infoPanel);
		
		titleLabel = new JLabel("T\u00EDtulo");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_2 = new JLabel("T\u00EDtulo original");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_3 = new JLabel("Director");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_4 = new JLabel("Pa\u00EDs");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		originalTitleLabel = new JLabel("Value");
		originalTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		directorLabel = new JLabel("Value");
		directorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		countryLabel = new JLabel("Value");
		countryLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		imageLabel = new JLabel("");
		imageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setIcon(new ImageIcon(FilmDetailsView.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		
		JLabel lblNewLabel_5 = new JLabel("A\u00F1o");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		yearLabel = new JLabel("Value");
		yearLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_6 = new JLabel("Reparto");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		castingList = new JList(this.getFilm().getReparto());
		castingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		castingList.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		
		JLabel lblNewLabel_7 = new JLabel("Premios");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		prizesList = new JList(this.getFilm().getPremios());
		prizesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		prizesList.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
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
		
		JButton btnNewButton = new JButton("Votar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rateItem();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		table = new JTable();
		
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
		
		yourMarkLabel = new JLabel("Tu Nota");
		yourMarkLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_9 = new JLabel("Nota Almas Gemelas");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 12));
		
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
		
		JLabel lblCrticas = new JLabel("Cr\u00EDticas");
		lblCrticas.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		reviewsTable = new JTable();
		reviewsTable.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, null, null, null));
		GroupLayout gl_infoPanel = new GroupLayout(infoPanel);
		gl_infoPanel.setHorizontalGroup(
			gl_infoPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 398, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(1)
							.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
					.addGap(10)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(1)
							.addComponent(originalTitleLabel, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(1)
							.addComponent(directorLabel, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
						.addComponent(countryLabel, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(8)
					.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(54)
					.addComponent(yearLabel, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(8)
					.addComponent(lblNewLabel_6, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(castingList, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
					.addGap(19)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(7)
					.addComponent(lblNewLabel_7, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(38)
					.addComponent(prizesList, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(13)
							.addComponent(yourMarkLabel))
						.addComponent(usersMarkFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(310)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(310)
					.addComponent(table, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(lblCrticas, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addComponent(reviewsTable, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNewLabel_9, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addGap(34)
					.addComponent(twinSoulsMarkFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		gl_infoPanel.setVerticalGroup(
			gl_infoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addComponent(titleLabel)
					.addGap(27)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
							.addGap(17)
							.addComponent(lblNewLabel_3)
							.addGap(17)
							.addComponent(lblNewLabel_4))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addComponent(originalTitleLabel)
							.addGap(18)
							.addComponent(directorLabel)
							.addGap(14)
							.addComponent(countryLabel))
						.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(1)
							.addComponent(lblNewLabel_5))
						.addComponent(yearLabel))
					.addGap(44)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_6))
						.addComponent(castingList, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(18)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(23)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(4)
							.addComponent(lblNewLabel_7))
						.addComponent(prizesList, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(21)
							.addComponent(yourMarkLabel)
							.addGap(6)
							.addComponent(usersMarkFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(10)
					.addComponent(btnNewButton)
					.addGap(6)
					.addComponent(table, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(9)
							.addComponent(lblCrticas))
						.addComponent(reviewsTable, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
					.addGap(47)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(25)
							.addComponent(lblNewLabel_9))
						.addComponent(twinSoulsMarkFrame, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)))
		);
		infoPanel.setLayout(gl_infoPanel);
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
