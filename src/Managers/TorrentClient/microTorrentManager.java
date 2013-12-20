package Managers.TorrentClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import Codification.Base64;
import Connection.ConnectionManager;
import Connection.SimpleConnectionManager;
import JSON.JSONException;
import JSON.JSONObject;
import Model.ArchivoTorrent;
import Utils.UtilTools;


public class microTorrentManager extends TorrentClient{
	private static volatile microTorrentManager instance = null;
	
	public static final String MICROTORRENT_RPC_SERVER_CONFIG_KEY = "microtorrent-rpc-host";
	public static final String MICROTORRENT_USER_AUTH_CONFIG_KEY = "microtorrent-user";
	public static final String MICROTORRENT_PASSWORD_AUTH_CONFIG_KEY = "microtorrent-password";
	
	private static final String LOGIN_COMMANDO_REQUEST_KEY = ""; 

	public String user;
	public String password;
	public String urlBase;
	
	private String token = "";
	private Map<String, String> httpAuth;
	private boolean logged = false;
	private ConnectionManager cm;
	
	private microTorrentManager(){
		urlBase = "";
		user = "";
		password = "";
		cm = new ConnectionManager();
	}
	
	public static microTorrentManager getInstance(){
		synchronized (microTorrentManager.class) {
			if(instance == null){
				instance = new microTorrentManager();
			}
		}
		return instance;
	}
	
	@Override
	public void setUpManager(){
		Map<String, String> configProperties = new UtilTools().getConfiguration();
		if(configProperties == null){
			System.out.println("Couldn't read from config file");
			return;
		}
		String server = configProperties.get(MICROTORRENT_RPC_SERVER_CONFIG_KEY);
		if(server != null){
			urlBase = server;
			if(!server.contains("gui")){
				if(!server.endsWith("/")){
					urlBase += "/";
				}
				urlBase += "gui/";
			}
		}
		
		String microTorrentUser = configProperties.get(MICROTORRENT_USER_AUTH_CONFIG_KEY);		
		if(user != null){
			user = microTorrentUser;
		}else{
			System.out.println("microTorrent user not set.");
		}
		
		String microTorrentPassword = configProperties.get(MICROTORRENT_PASSWORD_AUTH_CONFIG_KEY);
		if(microTorrentPassword != null){
			try {
				password = new String(Base64.decode(microTorrentPassword), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Couldn't decode microTorrent password");
			} catch (IOException e) {
				System.out.println("Couldn't decode microTorrent password");
			}
		}else{
			System.out.println("microTorrent password not set.");
		}
		logged = false;
	}
	
	@Override
	public boolean initManager(){
		setUpManager();
		return loginOnMicroTorrent();
	}
	
	private boolean loginOnMicroTorrent(){
	
		httpAuth = new HashMap<String, String>();
		httpAuth.put(SimpleConnectionManager.USER_BASIC_AUTH_KEY, user);
		httpAuth.put(SimpleConnectionManager.PASSWORD_BASIC_AUTH_KEY, password);
		
		String response = microTorrentRequest(LOGIN_COMMANDO_REQUEST_KEY);
	
		logged = false;
		if(response != null){
			if(requestToken()){
				System.out.println(token);
				logged = true;
				return true;
			}
		}
		return logged;
	}
	
	private boolean requestToken(){
		
		String response = microTorrentRequest("token.html");
	
		if(response != null){
			token = captureToken(response);
			System.out.println(token);
			return true;
		}
		return false;
	}
	private String captureToken(String responseText) {
		String tokenRegex = "<div id='token'.*?>(.*?)</div>";
		return this.getTokenFromHTML(tokenRegex, responseText);
	}

	public boolean addTorrent(ArchivoTorrent torrent){
		if(!logged){
			System.out.println("Internal Error: Must log in!");
			return false;
		}
		String torrentUrlString = (torrent.getTorrentUrl() != null)?torrent.getTorrentUrl():torrent.getMagneticLink();
		String query = initializeQuery() + "action=add-url&s=" + torrentUrlString;
		
		String response = microTorrentRequest(query);
		JSONObject jsonResponse = null;
		if(response != null){
			jsonResponse = null;
			try{
				jsonResponse = new JSONObject(response);
			}catch(JSONException e){
				System.out.println(response);
				return false;
			}
			System.out.println(jsonResponse);
			return true;
		}
		return false;
		//http://[IP]:[PUERTO]/gui/?action=add-url&s=[URL DE TORRENT]
	}
	
	private String microTorrentRequest(String command){
		URI uri;
		try {
			uri = new URI("http", urlBase + command, null);
		} catch (URISyntaxException e2) {
			System.out.println(e2.getMessage());
			return null;
		}
		System.out.println(uri);
        URL url;
		try {
			url = uri.toURL();
		} catch (MalformedURLException e1) {
			return null;
		}
		
		boolean retrieveResponse = !(LOGIN_COMMANDO_REQUEST_KEY.equals(command));
		Map<String, String> response = cm.sendRequest(url, null, false, httpAuth, ConnectionManager.METHOD_GET, retrieveResponse, true, true);
		
		if(response.get(ConnectionManager.STATUS_CODE_RESPONSE_KEY).equals("200")){
			if(LOGIN_COMMANDO_REQUEST_KEY.equals(command)){
				return "200";
			}else{
				return response.get(ConnectionManager.BODY_TEXT_RESPONSE_KEY);
			}
		}
		return null;
	}
	
	public void listTorrents(){
		String query = initializeQuery() + "list=1";
		
		String response = microTorrentRequest(query);
		
		if(response != null){
			JSONObject jsonResponse = null;
			try{
				jsonResponse = new JSONObject(response);
			}catch(JSONException e){
				System.out.println("Error: invalid response");
			}
			System.out.println(jsonResponse);
		}
	}
	
	private String initializeQuery(){
		return "?token=" + token + "&";
	}

	@Override
	public String getServerConfigKey() {
		return MICROTORRENT_RPC_SERVER_CONFIG_KEY;
	}

	@Override
	public String getUserConfigKey() {
		return MICROTORRENT_USER_AUTH_CONFIG_KEY;
	}

	@Override
	public String getPasswordConfigKey() {
		return MICROTORRENT_PASSWORD_AUTH_CONFIG_KEY;
	}
}

