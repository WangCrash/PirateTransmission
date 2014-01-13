package GUI.Helpers.Results.Items;

import javax.swing.JFrame;

import GUI.Helpers.Results.HelperResultsSection;
import Managers.Helpers.FilmAffinityBot;
import Model.FichaPelicula;
import Model.HelperItem;
import Utils.UtilTools;

public abstract class FilmResultItem extends HelperResultItem{
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
		String search;
		if(new UtilTools().showYesNoDialog(this.mainFrame, "Buscar Torrent", "Buscar en su idioma original")){
			if(ficha.getTituloOriginal() == null || ficha.getTituloOriginal().isEmpty()){
				ficha = FilmAffinityBot.getInstance().fillFichaPelicula(ficha);
			}
			search = ficha.getTituloOriginal();
		}else{
			search = new UtilTools().killFilmAffinityWords(ficha.getTitulo());
		}
		this.parentView.searchTorrent(search);
	}
	
	public void setFilm(FichaPelicula film){
		this.setHelperItem(film);
		this.film = film;
	}
	
	public FichaPelicula getFilm(){
		return this.film;
	}
	
	public abstract void filmSuccesfullyRated(FichaPelicula film);
}
