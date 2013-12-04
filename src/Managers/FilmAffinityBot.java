package Managers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utils.UtilTools;

import Codification.Base64;
import Connection.ConnectionManager;
import FilmAffinity.FilmAffinitySearcherModule;
import FilmAffinity.FilmAffinityVotingModule;

import Model.FichaPelicula;


public class FilmAffinityBot {
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
	
	private static String user = "";
	private static String password = "";
	private static ConnectionManager cm;	
	
	private static String urlBase = "www.filmaffinity.com";
	private static boolean logged = false;
	public static final String LOGGED_TEXT = "Logged";
	
	public static boolean initializeManager(boolean testing){
		cm = new ConnectionManager();
		if(!testing){
			setUpManager();
			logged = login();
			return logged;
		}
		return true;
	}
	
	public static boolean terminateManager(){
		if(!logged){
			return true;
		}
		try {
			logged = !logout();
			return !logged;
		} catch (MalformedURLException e) {
			return false;
		} catch (URISyntaxException e) {
			return false;
		} 
	}
	
	public static FichaPelicula[] getListRecommendations(){
		if(!logged){
			return null;
		}
		return new FilmAffinitySearcherModule(urlBase, logged, cm).lookForRecommendations();
	}
	
	public static FichaPelicula[] getListRecommendations(int genre){
		return getListRecommendations(genre, -1, -1, -1);
	}
	
	public static FichaPelicula[] getListRecommendations(int genre, int fromYear, int toYear){
		return getListRecommendations(genre, fromYear, toYear, -1);
	}
	
	public static FichaPelicula[] getListRecommendations(int genre, int fromYear, int toYear, int limit){
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
	
	public static FichaPelicula[] searchFilm(String search) throws Exception{
		return new FilmAffinitySearcherModule(urlBase, logged, cm).searchFilm(search);
	}
	
	public static FichaPelicula fillFichaPelicula(FichaPelicula film){
		return new FilmAffinitySearcherModule(urlBase, logged, cm).completeFilmDetails(film);
	}
	
	public static FichaPelicula getFilmDetails(FichaPelicula pelicula){
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
	
	public static boolean voteForFilm(FichaPelicula film, String rating){
		if(logged){
			return new FilmAffinityVotingModule(urlBase, cm).voteForFilm(film, rating);
		}
		return false;
	}
	
	private static boolean logout() throws URISyntaxException, MalformedURLException{
		if(!logged){
			return true;
		}
		
		URI uri = new URI("http", urlBase, "/es/logout.php", null);
		URL url = uri.toURL();
		
		Map<String, String> response = cm.sendRequest(url, ConnectionManager.METHOD_GET, false, true, true);
		if(response != null){
        	return (response.get("ResponseCode").equals("302") && (response.get("Location").equals("/es/logout.php") || response.get("Location").equals("/es/main.html")));
        }
        return false;
	}
	
	private static boolean login(){
		if(logged){
			return true;
		}
		URI uri;
		URL url;
		try {
			uri = new URI("http", urlBase,  "/es/login.php", null);
			url = uri.toURL();
		} catch (URISyntaxException e) {
			return false;
		} catch (MalformedURLException e) {
			return false;
		}
        Map<String, String> data = new HashMap<String, String>();
        data.put("rp", "");
        data.put("user", user);
        data.put("password", password);
        data.put("postback", "1");
        data.put("ok", "Enviar");
        Map<String, String> response = doLoginProcess(url, data);
        if(response != null){
        	return (response.get("ResponseCode").equals("302") && response.get("Location").equals("/es/main.html"));
        }
        return false;
	}
	
	private static Map<String, String> doLoginProcess(URL url, Map<String, String> parameters){
		Set<String> keys = parameters.keySet();
		Iterator<String> keyIter = keys.iterator();
		String parametersChain = "";
		for(int i=0; keyIter.hasNext(); i++) {
			String key = keyIter.next();
			if(i!=0) {
				parametersChain += "&";
			}
			parametersChain += key + "=" + parameters.get(key);
		}
		Map<String, String> postResponse = cm.sendRequest(url, parametersChain, false, null, ConnectionManager.METHOD_POST, false, true, false);
		if(postResponse == null){
			return null;
		}
		int responseCode;
		try{
			responseCode = Integer.parseInt(postResponse.get("ResponseCode"));
			System.out.println("responseCode: " + responseCode);
		}catch(NumberFormatException exception){
			return null;
		}
		if((responseCode == HttpURLConnection.HTTP_MOVED_TEMP) && (postResponse.containsKey("Location"))){
			try {
				url = new URL(url, postResponse.get("Location"));
			} catch (MalformedURLException e) {
				return null;
			}
			return cm.sendRequest(url, ConnectionManager.METHOD_GET, false, false, true);
		}
		return null;
	}
	
	private static void setUpManager(){
		String config = new UtilTools().readConfigFile();
		if(config == null){
			user = "";
			password = "";
			System.out.println("Couldn't read from config file");
		}
			
		String userRegex = "filmaffinity-user:(.*?);";
		Pattern p = Pattern.compile(userRegex);
		Matcher m = p.matcher(config);
		
		if(m.find()){
			user = m.group(1);
		}else{
			System.out.println("FilmAffinity user not set.");
		}
		
		String passRegex = "filmaffinity-password:(.*?);";
		p = Pattern.compile(passRegex);
		m = p.matcher(config);
		
		if(m.find()){
			try {
				password = new String(Base64.decode(m.group(1)), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				password = "";
				System.out.println("Couldn't decode FilmAffinity password");
			} catch (IOException e) {
				password = "";
				System.out.println("Couldn't decode FilmAffinity password");
			}
		}else{
			System.out.println("FilmAffinity password not set.");
		}
	}
}
