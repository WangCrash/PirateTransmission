package GUI.Helpers.Results.Items.Films;

import javax.swing.JPanel;

import GUI.Helpers.Results.HelperResultsSection;
import GUI.Helpers.Results.Items.Films.ReviewsTable.JTableButtonMouseListener;
import GUI.Helpers.Results.Items.Films.ReviewsTable.JTableButtonRenderer;
import GUI.Helpers.Results.Items.Films.ReviewsTable.ReviewDialog;
import GUI.Helpers.Results.Items.Films.ReviewsTable.ReviewsTableModel;
import GUI.Panel.PanelProperties;
import Managers.Helpers.FilmAffinityBot;
import Model.FichaPelicula;
import Model.HelperItem;
import Utils.UtilTools;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.UIManager;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class FilmDetailsView extends FilmResultItem implements Runnable{
	private URL imageURL;
	
	private JLabel markLabel;
	private JLabel originalTitleLabel;
	private JPanel twinSoulsMarkFrame;
	private JLabel countryLabel;
	private JPanel usersMarkFrame;
	private JLabel usersMarkLabel;
	private JLabel twinSoulsMarkLabel;
	private JLabel imageLabel;
	private JLabel yearLabel;
	private JLabel titleLabel;
	private JLabel yourMarkLabel;
	private JTable reviewsTable;
	private JPanel infoPanel;
	private JScrollPane castingScrollPane;
	private JTextPane sinopsisTextPane;
	private JList<String> castingList;
	private JScrollPane prizesScrollPane;
	private JList<String> prizesList;
	private JScrollPane reviewsScrollPane;
	private JScrollPane sinopsisScrollPane;
	private JButton rateButton;
	private JPanel directorsPane;
	
	public FilmDetailsView(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem) {
		super(mainFrame, parentView, helperItem);
		setFilm((FichaPelicula)helperItem);
		
		System.out.println("creado super");
		setBackground(SystemColor.menu);
		
		//setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(64, 64, 64), null, null, null));
		setBorder(PanelProperties.BORDER);
		
		infoPanel = new JPanel();
		//infoPanel.setBorder(UIManager.getBorder("InternalFrame.border"));
		
		titleLabel = new JLabel("T\u00EDtulo");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_2 = new JLabel("T\u00EDtulo original");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_3 = new JLabel("Director");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_4 = new JLabel("Pa\u00EDs");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		originalTitleLabel = new JLabel("Value");
		originalTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
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
		
		System.out.println("Creados primeros componentes");
		
		JLabel lblNewLabel_7 = new JLabel("Premios");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		System.out.println("Creadas listas");
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
					.addComponent(markLabel, GroupLayout.PREFERRED_SIZE, 57, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(14, Short.MAX_VALUE)
					.addComponent(markLabel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		rateButton = new JButton("");
		rateButton.setIcon(new ImageIcon(FilmDetailsView.class.getResource("/images/HelperResults/rate-film-icon.png")));
		rateButton.setToolTipText("Votar");
		rateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rateItem();
			}
		});
		rateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		System.out.println("Creando tabla");
		
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
					.addComponent(usersMarkLabel, GroupLayout.PREFERRED_SIZE, 55, Short.MAX_VALUE)
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
		
		sinopsisScrollPane = new JScrollPane();
		sinopsisScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel lblSinopsis = new JLabel("Sinopsis");
		lblSinopsis.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		reviewsScrollPane = new JScrollPane();
		
		reviewsTable = new JTable(){
			@Override
			 public String getToolTipText(MouseEvent e) {
			        String tip = null;
			        Point p = e.getPoint();
			        int rowIndex = rowAtPoint(p);
			        int colIndex = columnAtPoint(p);
			        int realColumnIndex = convertColumnIndexToModel(colIndex);

			        if (realColumnIndex == 0) { //Sport column
			            tip = getValueAt(rowIndex, colIndex).toString();

			        }// else { //another column
//			            //You can omit this part if you know you don't 
//			            //have any renderers that supply their own tool 
//			            //tips.
//			            tip = super.getToolTipText(e);
//			        }
			        return tip;
			    }
		};
		reviewsScrollPane.setViewportView(reviewsTable);
		reviewsTable.setBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, null, null, null));
		
		sinopsisTextPane = new JTextPane();
		sinopsisTextPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2){
					if(!sinopsisTextPane.getText().isEmpty()){
						showSinopsisOnDialog();
					}
				}
			}
		});
		sinopsisTextPane.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sinopsisTextPane.setText("lorem ipsum escatigae no taliratae guanto male son sonambilare");
		sinopsisTextPane.setEditable(false);
		sinopsisTextPane.setToolTipText("Doble click para agrandar");
		sinopsisScrollPane.setViewportView(sinopsisTextPane);
		
		castingScrollPane = new JScrollPane();
		castingScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		castingList = new JList<String>();
		castingList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2 && castingList.getSelectedIndex() != -1){
					searchActor();
				}
			}
		});
		castingScrollPane.setViewportView(castingList);
		
		prizesScrollPane = new JScrollPane();
		
		prizesList = new JList<String>();
		prizesScrollPane.setViewportView(prizesList);
		
		directorsPane = new JPanel();
		GroupLayout gl_infoPanel = new GroupLayout(infoPanel);
		gl_infoPanel.setHorizontalGroup(
			gl_infoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(4)
					.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 398, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(8)
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
							.addGap(11)
							.addComponent(originalTitleLabel, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(yearLabel, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
								.addComponent(countryLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
							.addGap(24)))
					.addGap(11)
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(13)
					.addComponent(lblNewLabel_7, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(prizesScrollPane, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(11)
					.addComponent(lblCrticas, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addComponent(reviewsScrollPane, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(lblNewLabel_6, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(castingScrollPane, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(10)
							.addComponent(lblSinopsis, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(directorsPane, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
								.addComponent(sinopsisScrollPane, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))))
					.addGap(27)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(rateButton, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(13)
							.addComponent(yourMarkLabel))
						.addComponent(usersMarkFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addGap(9)
					.addComponent(lblNewLabel_9, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addGap(34)
					.addComponent(twinSoulsMarkFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		gl_infoPanel.setVerticalGroup(
			gl_infoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_infoPanel.createSequentialGroup()
					.addComponent(titleLabel)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(27)
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
								.addComponent(originalTitleLabel))
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel.createSequentialGroup()
									.addGap(16)
									.addComponent(lblNewLabel_3)
									.addGap(1))
								.addGroup(gl_infoPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(directorsPane, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(countryLabel)
								.addComponent(lblNewLabel_4))
							.addGap(20)
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_5)
								.addComponent(yearLabel))
							.addGap(15))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(11)
							.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)))
					.addGap(11)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSinopsis)
								.addComponent(sinopsisScrollPane, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
							.addGap(19)
							.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_infoPanel.createSequentialGroup()
									.addGap(1)
									.addComponent(lblNewLabel_6))
								.addComponent(castingScrollPane, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(19)
							.addComponent(yourMarkLabel)
							.addGap(6)
							.addComponent(usersMarkFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rateButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_7)
						.addComponent(prizesScrollPane, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(49)
							.addComponent(lblCrticas))
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(46)
							.addComponent(reviewsScrollPane, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)))
					.addGap(17)
					.addGroup(gl_infoPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_infoPanel.createSequentialGroup()
							.addGap(25)
							.addComponent(lblNewLabel_9))
						.addComponent(twinSoulsMarkFrame, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
					.addGap(45))
		);
		directorsPane.setLayout(new GridLayout(0, 2, 0, 0));
		infoPanel.setLayout(gl_infoPanel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(2)
					.addComponent(infoPanel, GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(3)
					.addComponent(infoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		initLabels();
	}
	
//	private String arrayToString(String[] array){
//		String result = "";
//		for (int i = 0; i < array.length; i++) {
//			result += array[i] + ", ";
//		}
//		return result.substring(0, result.length() - 2);
//	}
	
	private void initLabels() {
		System.out.println("Detalles de \n" + this.getFilm());
		FichaPelicula film = this.getFilm();
		try {
			if(film.getImageUrl() != null){
				imageURL = new URL(film.getImageUrl());
				Thread t = new Thread(this);
				t.start();
			}
		} catch (MalformedURLException e) {
			System.out.println("BAD URL: " + film.getImageUrl());
		}
		
		UtilTools tools = new UtilTools();
		
		titleLabel.setText(film.getTitulo());
		tools.setToolTipText(titleLabel, film.getTitulo());
		
		originalTitleLabel.setText(film.getTituloOriginal());
		tools.setToolTipText(originalTitleLabel, film.getTituloOriginal());
		
		if(film.getDirector() != null){
			for (int i = 0; i < film.getDirector().length; i++) {
				JLabel directorLabel = new JLabel("<HTML><U>" + film.getDirector()[i] + "<U><HTML>");
				directorLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				directorLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						JLabel tag = (JLabel)e.getSource();
						if(e.getClickCount() == 1){
							searchDirector(tag.getText());
						}
					}
				});
				directorLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
				directorsPane.add(directorLabel);
			}
		}
		
		sinopsisTextPane.setText(film.getSinopsis());
		
		if(film.getReparto() != null){
			castingList.setListData(film.getReparto());
		}else{
			castingScrollPane.setEnabled(false);
		}
		if(film.getPremios() != null){
			prizesList.setListData(film.getPremios());
		}else{
			prizesScrollPane.setEnabled(false);
		}
		if(film.getCriticas() != null){
			reviewsTable.setModel(new ReviewsTableModel(mainFrame, this.getFilm()));
	        reviewsTable.getColumnModel().getColumn(2).setCellRenderer(new JTableButtonRenderer());
	        reviewsTable.addMouseListener(new JTableButtonMouseListener(reviewsTable));
	        
	        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
	        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
	        reviewsTable.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
	        
			reviewsTable.setRowHeight(35);
			reviewsTable.getColumnModel().getColumn(0).setPreferredWidth(100);
			reviewsTable.getColumnModel().getColumn(1).setPreferredWidth(10); 
			reviewsTable.getColumnModel().getColumn(2).setPreferredWidth(40);
		}else{
			reviewsScrollPane.setEnabled(false);
		}
		
		countryLabel.setText(film.getPais());
		tools.setToolTipText(countryLabel, film.getPais());		
		
		yearLabel.setText(film.getA�o());
		
		markLabel.setText(film.getValoracion());
		
		boolean userLogged = FilmAffinityBot.getInstance().isLogged();
		yourMarkLabel.setVisible(userLogged);
		usersMarkFrame.setVisible(userLogged);
		usersMarkLabel.setText(!userLogged || film.getNotaUsuario() == null || (film.getNotaUsuario().equals("-1"))?"":film.getNotaUsuario());
		usersMarkLabel.setVisible(userLogged);
		twinSoulsMarkFrame.setVisible(userLogged);
		twinSoulsMarkLabel.setText(film.getNotaAlmasGemelas());
		twinSoulsMarkLabel.setVisible(userLogged);
		rateButton.setEnabled(userLogged);
	}
	
	@Override
	public void run() {
		getFilmImage();
	}
	
	private void getFilmImage() {
		ImageIcon image = new ImageIcon(imageURL);
		imageLabel.setText("");
		imageLabel.setBorder(null);
		System.out.println("Ancho: " + (int)imageLabel.getSize().width + ", Alto:" + (int)imageLabel.getSize().getHeight());
		imageLabel.setIcon(new UtilTools().getScaledImageIcon(image.getImage(), 129, 166));
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				infoPanel.revalidate();
			}
		});
	}

	@Override
	public void filmSuccesfullyRated(FichaPelicula film) {
		this.setFilm(film);
		boolean flag = !film.getNotaUsuario().equals("-1");
		yourMarkLabel.setVisible(flag);
		usersMarkFrame.setVisible(flag);
		usersMarkLabel.setText(film.getNotaUsuario());
		usersMarkLabel.setVisible(flag);
	}
	
	private void searchDirector(String director) {
		director = director.replaceAll("<HTML>", "");
		String search = director.replaceAll("<U>", "");
		parentView.searchItem(search, FilmAffinityBot.FILMAFFINITY_DIRECTOR_SEARCH_OPTION);
	}
	
	private void searchActor() {
		String search = getFilm().getReparto()[castingList.getSelectedIndex()];
		parentView.searchItem(search, FilmAffinityBot.FILMAFFINITY_CAST_SEARCH_OPTION);
	}
	
	private void showSinopsisOnDialog() {
		ReviewDialog reviewDialog = new ReviewDialog(mainFrame, "Sinopsis", getFilm().getSinopsis());
		reviewDialog.setVisible(true);
	}
}
