package Managers.Helpers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Caller;
import de.umass.lastfm.ImageSize;
import de.umass.lastfm.Library;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Result;
import de.umass.lastfm.Session;
import de.umass.lastfm.Tag;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;
import Codification.Base64;
import Connection.ConnectionManager;
import Connection.SimpleConnectionManager;
import JSON.JSONArray;
import JSON.JSONObject;
import Model.Artista;
import Model.Disco;
import Model.HelperItem;
import Utils.UtilTools;

public class LastFMManager extends HelperManager {
	private static LastFMManager instance = null;
	
	public static final String LASTFM_USER_AUTH_CONFIG_KEY = "lastfm-user";
	public static final String LASTFM_PASSWORD_AUTH_CONFIG_KEY = "lastfm-password";
	
	public static final int LASTFM_ALL_SEARCH_OPTION = 0;
	public static final int LASTFM_ARTIST_SEARCH_OPTION = 1;
	public static final int LASTFM_ALBUM_SEARCH_OPTION = 2;
	public static final int LASTFM_TAG_SEARCH_OPTION = 3;
	
	private final static String USER_AGENT = ConnectionManager.USER_AGENT;
	private final String apiKey = "e316b14648cb44a93d18b79712dd0fa9";
	private final String secret = "459ae1e5e7e640b8848ee2455ba28b9c";
	
	private Session session;
	private String token;
	
	private String user;
	private String password;
	
	private List<String> libraryArtists;
	private List<String> libraryAlbums;
	
	private Thread daemon;
	private long minutesToReCheck;
	
	private LastFMManager(){
		user = "";
		password = "";
		token = "";
		libraryArtists = null;
		libraryAlbums = null;
	}
	
	public static LastFMManager getInstance(){
		synchronized (LastFMManager.class) {
			if(instance == null){
				instance = new LastFMManager();
			}
		}
		return instance;
	}

	@Override
	public boolean isLogged() {
		return (session != null);
	}
	
	public boolean logout(){
		if(session == null){
			return true;
		}
		stopDaemon();
		session = null;
		return true;
	}
	
	public HelperItem[] getRecommendations() {
		return getRecommendations(null);
	}

	@Override
	public HelperItem[] getRecommendations(Map<String, String> filters) {
		if(session == null){
			return null;
		}
		PaginatedResult<Artist> recommendations = User.getRecommendedArtists(session);
		int page = 1;
		if(page != 1){
			for (int i = 1; i <= page; i++) {
				recommendations = User.getRecommendedArtists(session);
			}
		}
		System.out.println("Num paginas: " + recommendations.getTotalPages());
		System.out.println("Pagina: " + recommendations.getPage());
		List<Artista> artistas = new ArrayList<Artista>();
		for (Artist artist : recommendations.getPageResults()) {
			if(artist == null){
				continue;
			}
			Artista artista = new Artista(artist);
			artistas.add(artista);
		}
		return Arrays.copyOf(artistas.toArray(), artistas.size(), Artista[].class);
	}
	
	public Artista getArtistTopAlbums(Artista artista){
		List<Disco> discografia = new ArrayList<Disco>();
		for (Album album : Artist.getTopAlbums(artista.getNombre(), apiKey)) {
			if(album == null){
				continue;
			}
			Disco disco = new Disco(album);
			discografia.add(disco);
		}
		artista.setDiscografia(Arrays.copyOf(discografia.toArray(), discografia.size(), Disco[].class));
		return artista;
	}
	
	public Disco getAlbumTracks(Disco disco){
		Album album = Album.getInfo(disco.getArtista(), disco.getMbid(), apiKey);
		List<String> tracks = new ArrayList<String>();
		for (Track track : album.getTracks()) {
			if(track == null){
				continue;
			}
			tracks.add(track.getName());
		}
		disco.setCanciones(Arrays.copyOf(tracks.toArray(), tracks.size(), String[].class));
		return disco;
	}

	@Override
	public boolean rateItem(HelperItem item) {
		if(session == null){
			return false;
		}
		if(item.getClass() == Artista.class){
			return rateArtist((Artista)item);
		}else if(item.getClass() == Disco.class){
			return rateAlbum((Disco)item);
		}
		return false;
	}
	
