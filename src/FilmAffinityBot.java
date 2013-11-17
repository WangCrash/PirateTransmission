import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class FilmAffinityBot {
	private static String urlBase = "filmaffinity.com";
	
	public static void searchFilm(String search) throws Exception{
		if(search.isEmpty()){
			return;
		}
		
        String query = "/es/search.php?stext=" + URLEncoder.encode(search, "UTF-8") + "&stype=title";
        
        URI uri = new URI("http", urlBase,  query, null);
        URL url = uri.toURL();

        String[] response = ConnectionManager.responseByGetRequest(url, false);
        System.out.println(response[1]);
	}
	
	public static boolean login() throws Exception{
		
		URI uri = new URI("http", urlBase,  "/es/login.php", null);
        URL url = uri.toURL();
        Map<String, String> data = new HashMap<String, String>();
        data.put("rp", "");
        data.put("user","wang_fan");
        data.put("password", "123wangfan123");
        data.put("postback", "1");
        data.put("ok", "Enviar");
        String[] response = ConnectionManager.FilmAffinityLoginProcess(url, data);
        if(response != null)
        	System.out.println(response[1]);
		return true;
	}
}
