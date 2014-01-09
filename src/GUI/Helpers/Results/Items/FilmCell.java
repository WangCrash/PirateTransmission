package GUI.Helpers.Results.Items;

import GUI.Helpers.Results.HelperResultsSection;
import Model.FichaPelicula;
import Model.HelperItem;
import Utils.UtilTools;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import java.awt.Font;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FilmCell extends HelperResultItem implements Runnable {

	private static final long serialVersionUID = -5062658692098221504L;
	private JLabel titleLabel;
	private JLabel imageLabel;
	private JLabel directorLabel;
	private JLabel countryLabel;
	private JLabel yearLabel;
	private JLabel noteLabel;
	private JButton voteButton;
	private JButton searchTorrentButton;
	private JButton showDetailsButton;
	private JLabel notWatchedLabel;
	
	private URL imageURL;

	public FilmCell(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem, boolean showAllLabels) {
		super(mainFrame, parentView, helperItem);
		
		imageLabel = new JLabel("Image");
		imageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setIcon(null);
		
		titleLabel = new JLabel("T\u00EDtulo");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		directorLabel = new JLabel("Director");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		
		countryLabel = new JLabel("Pa\u00EDs");
		
		yearLabel = new JLabel("A\u00F1o");
		
		voteButton = new JButton("Votar");
		voteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rateItem();
			}
		});
		
		searchTorrentButton = new JButton("Buscar Torrent");
		searchTorrentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchItemTorrent();
			}
		});
		
		showDetailsButton = new JButton("Ver Detalles");
		showDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showFilmDetails();
			}
		});
		
		notWatchedLabel = new JLabel("No vista");
		notWatchedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(22)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(directorLabel, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(countryLabel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(yearLabel, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)))
									.addPreferredGap(ComponentPlacement.RELATED, 10, GroupLayout.PREFERRED_SIZE)
									.addComponent(panel, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(75)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(searchTorrentButton)
									.addGap(18)
									.addComponent(showDetailsButton, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(2)
									.addComponent(notWatchedLabel, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(voteButton, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addComponent(titleLabel)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(imageLabel, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
								.addComponent(countryLabel)
								.addComponent(yearLabel))
							.addComponent(directorLabel))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(notWatchedLabel)
						.addComponent(voteButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchTorrentButton)
						.addComponent(showDetailsButton))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		noteLabel = new JLabel("10");
		noteLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(noteLabel, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(noteLabel, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
		
		initLabels(showAllLabels);
	}

	private void initLabels(boolean showAll) {
		FichaPelicula ficha = (FichaPelicula)this.helperItem;
		System.out.println(ficha);
		try {
			imageURL = new URL(ficha.getImageUrl());
			Thread t = new Thread(this);
			t.start();
		} catch (MalformedURLException e) {
			System.out.println("BAD URL: " + ficha.getImageUrl());
		}
		titleLabel.setText(ficha.getTitulo());
		String director = convertArrayToStringLine(ficha.getDirector());
		directorLabel.setText(director);
		countryLabel.setText(ficha.getPais());
		yearLabel.setText(ficha.getAño());
		noteLabel.setText(ficha.getValoracion());
		noteLabel.setVisible(showAll);
		voteButton.setVisible(showAll);
		notWatchedLabel.setVisible(showAll);
	}

	private String convertArrayToStringLine(String[] array) {
		if(array == null){
			return "";
		}
		String result = "";
		for (int i = 0; i < array.length; i++) {
			result += array[i] + ";";
		}
		return result.substring(0, result.length() - 1);
	}

	@Override
	public void searchItemTorrent() {
		FichaPelicula ficha = (FichaPelicula)this.helperItem;
		String search;
		if(new UtilTools().showYesNoDialog(this.mainFrame, "Buscar Torrent", "Buscar en su idioma original")){
			search = ficha.getTituloOriginal();
		}else{
			search = ficha.getTitulo();
		}
		this.parentView.searchTorrent(search);
	}
	
	private void showFilmDetails() {
		
	}

	@Override
	public void rateItem() {
		
	}

	@Override
	public void run() {
		getFilmImage();
	}

	private void getFilmImage() {
		ImageIcon image = new ImageIcon(imageURL);
		imageLabel.setIcon(image);
	}
}