	private boolean rateArtist(Artista artista){
		Result result = Library.addArtist(artista.getNombre(), session);
		System.out.println(result);
		return result.isSuccessful();
	}
	
	private boolean rateAlbum(Disco album){
		return Library.addAlbum(album.getArtista(), album.getNombre(), session).isSuccessful();
	}
	
	public boolean removeFromLibrary(HelperItem item){
		if(session == null){
			return false;
		}
		if(item.getClass() == Artista.class){
			return removeArtist((Artista)item);
		}else if(item.getClass() == Disco.class){
			return removeAlbum((Disco)item);
		}
		return false;
	}
	
	private boolean removeArtist(Artista artista){
		Result result = Library.removeArtist(artista.getNombre(), session);
		return result.isSuccessful();
	}
	
	private boolean removeAlbum(Disco disco){
		Result result = Library.removeAlbum(disco.getArtista(), disco.getNombre(), session);
		return result.isSuccessful();
	}
	
	@Override
	public HelperItem[] searchItem(String search, int option) {
		switch (option) {
		case LASTFM_ALL_SEARCH_OPTION:
			return searchAll(search);
		case LASTFM_ARTIST_SEARCH_OPTION:
			return searchArtist(search);
		case LASTFM_ALBUM_SEARCH_OPTION:
			return searchAlbum(search);
		case LASTFM_TAG_SEARCH_OPTION:
			return searchTag(search);
		default:
			return null;
		}
	}
	
	private HelperItem[] searchArtist(String search) {
		Collection<Artist> artistResults = Artist.search(search, apiKey);
		Artista[] results = new Artista[artistResults.size()];
		int i = 0;
		for (Artist artist : artistResults) {
			if(artist == null){
				continue;
			}
			results[i] = new Artista(artist);
			results[i].setIsRated(isArtistInLibrary(artist, libraryArtists));
			i++;
		}
		return results;
	}
	
	private HelperItem[] searchAlbum(String search) {
		Collection<Album> albumResults = Album.search(search, apiKey);
		Disco[] results = new Disco[albumResults.size()];
		int i = 0;
		for (Album album : albumResults) {
			if(album == null){
				continue;
			}
			results[i] = new Disco(album);
			results[i].setIsRated(isAlbumInLibrary(album, libraryAlbums));
			i++;
		}
		return results;
	}

	private HelperItem[] searchAll(String search) {
		HelperItem[] artistResults = searchArtist(search);
		HelperItem[] albumsResults = searchAlbum(search);
		HelperItem[] total = new HelperItem[artistResults.length + albumsResults.length];
		System.arraycopy(artistResults, 0, total, 0, artistResults.length);
		System.arraycopy(albumsResults, 0, total, artistResults.length, albumsResults.length);
		return total;
	}
	
	private boolean isArtistInLibrary(Artist artist, List<String> libraryArtists){
		if(libraryArtists == null){
			return false;
		}
		synchronized (libraryArtists) {
			return libraryArtists.contains(artist.getName());
		}
	}
	
	private boolean isAlbumInLibrary(Album album, List<String> libraryAlbums){
		if(libraryAlbums == null){
			return false;
		}
		synchronized (libraryAlbums) {
			return libraryAlbums.contains(album.getArtist() + "||" + album.getName());
		}
	}
	
	private HelperItem[] searchTag(String search) {
		Collection<Artist> tagResults = Tag.getTopArtists(search, apiKey);
		Artista[] results = new Artista[tagResults.size()];
		int i = 0;
		for (Artist artist : tagResults) {
			if(artist == null){
				continue;
			}
			results[i] = new Artista(artist);
			results[i].setIsRated(isArtistInLibrary(artist, libraryAlbums));
			i++;
		}
		return results;
	}

	private List<String> getLibraryArtists(){
		return getLibraryItems("http://ws.audioscrobbler.com/2.0/?method=library.getartists&api_key=" + apiKey + "&user=" + user, "artist");
	}
	
	private List<String> getLibraryAlbums(){
		return getLibraryItems("http://ws.audioscrobbler.com/2.0/?method=library.getalbums&api_key=" + apiKey + "&user=" + user, "album");
	}
	
