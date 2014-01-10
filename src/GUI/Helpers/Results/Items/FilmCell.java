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
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;

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
	private JPanel noteFrame;
	private JLabel lblNewLabel_1;

	public FilmCell(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem, boolean showForRating) {
		super(mainFrame, parentView, helperItem);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		imageLabel = new JLabel("Image");
		imageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setIcon(null);
		
		titleLabel = new JLabel("T\u00EDtulo");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		directorLabel = new JLabel("Director");
		directorLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		
		noteFrame = new JPanel();
		noteFrame.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		noteFrame.setBackground(new Color(255, 255, 255));
		
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
		
		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setVisible(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 381, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
												.addComponent(countryLabel, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
											.addGap(2)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(lblAo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(yearLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addGap(25))
										.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
											.addComponent(searchTorrentButton)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(showDetailsButton, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
										.addComponent(directorLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(lblDirector, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
									.addGap(106)))
							.addGap(16)
							.addComponent(noteFrame, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addGap(2))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(109)
							.addComponent(notWatchedLabel)
							.addGap(20)
							.addComponent(voteButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
					.addGap(8))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(178, Short.MAX_VALUE)
					.addComponent(lblNewLabel_1)
					.addGap(175))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(titleLabel)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(lblDirector)
									.addGap(3)
									.addComponent(directorLabel)
									.addGap(7)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel)
										.addComponent(lblAo))
									.addGap(6)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(countryLabel)
										.addComponent(yearLabel))))
							.addGap(3)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(4)
									.addComponent(notWatchedLabel))
								.addComponent(voteButton))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(searchTorrentButton)
								.addComponent(showDetailsButton))
							.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
							.addComponent(lblNewLabel_1))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(14)
							.addComponent(noteFrame, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(98))))
		);
		setLayout(groupLayout);
		
		initLabels(showForRating);
	}

	private void initLabels(boolean showForRating) {
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
		
		if((ficha.getValoracion() == null) || (ficha.getValoracion().isEmpty())){
			noteLabel.setVisible(false);
			noteFrame.setVisible(false);
		}else{
			noteLabel.setText(ficha.getValoracion());
		}
		
		voteButton.setVisible(showForRating);
		notWatchedLabel.setVisible(showForRating);
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
			search = new UtilTools().killFilmAffinityWords(ficha.getTitulo());
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
		imageLabel.setText("");
		imageLabel.setIcon(image);
	}
}
