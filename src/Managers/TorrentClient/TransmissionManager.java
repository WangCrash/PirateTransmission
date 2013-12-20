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
import Connection.SimpleConnectionManager;
import JSON.*;
import Model.ArchivoTorrent;
import Utils.UtilTools;

public class TransmissionManager extends TorrentClient{
	private static volatile TransmissionManager instance = null;
	
	public static final String TRANSMISSION_RPC_SERVER_CONFIG_KEY = "transmission-rpc-host";
	public static final String TRANSMISSION_USER_AUTH_CONFIG_KEY = "transmission-user";
	public static final String TRANSMISSION_PASSWORD_AUTH_CONFIG_KEY = "transmission-password";

	public String user;
	public String password;
	public String urlBase;
	
	private String transmissionId = "5L38jNKUmN8ZiYP4Htx66kK0yXSn0QubVV2DuKVfuZly6P";
	private boolean logged = false;
	
	private TransmissionManager() {
		urlBase = "";
		user = "";
		password = "";
	}
	
	public static TransmissionManager getInstance(){
		synchronized (TransmissionManager.class) {
			if(instance == null){
				instance = new TransmissionManager();
			}
		}
		return instance;
	}
	
	private boolean loginOnTranssmission(){
		URI loginUri;
		try {
			loginUri = new URI("http", urlBase, null);
		} catch (URISyntaxException e2) {
			return false;
		}
        URL loginUrl;
		try {
			loginUrl = loginUri.toURL();
		} catch (MalformedURLException e1) {
			return false;
		}
		try {
			//String[] response = ConnectionManager.getAuthorization(loginUrl);
			Map<String, String> httpAuth = new HashMap<String, String>();
			httpAuth.put(SimpleConnectionManager.USER_BASIC_AUTH_KEY, user);
			httpAuth.put(SimpleConnectionManager.PASSWORD_BASIC_AUTH_KEY, password);
			//Map<String, String> response = ConnectionManager.sendRequest(loginUrl, null, httpAuth, ConnectionManager.METHOD_GET, true, false, false);
			Map<String, String> response = new SimpleConnectionManager().sendGetRequest(loginUrl, null, httpAuth);
			String responseCode = response.get("ResponseCode");
			String responseText = response.get("ResponseBody");
			if(!responseCode.equals("200")){
				if(responseCode.equals("409")){	
					transmissionId = captureTransmissionId(responseText);
					if(transmissionId != null){
						logged = true;
						return true;
					}
					return false;
				}
			}
			logged = false;
			return logged;
		} catch (Exception e) {
			System.out.println("Message: " + e.getMessage());
			System.out.println("Cause: " + e.getCause());
			System.out.println("Localized: " + e.getLocalizedMessage());
			logged = false;
			return logged;
		}
	}
	
	private String captureTransmissionId(String response){
		String transmissionIdRegex = "<code>X-Transmission-Session-Id:(.*?)</code>";
		return this.getTokenFromHTML(transmissionIdRegex, response);
	}
	
	public boolean addTorrent(ArchivoTorrent torrent){
		return addTorrent(torrent, 1);
	}
	
	private boolean addTorrent(ArchivoTorrent torrent, int intento){
		if(!logged){
			System.out.println("Internal Error: Must log in!");
			return false;
		}
		
		String torrentUrl = (torrent.getTorrentUrl() != null)?torrent.getTorrentUrl():torrent.getMagneticLink();
		
		JSONObject jsonArguments = new JSONObject().put("filename", torrentUrl);
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("method", "torrent-add");
		jsonRequest.put("arguments", jsonArguments);
				
		JSONObject jsonResponse = remoteTransmission(jsonRequest, 0);
		
		if(jsonResponse == null){
			return false;
		}
		String result = (String) jsonResponse.get("result");
			
		return result.equalsIgnoreCase("success");
	}
	
	
	public void listTorrents() throws Exception{
		listTorrents(0);
	}
	