	private List<String> getLibraryItems(String libraryUrlString, String type){
		URL url;
		try {
			url = new URL(libraryUrlString + "&page=1&format=json");
		} catch (MalformedURLException e) {
			return null;
		}
		Map<String, String> response = new SimpleConnectionManager().sendGetRequest(url);
		int responseCode;
		try{
			responseCode = Integer.parseInt(response.get(ConnectionManager.STATUS_CODE_RESPONSE_KEY));
		}catch(NumberFormatException e){
			return null;
		}
		List<String> itemsNames = null;
		if(responseCode == HttpURLConnection.HTTP_OK){
			JSONObject libraryObject = new JSONObject(response.get(ConnectionManager.BODY_TEXT_RESPONSE_KEY));
			System.out.println(libraryObject);
			JSONObject attributes = libraryObject.getJSONObject(type + "s").getJSONObject("@attr");
			int totalPages = attributes.getInt("totalPages");
			itemsNames = new ArrayList<String>();
			JSONArray items = libraryObject.getJSONObject(type + "s").getJSONArray(type);
			itemsNames = getNamesFromJSON(itemsNames, items, type);
			if(totalPages > 1){
				itemsNames = getLibraryItemsRecursively(libraryUrlString, itemsNames, 2, totalPages, type);
			}
		}
		return itemsNames;

	}
	
	private List<String> getLibraryItemsRecursively(String libraryUrlString, List<String> itemsNames, int initPage, int totalPages, String type){
		if(totalPages < initPage){
			return itemsNames;
		}
		URL url;
		try {
			url = new URL(libraryUrlString + "&page=" + initPage + "&format=json");
		} catch (MalformedURLException e) {
			System.out.println("Malformed url: " + libraryUrlString + "&page=" + initPage + "&format=json");
			return itemsNames;
		}
		
		Map<String, String> response = new SimpleConnectionManager().sendGetRequest(url);
		int responseCode;
		try{
			responseCode = Integer.parseInt(response.get(ConnectionManager.STATUS_CODE_RESPONSE_KEY));
		}catch(NumberFormatException e){
			return itemsNames;
		}
		if(responseCode == HttpURLConnection.HTTP_OK){
			System.out.println("Page " + initPage);
			JSONObject libraryObject = new JSONObject(response.get(ConnectionManager.BODY_TEXT_RESPONSE_KEY));
			JSONArray itemsArray = libraryObject.getJSONObject(type + "s").getJSONArray(type);	
			itemsNames = getLibraryItemsRecursively(libraryUrlString, getNamesFromJSON(itemsNames, itemsArray, type), initPage + 1, totalPages, type);
		}
		return itemsNames;
	}
	
	private List<String> getNamesFromJSON(List<String> itemsNames, JSONArray itemsArray, String type){
		if(itemsNames == null || itemsArray == null){
			return null;
		}
		for (int i = 0; i < itemsArray.length(); i++) {
			String item = itemsArray.getJSONObject(i).get("name").toString();
			if(type.equals("album")){
				JSONObject artist = itemsArray.getJSONObject(i).getJSONObject("artist");
				if(artist != null){
					item = artist.getString("name") + "||" + item;
				}
			}
			itemsNames.add(item);
		}
		return itemsNames;
	}
	
	public Artista getSimilarArtists(Artista artista){
		Collection<Artist> similarArtists = Artist.getSimilar(artista.getNombre(), 5, apiKey);
		Artista[] similarArtistsArray = new Artista[similarArtists.size()];
		int i = 0;
		for (Artist artist : similarArtists) {
			if(artist == null){
				continue;
			}
			similarArtistsArray[i] = new Artista(artist);
			i++;
		}
		artista.setSimilares(similarArtistsArray);
		return artista;
	}
	
	public Artista getArtistTags(Artista artista){
		Collection<Tag> tags = Artist.getTopTags(artista.getNombre(), apiKey);
		String[] tagsNames = new String[tags.size()];
		int i = 0;
		for (Tag tag : tags) {
			tagsNames[i] = tag.getName();
			i++;
		}
		artista.setTags(tagsNames);
		return artista;
	}
	
	public Artista getArtistBio(Artista artista){
		Artist artist = Artist.getInfo(artista.getMbid(), user, apiKey);
		artista.setBio(artist.getWikiText());
		return artista;
	}
	
