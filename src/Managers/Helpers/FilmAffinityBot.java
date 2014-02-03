package Managers.Helpers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import Utils.UtilTools;
import Codification.Base64;
import Connection.ConnectionManager;
import FilmAffinity.FilmAffinityLoginModule;
import FilmAffinity.FilmAffinitySearcherModule;
import FilmAffinity.FilmAffinityVotingModule;
import Model.FichaPelicula;
import Model.HelperItem;


public class FilmAffinityBot extends HelperManager{
	private static volatile FilmAffinityBot instance = null;
	
	public static final String FILMAFFINITY_USER_AUTH_CONFIG_KEY = "filmaffinity-user";
	public static final String FILMAFFINITY_PASSWORD_AUTH_CONFIG_KEY = "filmaffinity-password";
	
	public static final int FILMAFFINITY_ALL_SEARCH_OPTION = 0;
	public static final int FILMAFFINITY_TITLE_SEARCH_OPTION = 1;
	public static final int FILMAFFINITY_DIRECTOR_SEARCH_OPTION = 2;
	public static final int FILMAFFINITY_CAST_SEARCH_OPTION = 3;
	
	public static final int FILMAFFINITY_GENRE_KEY_ALL = -1;
	public static final int FILMAFFINITY_GENRE_KEY_ACTION = 0;
	public static final int FILMAFFINITY_GENRE_KEY_ANIMATION = 1;
	public static final int FILMAFFINITY_GENRE_KEY_ADVENTURE = 2;
	public static final int FILMAFFINITY_GENRE_KEY_WAR = 3;
	public static final int FILMAFFINITY_GENRE_KEY_SCIENCE_FICTION = 4;
	public static final int FILMAFFINITY_GENRE_KEY_NOIR = 5;
	public static final int FILMAFFINITY_GENRE_KEY_COMEDY = 6;
	public static final int FILMAFFINITY_GENRE_KEY_UNKNOWN = 7;
	public static final int FILMAFFINITY_GENRE_KEY_DOCUMENTARY = 8;
	public static final int FILMAFFINITY_GENRE_KEY_DRAMA = 9;
	public static final int FILMAFFINITY_GENRE_KEY_FANTASTIC = 10;
	public static final int FILMAFFINITY_GENRE_KEY_INFANTILE = 11;
	public static final int FILMAFFINITY_GENRE_KEY_INTRIGUE = 12;
	public static final int FILMAFFINITY_GENRE_KEY_MUSICAL = 13;
	public static final int FILMAFFINITY_GENRE_KEY_ROMANCE = 14;
	public static final int FILMAFFINITY_GENRE_KEY_TV_SERIE = 15;
	public static final int FILMAFFINITY_GENRE_KEY_TERROR = 16;
	public static final int FILMAFFINITY_GENRE_KEY_THRILLER = 17;
	public static final int FILMAFFINITY_GENRE_KEY_WESTERN = 18;
	
	public static final String FILMAFFINITY_FILTERS_GENRE_KEY = "genre";
	public static final String FILMAFFINITY_FILTERS_LIMIT_KEY = "limit";
	public static final String FILMAFFINITY_FILTERS_FROM_YEAR_KEY = "fromyear";
	public static final String FILMAFFINITY_FILTERS_TO_YEAR_KEY = "toyear";
	
	public static final String FILMAFFINITY_FILM_NOT_WATCHED = "-1";
	
	public String user;
	public String password;
	
	private ConnectionManager cm;	
	
	private final String urlBase = "www.filmaffinity.com";
	private boolean logged = false;
	public final String LOGGED_TEXT = "Logged";
	
	private FilmAffinityBot(){
		user = "";
		password = "";
	}
	
	public static FilmAffinityBot getInstance(){
		synchronized (FilmAffinityBot.class) {
			if(instance == null){
				instance = new FilmAffinityBot();
			}
		}
		return instance;
	}
	
	@Override
	public boolean initManager(){
		return initManager(false);
	}
	
	public boolean initManager(boolean testing){
		cm = new ConnectionManager();
		if(!testing){
			setUpManager();
			logged = login();
			return logged;
		}
		return true;
	}
	
	private boolean login(){
		if(logged){
			return true;
		}
		return new FilmAffinityLoginModule(cm, urlBase).login(user, password);
	}
	
