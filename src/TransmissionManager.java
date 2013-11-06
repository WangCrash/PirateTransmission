import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import JSON.*;

public class TransmissionManager {

	public static String user = "iresana";
	public static String password = "198iresana";
	
	private static String urlBase = "//iriene.dlinkddns.com:9091/transmission/rpc";
	private static int requestTag = 0;
	private static String transmissionId = "5L38jNKUmN8ZiYP4Htx66kK0yXSn0QubVV2DuKVfuZly6P";
	private static boolean logged = false;
	
	public static void loginOnTranssmission() throws URISyntaxException, MalformedURLException{
		URI loginUri = new URI("http", urlBase, null);
        URL loginUrl = loginUri.toURL();
		try {
			String response = ConnectionManager.getAuthorization(loginUrl);
			System.out.println("Server Response: " + response);
			logged = true;
			TransmissionManager.captureTransmissionId(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Message: " + e.getMessage());
			System.out.println("Cause: " + e.getCause());
			System.out.println("Localized: " + e.getLocalizedMessage());
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
	
	public static void addTorrent(ArchivoTorrent torrent) throws URISyntaxException, MalformedURLException{
		if(!logged){
			System.out.println("Must log in!");
			return;
		}
		String torrentUrl = (torrent.getTorrentUrl() != null)?torrent.getTorrentUrl():torrent.getMagneticLink();
		JSONArray jsonTorrentUrls = new JSONArray();
		jsonTorrentUrls.put(torrentUrl);
		JSONObject jsonArguments = new JSONObject().put("trackerAdd", jsonTorrentUrls);
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.put("method", "torrent-set");
		jsonRequest.put("arguments", jsonArguments);
		jsonRequest.put("tag", requestTag);
		URI uri = new URI("http", urlBase, null);
        URL url = uri.toURL();
        System.out.println(url.toString());
		try {
			String response = ConnectionManager.responseByPostRequest(url, jsonRequest.toString(), transmissionId);
			JSONObject jsonResponse = new JSONObject(response);
			System.out.println(jsonResponse);			
			requestTag++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
}