	private void listTorrents(int intento) throws Exception{
		if(!logged){
			System.out.println("Internal Error: Must log in!");
			return;
		}
		
		String[] fields = new String[]{"id", "name", "creator", "comment", "percentDone", "status", "trackers", "webseeds", "torrentFile"};
		
		JSONArray jsonFields = new JSONArray();

		for (int i = 0; i < fields.length; i++) {
			jsonFields.put(fields[i]);
		}
		//JSONObject jsonArguments = new JSONObject().put("trackerAdd", jsonTorrentUrls);
		JSONObject jsonArguments = new JSONObject().put("fields", jsonFields);
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("method", "torrent-get");
		jsonRequest.put("arguments", jsonArguments);
		
		JSONObject jsonResponse = remoteTransmission(jsonRequest, 0);
		if(jsonResponse != null){	
			System.out.println(jsonResponse.toString());
		}
	}
	
	private JSONObject remoteTransmission(JSONObject jsonRequest, int intento){
		if(intento > 3){
			System.out.println("External Error: Transmission doesn't responds");
			return null;
		}
		URI uri;
		URL url;
		try{
			uri = new URI("http", urlBase, null);
			url = uri.toURL();
		}catch(URISyntaxException e1){
			return null;
		}catch(MalformedURLException e){
			return null;
		}

		//String[] response = ConnectionManager.sendPostRequest(url, jsonRequest.toString(), transmissionId);
        Map<String, String> httpAuth = new HashMap<String, String>();
        //TOKEN_NAME"), httpAuth.get("TOKEN_ID"));
        httpAuth.put(SimpleConnectionManager.TOKEN_NAME_BASIC_AUTH_KEY, "X-Transmission-Session-Id");
        httpAuth.put(SimpleConnectionManager.TOKEN_ID_BASIC_AUTH_KEY, transmissionId);
        httpAuth.put(SimpleConnectionManager.USER_BASIC_AUTH_KEY, user);
		httpAuth.put(SimpleConnectionManager.PASSWORD_BASIC_AUTH_KEY, password);
        
		//Map<String, String> response = ConnectionManager.sendRequest(url, jsonRequest.toString(), httpAuth, ConnectionManager.METHOD_POST, true, false, false);
		Map<String, String> response = new SimpleConnectionManager().sendPostRequest(url, jsonRequest.toString(), httpAuth);
		String responseCode = response.get("ResponseCode");
		String responseText = response.get("ResponseBody");
		
		if(!responseCode.equals("200")){
			if(responseCode.equals("409")){	
				transmissionId = captureTransmissionId(responseText);
				if(transmissionId != null){
					remoteTransmission(jsonRequest, intento + 1);
				}else{
					return null;
				}
			}
		}
		
		try{
			return new JSONObject(responseText);
		}catch(JSONException e){
			System.out.println(responseText);
			return null;
		}
	}
	
	public boolean initManager(){
		setUpManager();
		return loginOnTranssmission();
	}
	
	@Override
	public void setUpManager(){
		Map<String, String> configProperties = new UtilTools().getConfiguration();
		if(configProperties == null){
			System.out.println("Couldn't read from config file");
			return;
		}
		String server = configProperties.get(TRANSMISSION_RPC_SERVER_CONFIG_KEY);
		if(server != null){
			urlBase = server;
			if(!server.contains("transmission/rpc")){
				urlBase += "/transmission/rpc";
			}
		}
		
		String transmissionUser = configProperties.get(TRANSMISSION_USER_AUTH_CONFIG_KEY);		
		if(user != null){
			user = transmissionUser;
		}else{
			System.out.println("Transmmission user not set.");
		}
		
		String transmissionPassword = configProperties.get(TRANSMISSION_PASSWORD_AUTH_CONFIG_KEY);
		if(transmissionPassword != null){
			try {
				password = new String(Base64.decode(transmissionPassword), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Couldn't decode Transmission password");
			} catch (IOException e) {
				System.out.println("Couldn't decode Transmission password");
			}
		}else{
			System.out.println("Transmmission password not set.");
		}
		logged = false;
	}

	@Override
	public String getServerConfigKey() {
		return TRANSMISSION_RPC_SERVER_CONFIG_KEY;
	}

	@Override
	public String getUserConfigKey() {
		return TRANSMISSION_USER_AUTH_CONFIG_KEY;
	}

	@Override
	public String getPasswordConfigKey() {
		return TRANSMISSION_PASSWORD_AUTH_CONFIG_KEY;
	}
}
