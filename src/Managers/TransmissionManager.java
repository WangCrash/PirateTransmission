package Managers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Codification.Base64;
import Connection.SimpleConnectionManager;
import JSON.*;
import Model.ArchivoTorrent;
import Utils.UtilTools;

public class TransmissionManager {

	public static String user = "";
	public static String password = "";
	
	private static String urlBase = "//iriene.dlinkddns.com:9091/transmission/rpc";
	private static String transmissionId = "5L38jNKUmN8ZiYP4Htx66kK0yXSn0QubVV2DuKVfuZly6P";
	private static boolean logged = false;
	
	public static boolean loginOnTranssmission() throws URISyntaxException, MalformedURLException{
		URI loginUri = new URI("http", urlBase, null);
        URL loginUrl = loginUri.toURL();
		try {
			//String[] response = ConnectionManager.getAuthorization(loginUrl);
			Map<String, String> httpAuth = new HashMap<String, String>();
			httpAuth.put("USER", user);
			httpAuth.put("PASSWORD", password);
			//Map<String, String> response = ConnectionManager.sendRequest(loginUrl, null, httpAuth, ConnectionManager.METHOD_GET, true, false, false);
			Map<String, String> response = new SimpleConnectionManager().sendGetRequest(loginUrl, null, httpAuth);
			String responseCode = response.get("ResponseCode");
			String responseText = response.get("ResponseBody");
			if(!responseCode.equals("200")){
				if(responseCode.equals("409")){	
					captureTransmissionId(responseText);
					logged = true;
					return true;
				}
			}
			logged = false;
			return logged;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Message: " + e.getMessage());
			System.out.println("Cause: " + e.getCause());
			System.out.println("Localized: " + e.getLocalizedMessage());
			logged = false;
			return logged;
		}
	}
	
	private static void captureTransmissionId(String response){
		String transmissionIdRegex = "<code>X-Transmission-Session-Id:(.*?)</code>";
		Pattern p = Pattern.compile(transmissionIdRegex);
		Matcher m = p.matcher(response);
		//<code>X-Transmission-Session-Id: jSlV7sWsU81qptl1OU8UnniTM6HfIWIL1YOektP7CxeLeFwF</code>
		if(m.find()){
			transmissionId = m.group(1).trim();
		}
	}
	
	public static boolean addTorrent(ArchivoTorrent torrent) throws Exception{
		return addTorrent(torrent, 1);
	}
	
	private static boolean addTorrent(ArchivoTorrent torrent, int intento) throws Exception{
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
	
	
	public static void listTorrents() throws Exception{
		listTorrents(0);
	}
	
	private static void listTorrents(int intento) throws Exception{
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
			
		System.out.println(jsonResponse.toString());
	}
	
	private static JSONObject remoteTransmission(JSONObject jsonRequest, int intento) throws Exception{
		if(intento > 3){
			System.out.println("External Error: Transmission doesn't responds");
			return null;
		}
		URI uri = new URI("http", urlBase, null);
        URL url = uri.toURL();

		//String[] response = ConnectionManager.sendPostRequest(url, jsonRequest.toString(), transmissionId);
        Map<String, String> httpAuth = new HashMap<String, String>();
        //TOKEN_NAME"), httpAuth.get("TOKEN_ID"));
        httpAuth.put("TOKEN_NAME", "X-Transmission-Session-Id");
        httpAuth.put("TOKEN_ID", transmissionId);
        httpAuth.put("USER", user);
		httpAuth.put("PASSWORD", password);
        
		//Map<String, String> response = ConnectionManager.sendRequest(url, jsonRequest.toString(), httpAuth, ConnectionManager.METHOD_POST, true, false, false);
		Map<String, String> response = new SimpleConnectionManager().sendPostRequest(url, jsonRequest.toString(), null);
		String responseCode = response.get("ResponseCode");
		String responseText = response.get("ResponseBody");
		
		if(!responseCode.equals("200")){
			if(responseCode.equals("409")){	
				captureTransmissionId(responseText);
				remoteTransmission(jsonRequest, intento + 1);
			}
		}
		
		return new JSONObject(responseText);
	}
	
	public static boolean initManager() throws IOException, URISyntaxException{
		setUpManager();
		return loginOnTranssmission();
	}
	
	private static void setUpManager(){
		String config = new UtilTools().readConfigFile();
		if(config == null){
			user = "";
			password = "";
			System.out.println("Couldn't read from config file");
		}
		
		String urlBaselRegex = "transmission-rpc-host:(.*?);";
		Pattern p = Pattern.compile(urlBaselRegex);
		Matcher m = p.matcher(config);
		
		if(m.find()){
			urlBase = m.group(1);
			if(!m.group(1).contains("transmission/rpc")){
				System.out.println("transmission rpc host probably not well set");
			}
		}else{
			System.out.println("Transmmission host not set. Will take default.");
		}
		
		String userRegex = "transmission-user:(.*?);";
		p = Pattern.compile(userRegex);
		m = p.matcher(config);
		
		if(m.find()){
			user = m.group(1);
		}else{
			System.out.println("Transmmission user not set.");
		}
		
		String passRegex = "transmission-password:(.*?);";
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
			System.out.println("Transmmission password not set.");
		}
	}
}