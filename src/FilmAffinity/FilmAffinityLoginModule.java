package FilmAffinity;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Connection.ConnectionManager;

public class FilmAffinityLoginModule {
	private ConnectionManager cm;
	private final String urlBase;
	
	public FilmAffinityLoginModule(ConnectionManager cm, String urlbase){
		this.cm = cm;
		this.urlBase = urlbase;
	}
	
	public boolean login(String user, String password){
//		if(logged){
//			return true;
//		}
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
	
	private Map<String, String> doLoginProcess(URL url, Map<String, String> parameters){
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
	
	public boolean logout(){
//		if(!logged){
//			return true;
//		}
		
		URI uri;
		try {
			uri = new URI("http", urlBase, "/es/logout.php", null);
		} catch (URISyntaxException e) {
			return false;
		}
		URL url;
		try {
			url = uri.toURL();
		} catch (MalformedURLException e) {
			return false;
		}
		
		Map<String, String> response = cm.sendRequest(url, ConnectionManager.METHOD_GET, false, true, true);
		if(response != null){
        	return (response.get("ResponseCode").equals("302") && (response.get("Location").equals("/es/logout.php") || response.get("Location").equals("/es/main.html")));
        }
        return false;
	}
}
