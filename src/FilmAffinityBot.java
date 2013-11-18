import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class FilmAffinityBot {
	public static String user = "";
	public static String password = "";
	
	private static String urlBase = "www.filmaffinity.com";
	private static boolean logged = false;
	public static final String LOGGED_TEXT = "Logged";
	
	public static void searchFilm(String search) throws Exception{
		if(search.isEmpty()){
			return;
		}
		
        String query = "/es/search.php?stext=" + URLEncoder.encode(search, "UTF-8") + "&stype=title";
        
        URI uri = new URI("http", urlBase,  query, null);
        URL url = uri.toURL();

        Map<String, String> response = ConnectionManager.sendRequest(url, null, null, ConnectionManager.METHOD_GET, true, false, false);
        String responseCode = response.get("ResponseCode");
		String responseText = response.get("ResponseBody");
        System.out.println(responseText);
	}
	
	public static boolean logout() throws URISyntaxException, MalformedURLException{
		if(!logged){
			return true;
		}
		
		URI uri = new URI("http", urlBase, "/es/logout.php", null);
		URL url = uri.toURL();
		
		Map<String, String> response = ConnectionManager.sendRequest(url, null, null, ConnectionManager.METHOD_GET, false, false, true);
		if(response != null){
        	return (response.get("ResponseCode").equals("302") && response.get("Location").equals("/es/main.html"));
        }
        return false;
	}
	
	public static boolean login() throws Exception{
		if(logged){
			return true;
		}
		URI uri = new URI("http", urlBase,  "/es/login.php", null);
        URL url = uri.toURL();
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
	
	private static Map<String, String> doLoginProcess(URL url, Map<String, String> parameters) throws Exception{
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
		Map<String, String> postResponse = ConnectionManager.sendRequest(url, parametersChain, null, ConnectionManager.METHOD_POST, false, true, false);
		int responseCode;
		try{
			responseCode = Integer.parseInt(postResponse.get("ResponseCode"));
			System.out.println("responseCode: " + responseCode);
		}catch(NumberFormatException exception){
			return null;
		}
		if((responseCode == HttpURLConnection.HTTP_MOVED_TEMP) && (postResponse.containsKey("Location"))){
			url = new URL(url, postResponse.get("Location"));
			return ConnectionManager.sendRequest(url, null, null, ConnectionManager.METHOD_GET, false, false, true);
		}
		return null;
	}
}
