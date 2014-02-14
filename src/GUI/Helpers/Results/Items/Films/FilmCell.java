package GUI.Helpers.Results.Items.Films;

import GUI.Helpers.Results.HelperResultsSection;
import Managers.Helpers.FilmAffinityBot;
import Model.FichaPelicula;
import Model.HelperItem;
import Utils.UtilTools;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JPanel;

import java.awt.Font;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.MatteBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.GridLayout;

public class FilmCell extends FilmResultItem implements Runnable {

	private static final long serialVersionUID = -5062658692098221504L;
	private JLabel titleLabel;
	private JLabel imageLabel;
	private JLabel countryLabel;
	private JLabel yearLabel;
	private JLabel noteLabel;
	private JButton voteButton;
	private JButton searchTorrentButton;
	private JButton showDetailsButton;
	private JLabel notWatchedLabel;
	
	private URL imageURL;
	private JPanel noteFrame;
	private JLabel yourNoteLabel;
	private JLabel usersNoteLabel;
	private JPanel usersNoteFrame;
	private JPanel directorsPane;

	public FilmCell(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem, boolean showForRating) {
		super(mainFrame, parentView, helperItem);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		imageLabel = new JLabel("");
		imageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setIcon(new ImageIcon(FilmCell.class.getResource("/com/sun/java/swing/plaf/motif/icons/image-delayed.png")));
		
		titleLabel = new JLabel("T\u00EDtulo");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		noteFrame = new JPanel();
		noteFrame.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		noteFrame.setBackground(new Color(255, 255, 255));
		
		voteButton = new JButton("Votar");
		voteButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		voteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rateItem();
			}
		});
		
		searchTorrentButton = new JButton("Buscar Torrent");
		searchTorrentButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchTorrentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchItemTorrent();
			}
		});
		
		showDetailsButton = new JButton("Ver Detalles");
		showDetailsButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		showDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showFilmDetails();
			}
		});
		
		notWatchedLabel = new JLabel("No vista");
		notWatchedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblDirector = new JLabel("Director");
		lblDirector.setFont(new Font("Dialog", Font.BOLD, 11));
		
		JLabel lblNewLabel = new JLabel("Pa\u00EDs");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 11));
		
		JLabel lblAo = new JLabel("A\u00F1o");
		lblAo.setFont(new Font("Dialog", Font.BOLD, 11));
		
		noteLabel = new JLabel("10");
		noteLabel.setFont(new Font("Tahoma", Font.PLAIN, 33));
		noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_noteFrame = new GroupLayout(noteFrame);
		gl_noteFrame.setHorizontalGroup(
			gl_noteFrame.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_noteFrame.createSequentialGroup()
					.addGap(5)
					.addComponent(noteLabel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_noteFrame.setVerticalGroup(
			gl_noteFrame.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_noteFrame.createSequentialGroup()
					.addGap(3)
					.addComponent(noteLabel, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		noteFrame.setLayout(gl_noteFrame);
		
		countryLabel = new JLabel("Pa\u00EDs");
		countryLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		yearLabel = new JLabel("A\u00F1o");
		yearLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		usersNoteFrame = new JPanel();
		usersNoteFrame.setVisible(false);
		usersNoteFrame.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		usersNoteFrame.setBackground(Color.WHITE);
		
		usersNoteLabel = new JLabel("10");
		usersNoteLabel.setVisible(false);
		usersNoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usersNoteLabel.setFont(new Font("Tahoma", Font.PLAIN, 33));
		GroupLayout gl_usersNoteFrame = new GroupLayout(usersNoteFrame);
		gl_usersNoteFrame.setHorizontalGroup(
			gl_usersNoteFrame.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_usersNoteFrame.createSequentialGroup()
					.addGap(6)
					.addComponent(usersNoteLabel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_usersNoteFrame.setVerticalGroup(
			gl_usersNoteFrame.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_usersNoteFrame.createSequentialGroup()
					.addGap(5)
					.addComponent(usersNoteLabel, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		usersNoteFrame.setLayout(gl_usersNoteFrame);
		
		yourNoteLabel = new JLabel("Tu nota");
		yourNoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yourNoteLabel.setVisible(false);
		
		JButton btnNewButton = new JButton("botonParaAjuste");
		btnNewButton.setVisible(false);
		btnNewButton.setEnabled(false);
		
		directorsPane = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(95)
							.addComponent(lblDirector, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(26)
											.addComponent(notWatchedLabel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(voteButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(26)
											.addComponent(searchTorrentButton)
											.addGap(6)
											.addComponent(showDetailsButton, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(19)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
											.addGap(51)
											.addComponent(lblAo, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(countryLabel, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
											.addGap(10)
											.addComponent(yearLabel, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(15)
									.addComponent(directorsPane, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(noteFrame, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(yourNoteLabel, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(1)
									.addComponent(usersNoteFrame, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(196)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(titleLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(7)
							.addComponent(lblDirector)
							.addGap(2)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(6)
									.addComponent(directorsPane, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
									.addGap(9)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel)
										.addComponent(lblAo))
									.addGap(6)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(countryLabel)
										.addComponent(yearLabel))
									.addGap(16)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
											.addComponent(voteButton)
											.addGap(23))
										.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
											.addComponent(notWatchedLabel)
											.addGap(28)))
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(searchTorrentButton, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
										.addComponent(showDetailsButton, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
									.addGap(3))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(12)
									.addComponent(noteFrame, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
									.addGap(9)
									.addComponent(yourNoteLabel)
									.addGap(2)
									.addComponent(usersNoteFrame, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
							.addGap(6)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
							.addGap(6))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(47)
							.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		directorsPane.setLayout(new GridLayout(0, 2, 0, 0));
		setLayout(groupLayout);
		
		initLabels(showForRating);
	}
	
	public void filmSuccesfullyRated(FichaPelicula film){
		this.setFilm(film);
		boolean flag = !film.getNotaUsuario().equals("-1");
		yourNoteLabel.setVisible(flag);
		usersNoteFrame.setVisible(flag);
		usersNoteLabel.setText(film.getNotaUsuario());
		usersNoteLabel.setVisible(flag);
		notWatchedLabel.setVisible(!flag);
	}

	private void initLabels(boolean showForRating) {
		FichaPelicula ficha = this.getFilm();
		System.out.println(ficha);
		try {
			imageURL = new URL(ficha.getImageUrl());
			Thread t = new Thread(this);
			t.start();
		} catch (MalformedURLException e) {
			System.out.println("BAD URL: " + ficha.getImageUrl());
		}
		UtilTools tools = new UtilTools();
		
		titleLabel.setText(ficha.getTitulo());
		tools.setToolTipText(titleLabel, ficha.getTitulo());
		
		int maxDirectors = 3;
		boolean cutLength = true;
		if(maxDirectors >= ficha.getDirector().length){
			maxDirectors = ficha.getDirector().length;
			cutLength = false;
		}
		for (int i = 0; i < maxDirectors; i++) {
			JLabel directorLabel = new JLabel("<HTML><U>" + ficha.getDirector()[i] + "<U><HTML>");
			directorLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			directorLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
			directorLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JLabel tag = (JLabel)e.getSource();
					if(e.getClickCount() == 1){
						searchDirector(tag.getText());
					}
				}
			});
			directorsPane.add(directorLabel);
		}
		if(cutLength){
			JLabel furtherMoreLabel = new JLabel("Y más...");
			furtherMoreLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
			directorsPane.add(furtherMoreLabel);
		}
		
		countryLabel.setText(ficha.getPais());
		tools.setToolTipText(countryLabel, ficha.getPais());
		
		yearLabel.setText(ficha.getAño());
		
		if((ficha.getValoracion() == null) || (ficha.getValoracion().isEmpty())){
			noteLabel.setVisible(false);
			noteFrame.setVisible(false);
		}else{
			noteLabel.setText(ficha.getValoracion());
		}
		
		voteButton.setVisible(showForRating);
		notWatchedLabel.setVisible(showForRating);
	}
	
	private void showFilmDetails() {
		parentView.showItemDetails(this.getFilm());
	}

	@Override
	public void run() {
		getFilmImage();
	}

	private void getFilmImage() {
		ImageIcon image = new ImageIcon(imageURL);
		imageLabel.setText("");
		imageLabel.setIcon(image);
	}
	
	private void searchDirector(String director) {
		director = director.replaceAll("<HTML>", "");
		String search = director.replaceAll("<U>", "");
		parentView.searchItem(search, FilmAffinityBot.FILMAFFINITY_DIRECTOR_SEARCH_OPTION);
	}
}
