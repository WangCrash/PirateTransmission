package Managers;
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


public class FilmAffinityBot extends Manager{
	private static volatile FilmAffinityBot instance = null;
	
	public static final String FILMAFFINITY_USER_AUTH_CONFIG_KEY = "filmaffinity-user";
	public static final String FILMAFFINITY_PASSWORD_AUTH_CONFIG_KEY = "filmaffinity-password";
	
	public static final int FAMS_GENRE_KEY_ALL = -1;
	public static final int FAMS_GENRE_KEY_ACTION = 0;
	public static final int FAMS_GENRE_KEY_ANIMATION = 1;
	public static final int FAMS_GENRE_KEY_ADVENTURE = 2;
	public static final int FAMS_GENRE_KEY_WAR = 3;
	public static final int FAMS_GENRE_KEY_SCIENCE_FICTION = 4;
	public static final int FAMS_GENRE_KEY_NOIR = 5;
	public static final int FAMS_GENRE_KEY_COMEDY = 6;
	public static final int FAMS_GENRE_KEY_UNKNOWN = 7;
	public static final int FAMS_GENRE_KEY_DOCUMENTARY = 8;
	public static final int FAMS_GENRE_KEY_DRAMA = 9;
	public static final int FAMS_GENRE_KEY_FANTASTIC = 10;
	public static final int FAMS_GENRE_KEY_INFANTILE = 11;
	public static final int FAMS_GENRE_KEY_INTRIGUE = 12;
	public static final int FAMS_GENRE_KEY_MUSICAL = 13;
	public static final int FAMS_GENRE_KEY_ROMANCE = 14;
	public static final int FAMS_GENRE_KEY_TV_SERIE = 15;
	public static final int FAMS_GENRE_KEY_TERROR = 16;
	public static final int FAMS_GENRE_KEY_THRILLER = 17;
	public static final int FAMS_GENRE_KEY_WESTERN = 18;
	
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
	
	public FichaPelicula[] getListRecommendations(){
		if(!logged){
			return null;
		}
		return new FilmAffinitySearcherModule(urlBase, logged, cm).lookForRecommendations();
	}
	
	public FichaPelicula[] getListRecommendations(int genre){
		return getListRecommendations(genre, -1, -1, -1);
	}
	
	public FichaPelicula[] getListRecommendations(int genre, int fromYear, int toYear){
		return getListRecommendations(genre, fromYear, toYear, -1);
	}
	
	public FichaPelicula[] getListRecommendations(int genre, int fromYear, int toYear, int limit){
		if(!logged){
			return null;
		}
		FilmAffinitySearcherModule fasm = new FilmAffinitySearcherModule(urlBase, logged, cm);
		Map <String, String> filters = new HashMap<String, String>();
		
		String genreKey = fasm.getGenreKey(genre);
		if(genreKey == null){
			return null;
		}
		filters.put(FilmAffinitySearcherModule.FAMS_FILTERS_GENRE_KEY, genreKey);
		
		String limitString = "20";
		if(limit != -1){
			limitString = String.valueOf(limit);
		}
		filters.put(FilmAffinitySearcherModule.FAMS_FILTERS_LIMIT_KEY, limitString);
		
		String fromYearString = "";
		if(fromYear != -1){
			fromYearString = String.valueOf(fromYear);
		}
		filters.put(FilmAffinitySearcherModule.FAMS_FILTERS_FROM_YEAR_KEY, fromYearString);
		
		String toYearString = "";
		if(toYear != -1){
			toYearString = String.valueOf(toYear);
		}
		filters.put(FilmAffinitySearcherModule.FAMS_FILTERS_TO_YEAR_KEY, toYearString);
		return fasm.lookForRecommendations(filters);
	}
	
	public FichaPelicula[] searchFilm(String search) throws Exception{
		return new FilmAffinitySearcherModule(urlBase, logged, cm).searchFilm(search);
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
	
	public boolean voteForFilm(FichaPelicula film, String rating){
		if(logged){
			return new FilmAffinityVotingModule(urlBase, cm).voteForFilm(film, rating);
		}
		return false;
	}
}
