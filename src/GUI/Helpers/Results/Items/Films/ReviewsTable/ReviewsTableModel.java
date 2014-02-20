package GUI.Helpers.Results.Items.Films.ReviewsTable;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.CellRendererPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import Model.FichaPelicula;
import Utils.UtilTools;

public class ReviewsTableModel extends AbstractTableModel implements ActionListener{
	private FichaPelicula film;
	private String[] columns;
	private String[] authors;
	private static final Class<?>[] COLUMN_TYPES = new Class<?>[] {Integer.class, ImageIcon.class, JButton.class};
	private ButtonGroup buttons;
	private JFrame mainFrame;
	
	public ReviewsTableModel(JFrame mainFrame, FichaPelicula film){
		super();
		this.film = film;
		columns = new String[]{"Autor", "Valoración", ""};
		authors = film.getCriticas().keySet().toArray(new String[0]);
		buttons = new ButtonGroup();
		this.mainFrame = mainFrame;
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}
	@Override
	public String getColumnName(int col) {
        return columns[col];
    }
	

	@Override
	public int getRowCount() {
		return film.getCriticas().size();
	}
	
	@Override
	public boolean isCellEditable(int row, int col){
		return false;
	}
	
	@Override
	public Class<?> getColumnClass(int column){
		return COLUMN_TYPES[column];
	}

	@Override
	public Object getValueAt(int row, int col) {
		String author = authors[row];
		if(author == null){
			System.out.println("no hay autor");
			return null;
		}
		UtilTools tools = new UtilTools();
		ImageIcon image;
		switch (col) {
		case 0:
			return author;
		case 1:
			switch (film.getValoracionCritica(author)) {
			case FichaPelicula.FICHAPELICULA_CRITICA_POSITIVA:
				image = new ImageIcon(getClass().getResource("/images/emoticons/happy.png"));
				break;
			case FichaPelicula.FICHAPELICULA_CRITICA_NEUTRA:
				image = new ImageIcon(getClass().getResource("/images/emoticons/neutral.png"));
				break;
			case FichaPelicula.FICHAPELICULA_CRITICA_NEGATIVA:
				image = new ImageIcon(getClass().getResource("/images/emoticons/angry.png"));
				break;
			default:
				System.out.println("valoración no determinada");
				return null;
			}
			System.out.println("image added");
			return tools.getScaledImageIcon(image.getImage(), 30, 30);
		case 2:
			JButton button = new ReviewsTableButton("Leer", author);
			button.addActionListener(this);
			buttons.add(button);
			return button;
		default:
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ReviewsTableButton button = (ReviewsTableButton)e.getSource();
		System.out.println(film.getCriticas());
		
		ReviewDialog reviewDialog = new ReviewDialog(mainFrame, button.getReviewsKey(), film.getCritica(button.getReviewsKey()));
		reviewDialog.setVisible(true);
	}

}
