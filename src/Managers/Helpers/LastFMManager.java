package Managers.Helpers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import Model.ArchivoTorrent;
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
	
	private final static String USER_AGENT = ConnectionManager.USER_AGENT;
	private final String apiKey = "e316b14648cb44a93d18b79712dd0fa9";
	private final String secret = "459ae1e5e7e640b8848ee2455ba28b9c";
	
	private Session session;
	private String token;
	
	private String user;
	private String password;
	
	private LastFMManager(){
		user = "";
		password = "";
		token = "";
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
		// TODO Auto-generated method stub
		return false;
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
			Artista artista = new Artista();
			artista.setMbid(artist.getMbid());
			artista.setNombre(artist.getName());
			artista.setImageURL(artist.getImageURL(ImageSize.MEDIUM));
			artistas.add(artista);
		}
		return Arrays.copyOf(artistas.toArray(), artistas.size(), Artista[].class);
	}
	
	public Artista getArtistTopAlbums(Artista artista){
		if(session == null){
			return artista;
		}
		List<Disco> discografia = new ArrayList<Disco>();
		for (Album album : Artist.getTopAlbums(artista.getNombre(), apiKey)) {
			if(album == null){
				continue;
			}
			Disco disco = new Disco();
			disco.setMbid(album.getMbid());
			disco.setNombre(album.getName());
			disco.setArtista(artista.getNombre());
			disco.setImageURL(album.getImageURL(ImageSize.MEDIUM));
			discografia.add(disco);
		}
		artista.setDiscografia(Arrays.copyOf(discografia.toArray(), discografia.size(), Disco[].class));
		return artista;
	}
	
	public Disco getAlbumTracks(Disco disco){
		if(session == null){
			return disco;
		}
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
	
	@Override
	public HelperItem[] searchItem(String search, int option) {
		switch (option) {
		case LASTFM_ALL_SEARCH_OPTION:
			return searchAll(search);
		case LASTFM_ARTIST_SEARCH_OPTION:
			return searchArtist(search);
		case LASTFM_ALBUM_SEARCH_OPTION:
			return searchAlbum(search);
		default:
			return null;
		}
	}

	private HelperItem[] searchAlbum(String search) {
		Collection<Album> albumResults = Album.search(search, apiKey);
		Disco[] results = new Disco[albumResults.size()];
		int i = 0;
		for (Album album : albumResults) {
			if(album == null){
				continue;
			}
			results[i] = new Disco();
			results[i].setMbid(album.getMbid());
			results[i].setNombre(album.getName());
			results[i].setImageURL(album.getImageURL(ImageSize.MEDIUM));
			i++;
		}
		return results;
	}

	private HelperItem[] searchArtist(String search) {
		Collection<Artist> artistResults = Artist.search(search, apiKey);
		Artista[] results = new Artista[artistResults.size()];
		int i = 0;
		for (Artist artist : artistResults) {
			if(artist == null){
				continue;
			}
			results[i] = new Artista();
			results[i].setMbid(artist.getMbid());
			results[i].setNombre(artist.getName());
			results[i].setImageURL(artist.getImageURL(ImageSize.MEDIUM));
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
	
	public Artista getSimilarArtists(Artista artista){
		Collection<Artist> similarArtists = Artist.getSimilar(artista.getMbid(), 5, apiKey);
		Artista[] similarArtistsArray = new Artista[similarArtists.size()];
		int i = 0;
		for (Artist artist : similarArtists) {
			if(artist == null){
				continue;
			}
			similarArtistsArray[i] = new Artista();
			similarArtistsArray[i].setMbid(artist.getMbid());
			similarArtistsArray[i].setNombre(artist.getName());
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
		options.put("�lbum", LASTFM_ALBUM_SEARCH_OPTION);
		return options;
	}

	@Override
	public boolean initManager() {
		setUpManager();
		Caller.getInstance().setUserAgent(USER_AGENT);
		initSession();
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
	public boolean isStared() {
		return isLogged();
	}
	
	public static void main(String[] args){
		LastFMManager.getInstance().initManager();
		HelperItem[] results = LastFMManager.getInstance().searchItem("Los enemigos", LASTFM_ALL_SEARCH_OPTION);
		for (int i = 0; i < results.length; i++) {
			System.out.println(results[i]);
		}
		System.out.println("Tags de " + ((Artista)results[0]).getNombre() + ":");
		results[0] = LastFMManager.getInstance().getArtistTags((Artista)results[0]);
		String[] tags = ((Artista)results[0]).getNFirstTags(5);
		for (int i = 0; i < tags.length; i++) {
			System.out.println(tags[i]);
		}
		Artista artista = (Artista)results[0];
		artista = LastFMManager.getInstance().getArtistBio(artista);
		System.out.println("Biograf�a de " + artista.getNombre() + ":\n" + artista.getBio());		
	}
}