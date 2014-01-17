package Managers.Helpers;

import java.util.Map;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Chart;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Session;
import de.umass.lastfm.User;

import Connection.ConnectionManager;
import Model.HelperItem;

public class LastFMManager extends HelperManager {
	private static LastFMManager instance = null;
	
	private final static String USER_AGENT = ConnectionManager.USER_AGENT;
	private final String apiKey = "e316b14648cb44a93d18b79712dd0fa9";
	private final String secret = "459ae1e5e7e640b8848ee2455ba28b9c";
	
	private Session session;
	private String token;
	
	private String user;
	private String password;
	
	private LastFMManager(){
		user = "WaftFunk";
		password = "red1983survivor";
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
		for (Artist artist : recommendations) {
			System.out.println(artist);
		}
		return null;
	}

	@Override
	public boolean rateItem(HelperItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HelperItem[] searchItem(String search, int option) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHelperName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDefaultSearchOption() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Integer> getSearchOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean initManager() {
		//setupManager();
		Caller.getInstance().setUserAgent(USER_AGENT);
		initSession();
		return false;
	}

	private void initSession() {
		//token = Authenticator.getToken(apiKey);
		System.out.println("token: " + token);
		session = Authenticator.getMobileSession(user, password, apiKey, secret);
		System.out.println("session: " + session);
	}

	@Override
	protected void setUpManager() {
	}
	
	public static void main(String[] args){
		LastFMManager.getInstance().initManager();
		LastFMManager.getInstance().getRecommendations();
	}
}