	@Override
	public void setUpManager(){
		Map<String, String> configProperties = new UtilTools().getConfiguration();
		if(configProperties == null){
			System.out.println("Couldn't read from config file");
			return;
		}
		
		if(logged){
			new FilmAffinityLoginModule(cm, urlBase).logout();
		}
			
		String faUser = configProperties.get(FILMAFFINITY_USER_AUTH_CONFIG_KEY);		
		if(faUser != null){
			user = faUser;
		}else{
			System.out.println("FilmAffinity user not set.");
		}
		
		String faPassword = configProperties.get(FILMAFFINITY_PASSWORD_AUTH_CONFIG_KEY);
		if(faPassword != null){
			try {
				password = new String(Base64.decode(faPassword), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Couldn't decode FilmAffinity password");
			} catch (IOException e) {
				System.out.println("Couldn't decode FilmAffinity password");
			}
		}else{
			System.out.println("FilmAffinity password not set.");
		}
		logged = false;
	}
	
	public boolean terminateManager(){
		if(!logged){
			return true;
		}
		logged = !logout();
		return !logged;
	}
	
	private boolean logout(){
		if(!logged){
			return true;
		}
		return new FilmAffinityLoginModule(cm, urlBase).logout();
	}
	
	public FichaPelicula[] getRecommendations(Map<String, String> filters){
		if(!logged){
			return null;
		}
		FilmAffinitySearcherModule fasm = new FilmAffinitySearcherModule(urlBase, logged, cm);
		return fasm.lookForRecommendations(filters);
	}

	
	public FichaPelicula[] searchItem(String search, int option){
		return new FilmAffinitySearcherModule(urlBase, logged, cm).searchFilm(search, option);
	}
	
	public FichaPelicula fillFichaPelicula(FichaPelicula film){
		return new FilmAffinitySearcherModule(urlBase, logged, cm).completeFilmDetails(film);
	}
	
	public FichaPelicula getFilmDetails(FichaPelicula pelicula){
		if(pelicula.getFilmDetailsUrl() == null){
			return null;
		}
		URL detailsUrl;
		try{
			detailsUrl = new URI("http", urlBase, pelicula.getFilmDetailsUrl(), null).toURL();
		}catch(MalformedURLException e){
			return null;
		}catch(URISyntaxException e1){
			return null;
		}
		return new FilmAffinitySearcherModule(urlBase, logged, cm).getFilmDetails(detailsUrl);
	}
	
	public boolean rateItem(HelperItem item){
		if(logged){
			return new FilmAffinityVotingModule(urlBase, cm).voteForFilm((FichaPelicula)item);
		}
		return false;
	}
	
	public boolean isLogged(){
		return logged;
	}

	@Override
	public String getHelperName() {
		return HELPERMANAGER_NAME_FILMS;
	}

	@Override
	public int getDefaultSearchOption() {
		return FILMAFFINITY_TITLE_SEARCH_OPTION;
	}

	@Override
	public Map<String, Integer> getSearchOptions() {
		Map<String, Integer> options = new HashMap<String, Integer>();
		options.put("Todo", FILMAFFINITY_ALL_SEARCH_OPTION);
		options.put("Título", FILMAFFINITY_TITLE_SEARCH_OPTION);
		options.put("Director", FILMAFFINITY_DIRECTOR_SEARCH_OPTION);
		options.put("Reparto", FILMAFFINITY_CAST_SEARCH_OPTION);
		return options;
	}

	@Override
	public String getUserConfigKey() {
		return FILMAFFINITY_USER_AUTH_CONFIG_KEY;
	}

	@Override
	public String getPasswordConfigKey() {
		return FILMAFFINITY_PASSWORD_AUTH_CONFIG_KEY;
	}

	@Override
	public boolean isStarted() {
		return isLogged();
	}

	@Override
	public URL getSignUpURL() {
		try {
			return new URL("http://www.filmaffinity.com/es/register.php");
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	public static void main(String[] args){
		FilmAffinityBot.getInstance().initManager(true);
		FichaPelicula[] films = FilmAffinityBot.getInstance().searchItem("oblivion", FILMAFFINITY_TITLE_SEARCH_OPTION);
		for (int i = 0; i < films.length; i++) {
			if(films[i].getTitulo().equals("Land of Oblivion")){
				System.out.println(FilmAffinityBot.getInstance().fillFichaPelicula(films[i]));
			}
		}
	}
}
