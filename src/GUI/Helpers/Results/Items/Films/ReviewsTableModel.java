package GUI.Helpers.Results.Items.Films;

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
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import Model.FichaPelicula;
import Utils.UtilTools;

public class ReviewsTableModel extends AbstractTableModel implements ActionListener{
	private FichaPelicula film;
	private String[] columns;
	private String[] authors;
	private ButtonGroup buttons;
	
	public ReviewsTableModel(FichaPelicula film){
		super();
		this.film = film;
		columns = new String[]{"Autor", "Valoración", ""};
		authors = film.getCriticas().keySet().toArray(new String[0]);
		buttons = new ButtonGroup();
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
	public Class getColumnClass(int column){
		if(column == 2){
			return JButton.class;
		}
		return this.getValueAt(0, column).getClass();
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
				System.out.println("valoracion no determinada");
				return null;
			}
			System.out.println("image added");
			
			return tools.getScaledImage(image.getImage(), 30, 30);
		case 2:
			JButton button = new JButton("Leer");
			button.addActionListener(this);
			buttons.add(button);
			return button;
		default:
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("botón leer presionado");
	}

}
