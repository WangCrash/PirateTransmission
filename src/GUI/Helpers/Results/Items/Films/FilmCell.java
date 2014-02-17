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
		
		voteButton = new JButton("");
		voteButton.setIcon(new ImageIcon(FilmCell.class.getResource("/images/HelperResults/rate-film-icon.png")));
		voteButton.setToolTipText("Votar");
		voteButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		voteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rateItem();
			}
		});
		
		searchTorrentButton = new JButton("");
		searchTorrentButton.setIcon(new ImageIcon(FilmCell.class.getResource("/images/HelperResults/search-torrent-icon.png")));
		searchTorrentButton.setToolTipText("Buscar Torrent");
		searchTorrentButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchTorrentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchItemTorrent();
			}
		});
		
		showDetailsButton = new JButton("");
		showDetailsButton.setIcon(new ImageIcon(FilmCell.class.getResource("/images/HelperResults/show-details-icon.png")));
		showDetailsButton.setHorizontalAlignment(SwingConstants.LEFT);
		showDetailsButton.setToolTipText("Ver Detalles");
		showDetailsButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		showDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showFilmDetails();
			}
		});
		
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
		
		directorsPane = new JPanel();
		directorsPane.setLayout(new GridLayout(0, 2, 0, 0));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(95)
					.addComponent(lblDirector, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(directorsPane, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addGap(51)
							.addComponent(lblAo, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(countryLabel, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(yearLabel, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(25)
							.addComponent(searchTorrentButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(voteButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(showDetailsButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)))
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(noteFrame, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(yourNoteLabel, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(usersNoteFrame, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
					.addGap(6))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(titleLabel)
					.addGap(7)
					.addComponent(lblDirector)
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(17)
							.addComponent(imageLabel, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
							.addGap(50))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(directorsPane, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addGap(9)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(lblAo))
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(countryLabel)
								.addComponent(yearLabel))
							.addGap(48)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(searchTorrentButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(voteButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(showDetailsButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(noteFrame, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(9)
							.addComponent(yourNoteLabel)
							.addGap(2)
							.addComponent(usersNoteFrame, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
					.addGap(10))
		);
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