	public Disco getAlbumTags(Disco disco){
		Collection<Tag> tags = Album.getTopTags(disco.getArtista(), disco.getNombre(), apiKey);
		String[] tagsNames = new String[tags.size()];
		int i = 0;
		for (Tag tag : tags) {
			tagsNames[i] = tag.getName();
			i++;
		}
		disco.setTags(tagsNames);
		return disco;
	}
	
	public Disco getAlbumWikiText(Disco disco){
		Album album = Album.getInfo(disco.getArtista(), disco.getMbid(), user, apiKey);
		disco.setWikiText(album.getWikiText());
		return disco;
	}

	@Override
	public String getHelperName() {
		return HELPERMANAGER_NAME_MUSIC;
	}

	@Override
	public int getDefaultSearchOption() {
		return LASTFM_ALL_SEARCH_OPTION;
	}

	@Override
	public Map<String, Integer> getSearchOptions() {
		Map<String, Integer> options = new HashMap<String, Integer>();
		options.put("Todo", LASTFM_ALL_SEARCH_OPTION);
		options.put("Artista", LASTFM_ARTIST_SEARCH_OPTION);
		options.put("Álbum", LASTFM_ALBUM_SEARCH_OPTION);
		return options;
	}

	@Override
	public boolean initManager() {
		setUpManager();
		if(user != null && !user.isEmpty()){
			Caller.getInstance().setUserAgent(USER_AGENT);
			initSession();
			minutesToReCheck = 10 * 60 * 1000;
			if(session != null){
				watchForLibraryDaemon();
			}
		}
		return (session != null);
	}

	private void initSession() {
		System.out.println("token: " + token);
		session = Authenticator.getMobileSession(user, password, apiKey, secret);
		System.out.println("session: " + session);
	}

	@Override
	protected void setUpManager() {
		Map<String, String> configProperties = new UtilTools().getConfiguration();
		if(configProperties == null){
			System.out.println("Couldn't read from config file");
			return;
		}
			
		String lFMUser = configProperties.get(LASTFM_USER_AUTH_CONFIG_KEY);		
		if(lFMUser != null){
			user = lFMUser;
		}else{
			System.out.println("LastFM user not set.");
		}
		
		String lFMPassword = configProperties.get(LASTFM_PASSWORD_AUTH_CONFIG_KEY);
		if(lFMPassword != null){
			try {
				password = new String(Base64.decode(lFMPassword), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Couldn't decode LastFM password");
			} catch (IOException e) {
				System.out.println("Couldn't decode LastFM password");
			}
		}else{
			System.out.println("LastFM password not set.");
		}
	}

	@Override
	public String getUserConfigKey() {
		return LASTFM_USER_AUTH_CONFIG_KEY;
	}

	@Override
	public String getPasswordConfigKey() {
		return LASTFM_PASSWORD_AUTH_CONFIG_KEY;
	}

	@Override
	public boolean isStarted() {
		return isLogged();
	}

	@Override
	public URL getSignUpURL() {
		try {
			return new URL("http://www.lastfm.es/join");
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	public void testing(){
//		List<String> list = getLibraryArtists();
//		for (String string : list) {
//			System.out.println("Artist: " + string);
//		}
		List<String> list = getLibraryAlbums();
		for (String string : list) {
			System.out.println("Album: " + string);
		}
	}
	
	private void watchForLibraryDaemon(){
		daemon = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("iniciando proceso...");
				while(true){
					Date start = new Date();
					List<String> artists = getLibraryArtists();
					Date end = new Date();
					System.out.println("artistas de la coleccion en: " + (end.getTime() - start.getTime()));
					synchronized (artists) {
						libraryArtists = artists;
					}
					List<String> albums = getLibraryAlbums();
					synchronized (albums) {
						libraryAlbums = albums;
					}
					try {
						DateFormat df = new SimpleDateFormat("HH:mm:ss");
						Date today = Calendar.getInstance().getTime();        
						String reportDate = df.format(today);
						System.out.println("[" +  reportDate + "]: durmiendo...");
						Thread.sleep(minutesToReCheck);
					} catch (InterruptedException e) {
						System.out.println("Interrupted!!!!");
						return;
					}
					System.out.println("repitiendo proceso...");
				}
			}
		});
		daemon.start();
	}
	
	private void stopDaemon(){
		if(daemon != null){
			daemon.interrupt();
		}
	}
	
	public static void main(String[] args){
		LastFMManager.getInstance().initManager();
	}
}
