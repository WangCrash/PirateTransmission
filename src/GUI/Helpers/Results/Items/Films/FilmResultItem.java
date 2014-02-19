package GUI.Helpers.Results.Items.Films;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import GUI.Helpers.Results.HelperResultsSection;
import GUI.Helpers.Results.Items.HelperResultItem;
import GUI.Helpers.Results.Items.Films.Rating.RateFilm;
import GUI.Helpers.Results.Items.Films.Rating.RateMovieView;
import Managers.Helpers.FilmAffinityBot;
import Model.FichaPelicula;
import Model.HelperItem;
import Utils.UtilTools;

@SuppressWarnings("serial")
public abstract class FilmResultItem extends HelperResultItem implements RateFilm{
	private FichaPelicula film;
	
	public FilmResultItem(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem){
		super(mainFrame, parentView, helperItem);
		this.film = (FichaPelicula)this.getHelperItem();
	}
	
	@Override
	public void rateItem(){
		RateMovieView rateView = new RateMovieView(mainFrame, this, film);
		rateView.setVisible(true);
	}
	
	@Override
	public void searchItemTorrent() {
		FichaPelicula ficha = this.getFilm();
		String search = null;
		int response = new UtilTools().showYesNoDialog(this.mainFrame, "Buscar Torrent", "Buscar en su idioma original");
		if(response == JOptionPane.YES_OPTION){
			if(ficha.getTituloOriginal() == null || ficha.getTituloOriginal().isEmpty()){
				ficha = FilmAffinityBot.getInstance().fillFichaPelicula(ficha);
			}
			search = ficha.getTituloOriginal();
		}else if(response == JOptionPane.NO_OPTION){
			search = new UtilTools().killFilmAffinityWords(ficha.getTitulo());
		}
		if(search != null){
			this.parentView.searchTorrent(search, getFilm());
		}
	}
	
	public void setFilm(FichaPelicula film){
		this.setHelperItem(film);
		this.film = film;
	}
	
	public FichaPelicula getFilm(){
		return this.film;
	}
}
